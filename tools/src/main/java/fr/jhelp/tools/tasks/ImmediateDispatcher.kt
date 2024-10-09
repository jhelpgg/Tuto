package fr.jhelp.tools.tasks

import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

class ImmediateDispatcher : CoroutineDispatcher()
{
    override fun dispatch(context: CoroutineContext, block: Runnable)
    {
        block.run()
    }
}
