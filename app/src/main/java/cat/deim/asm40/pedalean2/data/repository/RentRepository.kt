package cat.deim.asm40.pedalean2.data.repository

import com.pedalean2.common.datasource.local.model.RentModel
import com.pedalean2.common.interfaces.IDatasource
import cat.deim.asm40.pedalean2.domain.models.Rent
import cat.deim.asm40.pedalean2.domain.repository.IRentRepository

class RentRepository(private val localDatasource: IDatasource<RentModel>) : IRentRepository {

    override fun getAllRents(): List<Rent> {
        return localDatasource.getAll().map { it.toDomain() }
    }

    override fun createAllRents(rents: List<Rent>): Int {
        var count = 0
        rents.forEach { rent ->
            if (localDatasource.insert(rent.toModel())) count++
        }
        return count
    }

    override fun updateAllRents(rents: List<Rent>): Int {
        var count = 0
        rents.forEach { rent ->
            if (localDatasource.update(rent.toModel())) count++
        }
        return count
    }

    override fun deleteAllRents(): Int {
        return localDatasource.getAll().count { localDatasource.delete(it.uuid) }
    }

    override fun getRentByUuid(uuid: String): Rent? {
        return localDatasource.getById(uuid)?.toDomain()
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

    private fun RentModel.toDomain(): Rent {
        return Rent(
            uuid = this.uuid,
            bikeUuid = this.bike.uuid,
            bikeName = this.bike.name,
            userEmail = this.user.email,
            userUsername = this.user.username,
            userFirstName = this.user.firstName,
            userLastName = this.user.lastName,
            isRented = this.isRented,
            rentMeters = this.rentMeters,
            rentStartLatitude = this.rentStartLatitude,
            rentStartLongitude = this.rentStartLongitude,
            rentTime = this.rentTime,
            timeStart = this.timeStart,
            timeEnd = this.timeEnd
        )
    }

    private fun Rent.toModel(): RentModel {
        return RentModel(
            uuid = this.uuid,
            bike = com.pedalean2.common.datasource.local.model.BikeRentModel(
                uuid = this.bikeUuid,
                name = this.bikeName
            ),
            user = com.pedalean2.common.datasource.local.model.UserRentModel(
                email = this.userEmail,
                username = this.userUsername,
                firstName = this.userFirstName,
                lastName = this.userLastName
            ),
            isRented = this.isRented,
            rentMeters = this.rentMeters,
            rentStartLatitude = this.rentStartLatitude,
            rentStartLongitude = this.rentStartLongitude,
            rentTime = this.rentTime,
            timeStart = this.timeStart,
            timeEnd = this.timeEnd
        )
    }
}