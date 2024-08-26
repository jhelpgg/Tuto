package fr.jhelp.injector

class InjectionNotFoundException(className:String) : Exception("No Injection found for class : $className")