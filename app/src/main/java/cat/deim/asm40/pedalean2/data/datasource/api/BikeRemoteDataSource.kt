package cat.deim.asm40.pedalean2.data.datasource.api

import android.util.Log
import cat.deim.asm40.pedalean2.data.datasource.api.model.BikeApiModel
import com.pedalean2.common.datasource.local.model.BikeModel
import com.pedalean2.common.interfaces.IDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class BikeRemoteDatasource(
    private val apiService: ApiService,
    private val tokenStorage: TokenStorage,
    private val serverToken: String
) : IDatasource<BikeModel> {

    override fun getAll(): List<BikeModel> = runBlocking {
        withContext(Dispatchers.IO) {
            try {
                val bearer = tokenStorage.getBearerToken() ?: return@withContext emptyList()
                val response = apiService.getBikes(serverToken, bearer)
                if (response.isSuccessful) {
                    response.body()?.bike?.map { it.toBikeModel() } ?: emptyList()
                } else emptyList()
            } catch (e: Exception) {
                Log.e("BikeRemoteDatasource", "getAll error: ${e.message}")
                emptyList()
            }
        }
    }

    override fun getById(uuid: String): BikeModel? = getAll().find { it.uuid == uuid }
    override fun insert(dataModel: BikeModel): Boolean = false
    override fun update(dataModel: BikeModel): Boolean = false
    override fun delete(uuid: String): Boolean = false

    private fun BikeApiModel.toBikeModel(): BikeModel = BikeModel(
        uuid = uuid, id = id, name = name, type = type,
        batteryLevel = batteryLevel, meters = meters,
        isRented = isRented, isReserved = isReserved,
        latitude = latitude, longitude = longitude,
        lastMaintenance = lastMaintenance, lastUse = lastUse
    )
}