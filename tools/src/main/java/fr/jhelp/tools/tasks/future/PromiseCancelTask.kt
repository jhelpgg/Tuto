package fr.jhelp.tools.tasks.future

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class PromiseCancelTask(private val coroutineContext: CoroutineContext, private val task: suspend (String) -> Unit)
{
    fun execute(reason: String)
    {
        CoroutineScope(this.coroutineContext).launch {
            this@PromiseCancelTask.task(reason)
        }
    }
}