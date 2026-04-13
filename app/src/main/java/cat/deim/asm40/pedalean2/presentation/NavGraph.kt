package cat.deim.asm40.pedalean2.presentation
/**
 * Definición de las rutas de navegación de la aplicación.
 * El uso de una sealed class garantiza que solo existan estas pantallas,
 * para facilitar el manejo del NavHost y evitar errores de rutas.
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object BikeList : Screen("bike_list")
    object Profile : Screen("profile")
}