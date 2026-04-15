package cat.deim.asm40.pedalean2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cat.deim.asm40.pedalean2.presentation.login.LoginActivity
import cat.deim.asm40.pedalean2.presentation.splash.SplashScreen
import cat.deim.asm40.pedalean2.ui.theme.ASM40Theme
/**
 *Launcher Activity (Manifest modificado).
 * Esta actividad se encarga de:
 * 1. Inicializar el tema visual de la aplicación.
 * 2. Mostrar SplashScreen.
 * 3. Gestionar la transición hacia LoginActivity cuando se pulsa el botón.
 */
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ASM40Theme {
                SplashScreen(
                    onStartClick = {
                        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}