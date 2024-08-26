package fr.jhelp.tuto.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import fr.jhelp.injector.injected
import fr.jhelp.model.shared.GreetingModel

class GreetingComposable {
    private val greetingModel: GreetingModel by injected<GreetingModel>()

    @Composable
    fun Show(modifier: Modifier = Modifier) {
        val message: String by this.greetingModel.message.collectAsState()

        Column(modifier = modifier) {
            Text(text = "Greeting $message!")

            Button(onClick = { this@GreetingComposable.greetingModel.otherMessage() }) {
                Text(text = "Other message")
            }
        }
    }
}