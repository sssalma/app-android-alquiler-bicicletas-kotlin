package cat.deim.asm40.pedalean2.data.repository

import com.pedalean2.common.datasource.local.model.UserModel
import com.pedalean2.common.interfaces.IDatasource
import cat.deim.asm40.pedalean2.domain.models.User
import cat.deim.asm40.pedalean2.domain.repository.IUserRepository

class UserRepository(private val localDatasource: IDatasource<UserModel>) : IUserRepository {

    override fun getUserById(uuid: String): User {
        return localDatasource.getById(uuid)?.toDomain()
            ?: throw Exception("User not found")
    }

    override fun getActiveUser(): User {
        return localDatasource.getAll().first().toDomain()
    }

    override fun setActiveUser(user: User) {
        localDatasource.update(user.toModel())
    }

    override fun updateActiveUser(user: User) {
        localDatasource.update(user.toModel())
    }

    override fun deleteActiveUser(user: User) {
        localDatasource.delete(user.uuid)
    }

    private fun UserModel.toDomain(): User {
        return User(
            uuid = this.uuid,
            name = this.name,
            userName = this.userName,
            email = this.email,
            courseGroup = this.courseGroup,
            phoneNumber = this.phoneNumber,
            birthDate = this.birthDate,
            isInRenting = this.isInRenting,
            totalRentingTime = this.totalRentingTime,
            totalRents = this.totalRents,
            creditCardNumber = this.creditCardNumber,
            creditCardCvv = this.creditCardCvv,
            creditCardExpirationDateMonth = this.creditCardExpirationDateMonth,
            creditCardExpirationDateYear = this.creditCardExpirationDateYear
        )
    }

    private fun User.toModel(): UserModel {
        return UserModel(
            uuid = this.uuid,
            name = this.name,
            userName = this.userName,
            email = this.email,
            courseGroup = this.courseGroup,
            phoneNumber = this.phoneNumber,
            birthDate = this.birthDate,
            isInRenting = this.isInRenting,
            totalRentingTime = this.totalRentingTime,
            totalRents = this.totalRents,
            creditCardNumber = this.creditCardNumber,
            creditCardCvv = this.creditCardCvv,
            creditCardExpirationDateMonth = this.creditCardExpirationDateMonth,
            creditCardExpirationDateYear = this.creditCardExpirationDateYear
        )
    }
}