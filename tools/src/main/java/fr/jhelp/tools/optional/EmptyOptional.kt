package fr.jhelp.tools.optional

internal class EmptyOptional<T : Any> : Optional<T>
{
    override val present: Boolean = false
    override val absent: Boolean = true

    override fun ifPresent(action: (T) -> Unit) = Unit

    override fun ifAbsent(action: () -> Unit)
    {
        action()
    }

    override fun <R : Any> ifPresentElse(action: (T) -> R, absentAction: () -> R): R =
        absentAction()
}