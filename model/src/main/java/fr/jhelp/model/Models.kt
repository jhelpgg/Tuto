package fr.jhelp.model

import fr.jhelp.data.data
import fr.jhelp.injector.inject
import fr.jhelp.model.implementation.GreetingImplementation
import fr.jhelp.model.shared.GreetingModel

fun models() {
    data()
    inject<GreetingModel>(GreetingImplementation())
}