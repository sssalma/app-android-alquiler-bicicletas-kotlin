package cat.deim.asm40.pedalean2.data.datasource.api

import android.util.Log
import cat.deim.asm40.pedalean2.data.datasource.api.model.RefreshRequestApiModel
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * Cuando una llamada autenticada recibe 401, intenta renovar el access token
 * con el refresh token y reintenta la petición original una sola vez.
 */
class TokenAuthenticator(
    private val tokenStorage: TokenStorage,
    private val serverToken: String
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // Si ya reintentamos una vez con un token nuevo, no insistimos (evita bucle)
        if (responseCount(response) >= 2) return null

        val refresh = tokenStorage.getRefreshToken() ?: return null

        val newAccess = runBlocking {
            try {
                val resp = RetrofitClient.apiService.refreshToken(
                    serverToken, RefreshRequestApiModel(refresh)
                )
                if (resp.isSuccessful) resp.body()?.access else null
            } catch (e: Exception) {
                Log.e("TokenAuthenticator", "refresh error: ${e.message}")
                null
            }
        } ?: return null

        // Guardamos el nuevo access (conservando el refresh actual)
        tokenStorage.saveTokens(newAccess, refresh)

        // Reintentamos la petición original con el token nuevo
        return response.request.newBuilder()
            .header("Authorization", "Bearer $newAccess")
            .build()
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) { count++; prior = prior.priorResponse }
        return count
    }
}