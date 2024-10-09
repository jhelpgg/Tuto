package fr.jhelp.tuto.ui.composable

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import fr.jhelp.injector.injected
import fr.jhelp.model.shared.GreetingModel
import fr.jhelp.model.shared.NavigationModel
import fr.jhelp.model.shared.Screen

class GreetingComposable
{
    private val greetingModel: GreetingModel by injected<GreetingModel>()
    private val navigationModel: NavigationModel by injected<NavigationModel>()

    @Composable
    fun Show(modifier: Modifier = Modifier)
    {
        val message: String by this.greetingModel.message.collectAsState()
        Log.d("GreetingComposable", "message: $message")

        Column(modifier = modifier) {
            Text(text = "Greeting $message!")

            Button(onClick = { this@GreetingComposable.greetingModel.otherMessage() }) {
                Text(text = "Other message")
            }

            Button(onClick = { this@GreetingComposable.navigationModel.showScreen(Screen.OTHER) }) {
                Text(text = "Other screen")
            }
        }
    }
}