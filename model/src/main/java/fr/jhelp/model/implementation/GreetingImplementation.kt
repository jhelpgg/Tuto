package fr.jhelp.model.implementation

import fr.jhelp.model.shared.GreetingModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class GreetingImplementation : GreetingModel {
    private val messageMutable = MutableStateFlow("to you")
    override val message: StateFlow<String> = this.messageMutable

    override fun otherMessage() {
        this.messageMutable.value = "Yop ${Math.random()}"
    }
}