package cat.deim.asm40.pedalean2.data.datasource.api

import com.pedalean2.common.datasource.local.model.RentModel
import com.pedalean2.common.interfaces.IDatasource

interface IRentRemoteDatasource : IDatasource<RentModel> {
    suspend fun startRent(bikeUuid: String, latitude: Double, longitude: Double): Boolean
    suspend fun stopRent(bikeUuid: String, latitude: Double, longitude: Double): Boolean
}