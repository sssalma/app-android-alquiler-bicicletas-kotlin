package cat.deim.asm40.pedalean2.data.repository

import cat.deim.asm40.pedalean2.data.datasource.api.IRentRemoteDatasource
import cat.deim.asm40.pedalean2.data.datasource.database.RentLocalDatasource
import com.pedalean2.common.datasource.local.model.BikeRentModel
import com.pedalean2.common.datasource.local.model.RentModel
import com.pedalean2.common.datasource.local.model.UserRentModel
import com.pedalean2.common.interfaces.IDatasource
import cat.deim.asm40.pedalean2.domain.models.Rent
import cat.deim.asm40.pedalean2.domain.repository.IRentRepository

class RentRepository(
    private val localDatasource: IDatasource<RentModel>,
    private val remoteDatasource: IRentRemoteDatasource
) : IRentRepository {

    override fun getAllRents(): List<Rent> {
        return try {
            val remotes = remoteDatasource.getAll()
            if (remotes.isNotEmpty()) {
                localDatasource.getAll().forEach { localDatasource.delete(it.uuid) }
                (localDatasource as? RentLocalDatasource)?.insertAll(remotes)
                    ?: remotes.forEach { localDatasource.insert(it) }
                remotes.map { it.toDomain() }
            } else {
                localDatasource.getAll().map { it.toDomain() }
            }
        } catch (e: Exception) {
            localDatasource.getAll().map { it.toDomain() }
        }
    }

    override fun getRentByUuid(uuid: String): Rent? {
        val local = localDatasource.getById(uuid)
        if (local != null) return local.toDomain()
        val remote = remoteDatasource.getById(uuid) ?: return null
        localDatasource.insert(remote)
        return remote.toDomain()
    }

    override fun createRent(rent: Rent): Boolean {
        return localDatasource.insert(rent.toModel())
    }

    override fun updateRent(rent: Rent): Boolean {
        return localDatasource.update(rent.toModel())
    }

    override fun deleteRent(uuid: String): Boolean {
        return localDatasource.delete(uuid)
    }

    override fun isRentActive(uuid: String): Boolean {
        return localDatasource.getById(uuid)?.isRented ?: false
    }

    override fun createAllRents(rents: List<Rent>): Int {
        var count = 0
        rents.forEach { if (localDatasource.insert(it.toModel())) count++ }
        return count
    }

    override fun updateAllRents(rents: List<Rent>): Int {
        var count = 0
        rents.forEach { if (localDatasource.update(it.toModel())) count++ }
        return count
    }

    override fun deleteAllRents(): Int {
        return localDatasource.getAll().count { localDatasource.delete(it.uuid) }
    }
    override suspend fun startRent(bikeUuid: String, latitude: Double, longitude: Double): Boolean =
        remoteDatasource.startRent(bikeUuid, latitude, longitude)

    override suspend fun stopRent(bikeUuid: String, latitude: Double, longitude: Double): Boolean =
        remoteDatasource.stopRent(bikeUuid, latitude, longitude)
    private fun RentModel.toDomain(): Rent = Rent(
        uuid = uuid,
        bikeUuid = bike.uuid, bikeName = bike.name,
        userEmail = user.email, userUsername = user.username,
        userFirstName = user.firstName, userLastName = user.lastName,
        isRented = isRented, rentMeters = rentMeters,
        rentStartLatitude = rentStartLatitude, rentStartLongitude = rentStartLongitude,
        rentTime = rentTime, timeStart = timeStart, timeEnd = timeEnd
    )

    private fun Rent.toModel(): RentModel = RentModel(
        uuid = uuid,
        bike = BikeRentModel(uuid = bikeUuid, name = bikeName),
        user = UserRentModel(email = userEmail, username = userUsername,
            firstName = userFirstName, lastName = userLastName),
        isRented = isRented, rentMeters = rentMeters,
        rentStartLatitude = rentStartLatitude, rentStartLongitude = rentStartLongitude,
        rentTime = rentTime, timeStart = timeStart, timeEnd = timeEnd
    )
}