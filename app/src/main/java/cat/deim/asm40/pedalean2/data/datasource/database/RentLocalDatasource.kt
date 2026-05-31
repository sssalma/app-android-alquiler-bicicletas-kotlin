package cat.deim.asm40.pedalean2.data.datasource.database

import com.pedalean2.common.datasource.local.model.BikeRentModel
import com.pedalean2.common.datasource.local.model.RentModel
import com.pedalean2.common.datasource.local.model.UserRentModel
import com.pedalean2.common.interfaces.IDatasource
import cat.deim.asm40.pedalean2.data.datasource.database.model.RentDTO

class RentLocalDatasource(private val rentDatasource: RentDatasource) : IDatasource<RentModel> {

    override fun getAll(): List<RentModel> {
        return rentDatasource.getAll().map { it.toModel() }
    }

    override fun getById(uuid: String): RentModel? {
        return rentDatasource.getByUuid(uuid)?.toModel()
    }

    override fun insert(dataModel: RentModel): Boolean {
        return try {
            rentDatasource.insert(RentDTO.fromModel(dataModel))
            true
        } catch (e: Exception) { false }
    }

    override fun update(dataModel: RentModel): Boolean {
        return try {
            rentDatasource.update(RentDTO.fromModel(dataModel))
            true
        } catch (e: Exception) { false }
    }

    override fun delete(uuid: String): Boolean {
        return rentDatasource.deleteByUuid(uuid) > 0
    }

    private fun RentDTO.toModel(): RentModel = RentModel(
        uuid = uuid,
        bike = BikeRentModel(uuid = bikeUuid, name = bikeName),
        user = UserRentModel(email = userEmail, username = userUsername,
            firstName = userFirstName, lastName = userLastName),
        isRented = isRented, rentMeters = rentMeters,
        rentStartLatitude = rentStartLatitude, rentStartLongitude = rentStartLongitude,
        rentTime = rentTime, timeStart = timeStart, timeEnd = timeEnd
    )
}