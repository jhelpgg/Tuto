package fr.jhelp.tools.optional

internal class ValueOptional<T : Any>(private val value: T) : Optional<T>
{
    override val present: Boolean = true
    override val absent: Boolean = false

    override fun ifPresent(action: (T) -> Unit)
    {
        action(this.value)
    }

    override fun ifAbsent(action: () -> Unit) = Unit

    override fun <R : Any> ifPresentElse(action: (T) -> R, absentAction: () -> R): R =
        action(this.value)
}