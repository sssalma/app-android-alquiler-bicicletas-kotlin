package cat.deim.asm40.pedalean2.data.datasource.api

import cat.deim.asm40.pedalean2.data.datasource.api.model.RentApiModel
import cat.deim.asm40.pedalean2.data.datasource.api.model.RentStartRequestApiModel
import cat.deim.asm40.pedalean2.data.datasource.api.model.RentStopRequestApiModel
import cat.deim.asm40.pedalean2.domain.models.Rent

/**
 * RentRemoteDatasource accede a los endpoints de alquiler de la API REST.
 */
class RentRemoteDatasource(
    private val apiService: ApiService,
    private val tokenStorage: TokenStorage,
    private val serverToken: String
) {

    /**
     * Obtiene el historial de alquileres del usuario autenticado.
     */
    suspend fun getRents(): List<RentApiModel> {
        return try {
            val bearer = tokenStorage.getBearerToken() ?: return emptyList()
            val response = apiService.getRents(
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
     * Inicia un nuevo alquiler de bicicleta.
     * @return Rent de dominio si el servidor confirma el inicio, null en caso contrario.
     */
    suspend fun startRent(bikeUuid: String, latitude: Double, longitude: Double): Rent? {
        return try {
            val bearer = tokenStorage.getBearerToken() ?: return null
            val response = apiService.startRent(
                serverToken = serverToken,
                authorization = bearer,
                request = RentStartRequestApiModel(bikeUuid, latitude, longitude)
            )
            if (response.isSuccessful) response.body()?.toDomain() else null
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Finaliza un alquiler activo.
     * @return Rent de dominio si el servidor confirma la parada, null en caso contrario.
     */
    suspend fun stopRent(rentUuid: String, latitude: Double, longitude: Double): Rent? {
        return try {
            val bearer = tokenStorage.getBearerToken() ?: return null
            val response = apiService.stopRent(
                serverToken = serverToken,
                authorization = bearer,
                request = RentStopRequestApiModel(rentUuid, latitude, longitude)
            )
            if (response.isSuccessful) response.body()?.toDomain() else null
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Obtiene los alquileres convertidos al modelo de dominio.
     */
    suspend fun getRentsDomain(): List<Rent> = getRents().map { it.toDomain() }
}
