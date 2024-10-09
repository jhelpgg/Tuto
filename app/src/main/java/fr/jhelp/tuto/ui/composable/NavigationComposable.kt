package fr.jhelp.tuto.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import fr.jhelp.injector.injected
import fr.jhelp.model.shared.NavigationModel
import fr.jhelp.model.shared.Screen

class NavigationComposable
{
    private val greetingComposable : GreetingComposable by lazy {GreetingComposable()}
    private val otherComposable : OtherComposable by lazy {OtherComposable()}
    private val navigationModel: NavigationModel by injected<NavigationModel>()

    @Composable
    fun Show(modifier: Modifier = Modifier)
    {
        val screen : Screen by this.navigationModel.currentScreen.collectAsState()

        when(screen)
        {
            Screen.GREETING -> this.greetingComposable.Show(modifier)
            Screen.OTHER -> this.otherComposable.Show(modifier)
        }
    }
}