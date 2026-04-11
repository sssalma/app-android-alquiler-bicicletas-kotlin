package cat.deim.asm40.pedalean2.data.repository

import com.pedalean2.common.datasource.local.model.BikeModel
import com.pedalean2.common.interfaces.IDatasource
import cat.deim.asm40.pedalean2.domain.models.Bike
import cat.deim.asm40.pedalean2.domain.repository.IBikeRepository

class BikeRepository(private val localDatasource: IDatasource<BikeModel>) : IBikeRepository {

    override fun insertAll(bikes: List<Bike>): Int {
        var count = 0
        bikes.forEach { bike ->
            if (localDatasource.insert(bike.toModel())) count++
        }
        return count
    }

    override fun getAll(): List<Bike> {
        return localDatasource.getAll().map { it.toDomain() }
    }

    override fun updateAll(bikes: List<Bike>): Int {
        var count = 0
        bikes.forEach { bike ->
            if (localDatasource.update(bike.toModel())) count++
        }
        return count
    }

    override fun deleteAll(): Int {
        return localDatasource.getAll().count { localDatasource.delete(it.uuid) }
    }

    override fun insert(bike: Bike): Boolean {
        return localDatasource.insert(bike.toModel())
    }

    override fun getByUuid(uuid: String): Bike? {
        return localDatasource.getById(uuid)?.toDomain()
    }

    override fun update(bike: Bike): Boolean {
        return localDatasource.update(bike.toModel())
    }

    override fun delete(uuid: String): Boolean {
        return localDatasource.delete(uuid)
    }

    private fun BikeModel.toDomain(): Bike {
        return Bike(
            uuid = this.uuid,
            id = this.id,
            name = this.name,
            type = this.type,
            batteryLevel = this.batteryLevel,
            meters = this.meters,
            isRented = this.isRented,
            isReserved = this.isReserved,
            latitude = this.latitude,
            longitude = this.longitude,
            lastMaintenance = this.lastMaintenance,
            lastUse = this.lastUse
        )
    }

    private fun Bike.toModel(): BikeModel {
        return BikeModel(
            uuid = this.uuid,
            id = this.id,
            name = this.name,
            type = this.type,
            batteryLevel = this.batteryLevel,
            meters = this.meters,
            isRented = this.isRented,
            isReserved = this.isReserved,
            latitude = this.latitude,
            longitude = this.longitude,
            lastMaintenance = this.lastMaintenance,
            lastUse = this.lastUse
        )
    }
}