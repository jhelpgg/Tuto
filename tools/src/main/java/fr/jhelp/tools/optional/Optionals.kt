package fr.jhelp.tools.optional

fun <T:Any> emptyOptional() : Optional<T> = EmptyOptional()

fun <T:Any> valueOptional(value:T) : Optional<T> = ValueOptional(value)
