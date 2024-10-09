package fr.jhelp.model.shared

import kotlinx.coroutines.flow.StateFlow

interface NavigationModel
{
    val currentScreen : StateFlow<Screen>

    fun showScreen(screen: Screen)
}