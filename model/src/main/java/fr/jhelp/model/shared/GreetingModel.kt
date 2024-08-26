package fr.jhelp.model.shared

import kotlinx.coroutines.flow.StateFlow


interface GreetingModel {
    val message : StateFlow<String>

    fun otherMessage()
}