package cat.deim.asm40.pedalean2.data.datasource.database

import com.pedalean2.common.datasource.local.model.BikeModel
import com.pedalean2.common.interfaces.IDatasource
import cat.deim.asm40.pedalean2.data.datasource.database.model.BikeDTO

class BikeLocalDatasource(private val bikeDatasource: BikeDatasource) : IDatasource<BikeModel> {

    override fun getAll(): List<BikeModel> {
        return bikeDatasource.getAll().map { it.toModel() }
    }

    override fun getById(uuid: String): BikeModel? {
        return bikeDatasource.getByUuid(uuid)?.toModel()
    }

    override fun insert(dataModel: BikeModel): Boolean {
        return try {
            bikeDatasource.insert(BikeDTO.fromModel(dataModel))
            true
        } catch (e: Exception) { false }
    }

    override fun update(dataModel: BikeModel): Boolean {
        return try {
            bikeDatasource.update(BikeDTO.fromModel(dataModel))
            true
        } catch (e: Exception) { false }
    }

    override fun delete(uuid: String): Boolean {
        return bikeDatasource.deleteByUuid(uuid) > 0
    }

    private fun BikeDTO.toModel(): BikeModel = BikeModel(
        uuid = uuid, id = id, name = name, type = type,
        batteryLevel = batteryLevel, meters = meters,
        isRented = isRented, isReserved = isReserved,
        latitude = latitude, longitude = longitude,
        lastMaintenance = lastMaintenance, lastUse = lastUse
    )
}