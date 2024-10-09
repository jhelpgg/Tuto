package fr.jhelp.tools.tasks.future

import fr.jhelp.tools.tasks.Mutex
import fr.jhelp.tools.tasks.future.future_status.FutureCanceled
import fr.jhelp.tools.tasks.future.future_status.FutureComputing
import fr.jhelp.tools.tasks.future.future_status.FutureFailed
import fr.jhelp.tools.tasks.future.future_status.FutureResult
import fr.jhelp.tools.tasks.future.future_status.FutureStatus
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class Future<T : Any> internal constructor(private val promise: Promise<T>)
{
    private var futureStatus: FutureStatus<T> = FutureComputing<T>()
    private val mutex = Mutex()
    private val tasks = ArrayList<FutureTask<T>>()
    private val lock = Object()

    fun <R : Any> onSuccess(task: (T) -> R): Future<R> =
        this.onSuccess(Dispatchers.Default, task)

    fun <R : Any> onSuccess(coroutineContext: CoroutineContext, task: (T) -> R): Future<R>
    {
        val promise = Promise<R>()
        val futureTask = FutureTask<T>(coroutineContext) { futureStatus ->
            when (futureStatus)
            {
                is FutureResult   ->
                {
                    try
                    {
                        promise.success(task(futureStatus.result))
                    }
                    catch (exception: Exception)
                    {
                        promise.fail(exception)
                    }
                }

                is FutureFailed   -> promise.fail(futureStatus.error)
                is FutureCanceled -> promise.cancel(futureStatus.reason)
                else              -> Unit
            }
        }

        this.addTask(futureTask)
        promise.onCancel { reason -> this.cancel(reason) }
        return promise.future
    }

    fun onFailure(task: (Exception) -> Unit): Future<T> =
        this.onFailure(Dispatchers.Default, task)

    fun onFailure(coroutineContext: CoroutineContext, task: (Exception) -> Unit): Future<T>
    {
        val futureTask = FutureTask<T>(coroutineContext) { futureStatus ->
            when (futureStatus)
            {
                is FutureFailed -> task(futureStatus.error)
                else            -> Unit
            }
        }

        this.addTask(futureTask)
        return this
    }

    fun onCancel(task: (String) -> Unit): Future<T> =
        this.onCancel(Dispatchers.Default, task)

    fun onCancel(coroutineContext: CoroutineContext, task: (String) -> Unit): Future<T>
    {
        val futureTask = FutureTask<T>(coroutineContext) { futureStatus ->
            when (futureStatus)
            {
                is FutureCanceled -> task(futureStatus.reason)
                else              -> Unit
            }
        }

        this.addTask(futureTask)
        return this
    }

    fun <R : Any> onCompletion(task: (Future<T>) -> R): Future<R> =
        this.onCompletion(Dispatchers.Default, task)

    fun <R : Any> onCompletion(coroutineContext: CoroutineContext, task: (Future<T>) -> R): Future<R>
    {
        val promise = Promise<R>()
        val futureTask = FutureTask<T>(coroutineContext) { _ ->
            try
            {
                promise.success(task(this))
            }
            catch (exception: Exception)
            {
                promise.fail(exception)
            }
        }

        this.addTask(futureTask)
        promise.onCancel { reason -> this.cancel(reason) }
        return promise.future
    }

    fun <R : Any> onSuccessUnwrap(task: (T) -> Future<R>): Future<R> =
        this.onSuccessUnwrap(Dispatchers.Default, task)

    fun <R : Any> onSuccessUnwrap(coroutineContext: CoroutineContext, task: (T) ->  Future<R>): Future<R> =
        this.onSuccess(coroutineContext, task).unwrap

    fun <R : Any> onCompletionUnwrap(task: (Future<T>) ->  Future<R>): Future<R> =
        this.onCompletionUnwrap(Dispatchers.Default, task)

    fun <R : Any> onCompletionUnwrap(coroutineContext: CoroutineContext, task: (Future<T>) ->  Future<R>): Future<R> =
        this.onCompletion(coroutineContext, task).unwrap

    fun waitCompletion(): FutureStatus<T>
    {
        synchronized(this.lock) {
            while (this.futureStatus is FutureComputing)
            {
                this.lock.wait()
            }
        }

        return this.futureStatus
    }

    fun cancel(reason: String)
    {
        this.mutex {
            when (this.futureStatus)
            {
                is FutureComputing ->
                {
                    this.futureStatus = FutureCanceled(reason)
                    this.completion()
                    this.promise.cancel(reason)
                }

                else               -> Unit
            }
        }
    }

    internal fun result(result: T)
    {
        this.mutex {
            when (this.futureStatus)
            {
                is FutureComputing ->
                {
                    this.futureStatus = FutureResult(result)
                    this.completion()
                }

                else               -> Unit
            }
        }
    }

    internal fun failed(error: Exception)
    {
        this.mutex {
            when (this.futureStatus)
            {
                is FutureComputing ->
                {
                    this.futureStatus = FutureFailed(error)
                    this.completion()
                }

                else               -> Unit
            }
        }
    }

    private fun completion()
    {
        for (task in this.tasks)
        {
            task.execute(this.futureStatus)
        }

        this.tasks.clear()

        synchronized(this.lock) { this.lock.notifyAll() }
    }

    private fun addTask(task: FutureTask<T>)
    {
        this.mutex {
            when (this.futureStatus)
            {
                is FutureComputing -> this.tasks.add(task)
                else               -> task.execute(this.futureStatus)
            }
        }
    }
}