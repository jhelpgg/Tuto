package fr.jhelp.tools.tasks

import fr.jhelp.tools.tasks.future.Future
import fr.jhelp.tools.tasks.future.Promise
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun <R : Any> (() -> R).parallel(coroutineContext: CoroutineContext = Dispatchers.Default): Future<R>
{
    val promise = Promise<R>()
    val job = CoroutineScope(coroutineContext).launch {
        try
        {
            promise.success(this@parallel())
        }
        catch (exception: Exception)
        {
            promise.fail(exception)
        }
    }

    promise.onCancel { reason -> job.cancel(CancellationException(reason)) }
    return promise.future
}

fun <R : Any> (() -> R).delay(milliseconds: Long, coroutineContext: CoroutineContext = Dispatchers.Default): Future<R>
{
    val promise = Promise<R>()
    val job = CoroutineScope(coroutineContext).launch {
        kotlinx.coroutines.delay(milliseconds)

        try
        {
            promise.success(this@delay())
        }
        catch (exception: Exception)
        {
            promise.fail(exception)
        }
    }

    promise.onCancel { reason -> job.cancel(CancellationException(reason)) }
    return promise.future
}

fun <P : Any, R : Any> ((P) -> R).parallel(parameter: P, coroutineContext: CoroutineContext = Dispatchers.Default): Future<R>
{
    val promise = Promise<R>()
    val job = CoroutineScope(coroutineContext).launch {
        try
        {
            promise.success(this@parallel(parameter))
        }
        catch (exception: Exception)
        {
            promise.fail(exception)
        }
    }

    promise.onCancel { reason -> job.cancel(CancellationException(reason)) }
    return promise.future
}

fun <P : Any, R : Any> ((P) -> R).delay(parameter: P, milliseconds: Long, coroutineContext: CoroutineContext = Dispatchers.Default): Future<R>
{
    val promise = Promise<R>()
    val job = CoroutineScope(coroutineContext).launch {
        kotlinx.coroutines.delay(milliseconds)

        try
        {
            promise.success(this@delay(parameter))
        }
        catch (exception: Exception)
        {
            promise.fail(exception)
        }
    }

    promise.onCancel { reason -> job.cancel(CancellationException(reason)) }
    return promise.future
}