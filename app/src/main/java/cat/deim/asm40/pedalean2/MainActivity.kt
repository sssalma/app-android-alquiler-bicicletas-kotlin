package cat.deim.asm40.pedalean2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cat.deim.asm40.pedalean2.ui.theme.ASM40Theme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.deim.asm40.pedalean2.presentation.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ASM40Theme {
                val navController = rememberNavController() //para llevar registro de pantallas y navegarlas

                // NavHost decide qué pantalla mostrar dentro de MainActivity (contenedor)
                NavHost(navController = navController, startDestination = Screen.Login.route) {
                    composable(Screen.Login.route) {
                        LoginScreen(onLoginSuccess = {
                            navController.navigate(Screen.BikeList.route)
                        })
                    }
                    composable(Screen.BikeList.route) {
                        Text("¡Bienvenido a la lista de bicis!")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ASM40Theme {
        Greeting("Android")
    }
}

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) { //placeholder per lestructuracio del mainAc
    Text("Pantalla de Login")
}