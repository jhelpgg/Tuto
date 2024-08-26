package fr.jhelp.model.dummy

import fr.jhelp.model.shared.GreetingModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GreetingDummy(val messageMutable: MutableStateFlow<String>, val otherMessageProvider: () -> String = { "other" }) : GreetingModel {
    constructor(message: String, otherMessageProvider: () -> String = { "other" }) : this(MutableStateFlow(message), otherMessageProvider)

    override val message: StateFlow<String> = this.messageMutable

    override fun otherMessage() {
        this.messageMutable.value = this.otherMessageProvider()
    }
}