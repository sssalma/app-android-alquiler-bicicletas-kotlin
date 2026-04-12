package cat.deim.asm40.pedalean2.domain.usecase

/**
 * Data class que encapsula las credenciales de acceso del usuario.
 * Definida por la parte de Presentación para ser usada en el Dominio.
 */
data class Credentials(
    val email: String,
    val password: String
)