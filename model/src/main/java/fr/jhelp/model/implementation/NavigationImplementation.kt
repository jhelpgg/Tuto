package fr.jhelp.model.implementation

import fr.jhelp.model.shared.NavigationModel
import fr.jhelp.model.shared.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class NavigationImplementation : NavigationModel
{
    private val currentScreenMutable = MutableStateFlow(Screen.GREETING)
    override val currentScreen: StateFlow<Screen> = this.currentScreenMutable.asStateFlow()

    override fun showScreen(screen: Screen)
    {
        this.currentScreenMutable.value = screen
    }
}