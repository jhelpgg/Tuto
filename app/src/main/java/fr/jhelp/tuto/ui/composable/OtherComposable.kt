package fr.jhelp.tuto.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.injector.injected
import fr.jhelp.model.shared.GreetingModel
import fr.jhelp.model.shared.NavigationModel
import fr.jhelp.model.shared.Screen

class OtherComposable
{
    private val greetingModel: GreetingModel by injected<GreetingModel>()
    private val navigationModel: NavigationModel by injected<NavigationModel>()

    @Composable
    fun Show(modifier: Modifier=Modifier) {
        Column(modifier = modifier) {
            Text(text = "Other screen!")

            Button(onClick = { this@OtherComposable.greetingModel.otherMessage() }) {
                Text(text = "Other message")
            }

            Button(onClick = { this@OtherComposable.navigationModel.showScreen(Screen.GREETING) }) {
                Text(text = "Previous screen")
            }
        }
    }
}