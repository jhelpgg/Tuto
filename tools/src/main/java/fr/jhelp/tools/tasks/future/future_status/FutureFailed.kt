package fr.jhelp.tools.tasks.future.future_status

class FutureFailed<T:Any>(val error:Exception) : FutureStatus<T>
