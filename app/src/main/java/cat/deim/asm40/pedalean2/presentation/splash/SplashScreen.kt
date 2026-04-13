package cat.deim.asm40.pedalean2.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cat.deim.asm40.pedalean2.R
import kotlinx.coroutines.delay

/**
 * Pantalla de bienvenida (Splash Screen) de la aplicación.
 * *Muestra el logotipo de la apen pantalla durante 2s.
 * Utiliza [LaunchedEffect] para gestionar el retardo de forma no bloqueante para recargar los datos de mientras
 * antes de ejecutar la acción de navegación definida en [onTimeout].
 *
 * @param onTimeout Función lambda que se ejecuta tras la espera para navegar a la siguiente pantalla.
 */
@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {      //Unit -> solo se ejectuará 1 vez
        delay(2000)         // 2 segundos
        onTimeout()                   //cuando se
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.app_name)
        )
    }
}