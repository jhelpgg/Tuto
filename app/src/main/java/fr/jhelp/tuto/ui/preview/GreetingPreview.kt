package fr.jhelp.tuto.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fr.jhelp.injector.inject
import fr.jhelp.model.dummy.GreetingDummy
import fr.jhelp.model.shared.GreetingModel
import fr.jhelp.tuto.ui.composable.GreetingComposable
import fr.jhelp.tuto.ui.theme.TutoTheme

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    inject<GreetingModel>(GreetingDummy("to preview"))
    val greetingComposable = GreetingComposable()
    TutoTheme {
        greetingComposable.Show()
    }
}