package fr.jhelp.model

import fr.jhelp.data.data
import fr.jhelp.injector.inject
import fr.jhelp.model.implementation.GreetingImplementation
import fr.jhelp.model.implementation.NavigationImplementation
import fr.jhelp.model.shared.GreetingModel
import fr.jhelp.model.shared.NavigationModel

fun models()
{
    data()
    inject<NavigationModel>(NavigationImplementation())
    inject<GreetingModel>(GreetingImplementation())
}