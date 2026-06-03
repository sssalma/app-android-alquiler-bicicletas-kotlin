package cat.deim.asm40.pedalean2.data.repository

import com.pedalean2.common.datasource.local.model.BikeModel
import com.pedalean2.common.interfaces.IDatasource
import cat.deim.asm40.pedalean2.domain.models.Bike
import cat.deim.asm40.pedalean2.domain.repository.IBikeRepository

class BikeRepository(
    private val localDatasource: IDatasource<BikeModel>,
    private val remoteDatasource: IDatasource<BikeModel>
) : IBikeRepository {

    override fun getAll(): List<Bike> {
        return try {
            // Red disponible: el remoto manda. Traemos lo fresco y actualizamos la cache.
            val remotes = remoteDatasource.getAll()
            if (remotes.isNotEmpty()) {
                localDatasource.getAll().forEach { localDatasource.delete(it.uuid) }
                remotes.forEach { localDatasource.insert(it) }
                remotes.map { it.toDomain() }
            } else {
                // Remoto vacío (p. ej. error de red): caemos a la cache local
                localDatasource.getAll().map { it.toDomain() }
            }
        } catch (e: Exception) {
            // Sin red: respaldo local
            localDatasource.getAll().map { it.toDomain() }
        }
    }

    override fun getByUuid(uuid: String): Bike? {
        return try {
            val remote = remoteDatasource.getById(uuid)
            if (remote != null) {
                localDatasource.insert(remote)   // actualiza/inserta el registro fresco
                remote.toDomain()
            } else {
                localDatasource.getById(uuid)?.toDomain()
            }
        } catch (e: Exception) {
            localDatasource.getById(uuid)?.toDomain()
        }
    }
    override fun insertAll(bikes: List<Bike>): Int {
        var count = 0
        bikes.forEach { if (localDatasource.insert(it.toModel())) count++ }
        return count
    }

    override fun updateAll(bikes: List<Bike>): Int {
        var count = 0
        bikes.forEach { if (localDatasource.update(it.toModel())) count++ }
        return count
    }

    override fun deleteAll(): Int {
        return localDatasource.getAll().count { localDatasource.delete(it.uuid) }
    }

    override fun insert(bike: Bike): Boolean = localDatasource.insert(bike.toModel())
    override fun update(bike: Bike): Boolean = localDatasource.update(bike.toModel())
    override fun delete(uuid: String): Boolean = localDatasource.delete(uuid)

    private fun BikeModel.toDomain(): Bike = Bike(
        uuid = uuid, id = id, name = name, type = type,
        batteryLevel = batteryLevel, meters = meters,
        isRented = isRented, isReserved = isReserved,
        latitude = latitude, longitude = longitude,
        lastMaintenance = lastMaintenance, lastUse = lastUse
    )

    private fun Bike.toModel(): BikeModel = BikeModel(
        uuid = uuid, id = id, name = name, type = type,
        batteryLevel = batteryLevel, meters = meters,
        isRented = isRented, isReserved = isReserved,
        latitude = latitude, longitude = longitude,
        lastMaintenance = lastMaintenance, lastUse = lastUse
    )
}