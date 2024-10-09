package fr.jhelp.tools.tasks

import java.util.concurrent.Semaphore

class Mutex
{
    private val mutex = Semaphore(1, true)

    operator fun <T> invoke(block: () -> T): T
    {
        this.mutex.acquire()

        try
        {
            return block()
        }
        finally
        {
            this.mutex.release()
        }
    }
}