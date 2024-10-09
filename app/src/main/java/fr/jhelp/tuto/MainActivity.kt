package fr.jhelp.tuto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import fr.jhelp.tuto.ui.composable.GreetingComposable
import fr.jhelp.tuto.ui.composable.NavigationComposable
import fr.jhelp.tuto.ui.theme.TutoTheme

class MainActivity : ComponentActivity() {
    private val navigationComposable: NavigationComposable by lazy { NavigationComposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TutoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    this.navigationComposable.Show(Modifier.padding(innerPadding))
                }
            }
        }
    }
}
