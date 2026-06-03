package cat.deim.asm40.pedalean2.data.datasource.api

import android.util.Log
import cat.deim.asm40.pedalean2.data.datasource.api.model.RentStartRequestApiModel
import cat.deim.asm40.pedalean2.data.datasource.api.model.RentStopRequestApiModel
import com.pedalean2.common.datasource.local.model.BikeRentModel
import com.pedalean2.common.datasource.local.model.RentModel
import com.pedalean2.common.datasource.local.model.UserRentModel
import com.pedalean2.common.interfaces.IDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
class RentRemoteDatasource(
    private val apiService: ApiService,
    private val tokenStorage: TokenStorage,
    private val serverToken: String
) : IRentRemoteDatasource {

    override fun getAll(): List<RentModel> = runBlocking {
        withContext(Dispatchers.IO) {
            try {
                val bearer = tokenStorage.getBearerToken() ?: return@withContext emptyList()
                val response = apiService.getRents(serverToken, bearer)
                if (response.isSuccessful) {
                    response.body()?.rent?.map { it.toRentModel() } ?: emptyList()
                } else emptyList()
            } catch (e: Exception) {
                Log.e("RentRemoteDatasource", "getAll error: ${e.message}")
                emptyList()
            }
        }
    }

    override fun getById(uuid: String): RentModel? = getAll().find { it.uuid == uuid }

    override fun insert(dataModel: RentModel): Boolean = false
    override fun update(dataModel: RentModel): Boolean = false
    override fun delete(uuid: String): Boolean = false

    override suspend fun startRent(bikeUuid: String, latitude: Double, longitude: Double): Boolean {
        return try {
            val bearer = tokenStorage.getBearerToken() ?: return false
            val response = apiService.startRent(
                serverToken, bearer,
                RentStartRequestApiModel(bikeUuid, latitude, longitude)
            )
            if (!response.isSuccessful) {
                Log.e("RentRemoteDatasource", "startRent ${response.code()}: ${response.errorBody()?.string()}")
            }
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("RentRemoteDatasource", "startRent error: ${e.message}")
            false
        }
    }

    override suspend fun stopRent(bikeUuid: String, latitude: Double, longitude: Double): Boolean {
        return try {
            val bearer = tokenStorage.getBearerToken() ?: return false
            val response = apiService.stopRent(
                serverToken, bearer,
                RentStopRequestApiModel(bikeUuid, latitude, longitude)
            )
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("RentRemoteDatasource", "stopRent error: ${e.message}")
            false
        }
    }
    private fun cat.deim.asm40.pedalean2.data.datasource.api.model.RentApiModel.toRentModel(): RentModel =
        RentModel(
            uuid = uuid,
            bike = BikeRentModel(uuid = bike.uuid, name = bike.name),
            user = UserRentModel(
                email = user.email, username = user.username,
                firstName = user.firstName, lastName = user.lastName
            ),
            isRented = isRented, rentMeters = rentMeters,
            rentStartLatitude = rentStartLat, rentStartLongitude = rentStartLng,
            rentTime = rentTime, timeStart = timeStart ?: "", timeEnd = timeEnd ?: ""
        )
}