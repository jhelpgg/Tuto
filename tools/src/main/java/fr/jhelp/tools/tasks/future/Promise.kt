package fr.jhelp.tools.tasks.future

import fr.jhelp.tools.tasks.Mutex
import fr.jhelp.tools.tasks.future.promise_status.PromiseCanceled
import fr.jhelp.tools.tasks.future.promise_status.PromiseComputing
import fr.jhelp.tools.tasks.future.promise_status.PromiseFinished
import fr.jhelp.tools.tasks.future.promise_status.PromiseStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.CoroutineContext

class Promise<T : Any>
{
    val future: Future<T> = Future<T>(this)
    private val mutex = Mutex()
    private val tasks = ArrayList<PromiseCancelTask>()
    private val promiseStatus = AtomicReference<PromiseStatus>(PromiseComputing)
    val status : PromiseStatus get() = this.promiseStatus.get()

    fun success(result: T)
    {
        if (this.promiseStatus.compareAndSet(PromiseComputing, PromiseFinished))
        {
            this.future.result(result)

            this.mutex { this.tasks.clear() }
        }
    }

    fun fail(exception: Exception)
    {
        if (this.promiseStatus.compareAndSet(PromiseComputing, PromiseFinished))
        {
            this.future.failed(exception)

            this.mutex { this.tasks.clear() }
        }
    }

    fun onCancel(task: suspend (String) -> Unit)
    {
        this.onCancel(Dispatchers.Default, task)
    }

    fun onCancel(coroutineContext: CoroutineContext, task: suspend (String) -> Unit)
    {
        this.mutex {
            val status = this.promiseStatus.get()

            when (status)
            {
                PromiseComputing   -> this.tasks.add(PromiseCancelTask(coroutineContext, task))
                is PromiseCanceled -> CoroutineScope(coroutineContext).launch { task(status.reason) }
                else               -> Unit
            }
        }
    }

    internal fun cancel(reason: String)
    {
        if (this.promiseStatus.compareAndSet(PromiseComputing, PromiseCanceled(reason)))
        {
            this.mutex {
                for (task in this.tasks)
                {
                    task.execute(reason)
                }

                this.tasks.clear()
            }
        }
    }
}