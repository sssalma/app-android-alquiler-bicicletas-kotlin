package cat.deim.asm40.pedalean2.data.datasource.api

import cat.deim.asm40.pedalean2.data.datasource.api.model.UserApiModel
import cat.deim.asm40.pedalean2.domain.models.User

/**
 * UserRemoteDatasource accede al endpoint de usuario de la API REST.
 * Gestiona las llamadas suspend para ser usadas desde coroutines.
 */
class UserRemoteDatasource(
    private val apiService: ApiService,
    private val tokenStorage: TokenStorage,
    private val serverToken: String
) {

    /**
     * Obtiene el token de acceso a partir de las credenciales.
     * Guarda los tokens en TokenStorage si la respuesta es exitosa.
     * @return true si el login fue correcto
     */
    suspend fun login(username: String, password: String): Boolean {
        return try {
            val response = apiService.getToken(
                serverToken = serverToken,
                credentials = cat.deim.asm40.pedalean2.data.datasource.api.model.TokenRequestApiModel(
                    username = username,
                    password = password
                )
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    tokenStorage.saveTokens(body.access, body.refresh)
                    tokenStorage.saveUserEmail(username)
                    true
                } else false
            } else false
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Obtiene la información del usuario autenticado desde el servidor.
     * @return UserApiModel si la petición es exitosa, null en caso contrario.
     */
    suspend fun getUser(): UserApiModel? {
        return try {
            val bearer = tokenStorage.getBearerToken() ?: return null
            val response = apiService.getUser(
                serverToken = serverToken,
                authorization = bearer
            )
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Convierte el resultado del servidor a dominio.
     */
    suspend fun getUserDomain(): User? = getUser()?.toDomain()
}
