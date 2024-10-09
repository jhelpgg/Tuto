package fr.jhelp.tools.optional

sealed interface Optional<T : Any>
{
    val present: Boolean
    val absent: Boolean
    fun ifPresent(action: (T) -> Unit)
    fun ifAbsent(action: () -> Unit)
    fun <R : Any> ifPresentElse(action: (T) -> R, absentAction: () -> R): R
}
