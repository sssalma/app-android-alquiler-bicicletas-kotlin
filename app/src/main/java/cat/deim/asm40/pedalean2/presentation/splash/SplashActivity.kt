package cat.deim.asm40.pedalean2.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cat.deim.asm40.pedalean2.data.datasource.api.RetrofitClient
import cat.deim.asm40.pedalean2.data.datasource.api.TokenStorage // <-- Añadido el import
import cat.deim.asm40.pedalean2.presentation.bikelist.BikeListActivity // <-- Añadido el import
import cat.deim.asm40.pedalean2.presentation.login.LoginActivity
import cat.deim.asm40.pedalean2.ui.theme.ASM40Theme

/**
 *Launcher Activity (Manifest modificado).
 * Esta actividad se encarga de:
 * 1. Inicializar el tema visual de la aplicación.
 * 2. Mostrar SplashScreen.
 * 3. Gestionar la transición dependiendo de si hay sesión activa o no.
 */
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitClient.init(TokenStorage(applicationContext))

        // Instanciamos el TokenStorage pasando el contexto de la Activity
        val tokenStorage = TokenStorage(this)

        setContent {
            ASM40Theme {
                SplashScreen(
                    onStartClick = {
                        // Comprobamos si la sesión está activa
                        if (tokenStorage.isSessionActive()) {
                            // Si hay token, vamos directos a la lista de bicis
                            val intent = Intent(this@SplashActivity, BikeListActivity::class.java)
                            startActivity(intent)
                        } else {
                            // Si no hay token, pedimos login
                            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        // Cerramos el Splash para que el usuario no pueda volver atrás a él
                        finish()
                    }
                )
            }
        }
    }
}