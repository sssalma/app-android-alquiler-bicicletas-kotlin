package cat.deim.asm40.pedalean2.data.datasource.api
import android.content.Context
import android.content.SharedPreferences

/**
 * TokenStorage gestiona la persistencia de los tokens de sesión.
 * Los tokens se guardan en SharedPreferences con cifrado básico.
 *
 * El server-token NO se guarda aquí ni en el código fuente.
 * Debe leerse desde un fichero local (asm_local.properties) o variable de entorno agregados al .gitignore por seguridad.
 */

class TokenStorage(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "pedalean2_session"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_USER_EMAIL = "user_email"
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        prefs.edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .putString(KEY_REFRESH_TOKEN, refreshToken)
            .apply()
    }

    fun saveUserEmail(email: String) {
        prefs.edit().putString(KEY_USER_EMAIL, email).apply()
    }

    fun getAccessToken(): String? = prefs.getString(KEY_ACCESS_TOKEN, null)

    fun getRefreshToken(): String? = prefs.getString(KEY_REFRESH_TOKEN, null)

    fun getUserEmail(): String? = prefs.getString(KEY_USER_EMAIL, null)

    /** Devuelve el token en formato Bearer para el header Authorization */
    fun getBearerToken(): String? = getAccessToken()?.let { "Bearer $it" }

    fun isSessionActive(): Boolean = getAccessToken() != null

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
