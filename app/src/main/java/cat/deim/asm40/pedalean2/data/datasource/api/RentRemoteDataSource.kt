package cat.deim.asm40.pedalean2.data.datasource.api

import android.util.Log
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
) : IDatasource<RentModel> {

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

    private fun cat.deim.asm40.pedalean2.data.datasource.api.model.RentApiModel.toRentModel(): RentModel =
        RentModel(
            uuid = uuid,
            bike = BikeRentModel(uuid = bikeUuid, name = bikeName),
            user = UserRentModel(
                email = userEmail, username = userUsername,
                firstName = userFirstName, lastName = userLastName
            ),
            isRented = isRented, rentMeters = rentMeters,
            rentStartLatitude = rentStartLatitude, rentStartLongitude = rentStartLongitude,
            rentTime = rentTime, timeStart = timeStart, timeEnd = timeEnd
        )
}