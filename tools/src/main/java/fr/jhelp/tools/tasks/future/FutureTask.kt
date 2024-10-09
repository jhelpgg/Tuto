package fr.jhelp.tools.tasks.future

import fr.jhelp.tools.tasks.future.future_status.FutureStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class FutureTask<T:Any>(private val coroutineContext: CoroutineContext, private val task: suspend (FutureStatus<T>) -> Unit)
{
    fun execute(futureStatus: FutureStatus<T>)
    {
        CoroutineScope(this.coroutineContext).launch { this@FutureTask.task(futureStatus) }
    }
}
