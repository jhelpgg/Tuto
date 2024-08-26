package fr.jhelp.injector

import kotlin.reflect.KProperty

private val injected = HashMap<String, Any>()

class Injected<I>(private val className: String) {
    operator fun getValue(thisRef: Any, property: KProperty<*>): I {
        return injected[this.className]?.let { instance -> instance as I } ?: throw InjectionNotFoundException(this.className)
    }
}

fun <I : Any> inject(clazz: Class<I>, instance: I) {
    injected[clazz.name] = instance
}

inline fun <reified I : Any> inject(instance: I) {
    inject(I::class.java, instance)
}

inline fun <reified I : Any> injected(): Injected<I> = Injected<I>(I::class.java.name)
