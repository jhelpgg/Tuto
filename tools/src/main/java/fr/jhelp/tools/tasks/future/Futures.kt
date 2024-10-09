package fr.jhelp.tools.tasks.future

import fr.jhelp.tools.tasks.future.future_status.FutureCanceled
import fr.jhelp.tools.tasks.future.future_status.FutureFailed
import fr.jhelp.tools.tasks.future.future_status.FutureResult

val <T:Any> Future<Future<T>>.unwrap:Future<T> get()
{
    val promise = Promise<T>()

   this.onCompletion { future ->
        val status = future.waitCompletion()

        when(status)
        {
            is FutureResult -> {
                status.result.onCompletion { result ->
                    val resultStatus = result.waitCompletion()

                    when(resultStatus)
                    {
                        is FutureResult -> promise.success(resultStatus.result)
                        is FutureFailed -> promise.fail(resultStatus.error)
                        is FutureCanceled -> promise.cancel(resultStatus.reason)
                        else -> promise.fail(IllegalStateException("Unknown status $resultStatus"))
                    }
                }

                promise.onCancel { reason -> status.result.cancel(reason) }
            }
            is FutureFailed -> promise.fail(status.error)
            is FutureCanceled -> promise.cancel(status.reason)
            else -> promise.fail(IllegalStateException("Unknown status $status"))
        }
    }

    promise.onCancel { reason -> this.cancel(reason) }
    return promise.future
}

val Exception.future:Future<Nothing> get()
{
    val promise = Promise<Nothing>()
    promise.fail(this)
    return promise.future
}

val <T:Any> T.future:Future<T> get()
{
    val promise = Promise<T>()
    promise.success(this)
    return promise.future
}