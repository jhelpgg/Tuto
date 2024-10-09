package fr.jhelp.injector

import kotlin.reflect.KProperty

private val injected = HashMap<String, Any>()

/**
 * Public for reified usage, not use it directly outside of this file
  */
class Injected<I>(private val className: String, private val qualifier: String)
{
    operator fun getValue(thisRef: Any, property: KProperty<*>): I
    {
        return injected["${this.className}:${this.qualifier}"]?.let { instance -> instance as I } ?: throw InjectionNotFoundException("${this.className}:${this.qualifier}")
    }
}

/**
 * Public for reified usage, not use it directly outside of this file
 */
fun <I : Any> inject(clazz: Class<I>, instance: I, qualifier: String)
{
    injected["${clazz.name}:$qualifier"] = instance
}

/**
 * Inject an instance to use it later
 */
inline fun <reified I : Any> inject(instance: I, qualifier: String = "")
{
    inject(I::class.java, instance, qualifier)
}

/**
 * Obtain an injected instance
 */
inline fun <reified I : Any> injected(qualifier: String = ""): Injected<I> = Injected<I>(I::class.java.name, qualifier)
