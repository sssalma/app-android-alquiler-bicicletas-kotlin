package cat.deim.asm40.pedalean2.data.datasource.api


import cat.deim.asm40.pedalean2.data.datasource.api.model.BikeApiModel
import cat.deim.asm40.pedalean2.domain.models.Bike

/**
 * BikeRemoteDatasource accede al endpoint de bicicletas de la API REST.
 */
class BikeRemoteDatasource(
    private val apiService: ApiService,
    private val tokenStorage: TokenStorage,
    private val serverToken: String
) {

    /**
     * Obtiene la lista de bicicletas disponibles desde el servidor.
     * @return Lista de BikeApiModel si la petición es exitosa, lista vacía en caso contrario.
     */
    suspend fun getBikes(): List<BikeApiModel> {
        return try {
            val bearer = tokenStorage.getBearerToken() ?: return emptyList()
            val response = apiService.getBikes(
                serverToken = serverToken,
                authorization = bearer
            )
            if (response.isSuccessful) response.body() ?: emptyList()
            else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Obtiene las bicicletas y las convierte al modelo de dominio.
     */
    suspend fun getBikesDomain(): List<Bike> = getBikes().map { it.toDomain() }
}
