package cat.deim.asm40.pedalean2.data.repository

import cat.deim.asm40.pedalean2.data.datasource.api.IUserRemoteDatasource
import cat.deim.asm40.pedalean2.domain.models.Credentials
import com.pedalean2.common.datasource.local.model.UserModel
import com.pedalean2.common.interfaces.IDatasource
import cat.deim.asm40.pedalean2.domain.models.User
import cat.deim.asm40.pedalean2.domain.repository.IUserRepository

class UserRepository(
    private val localDatasource: IDatasource<UserModel>,
    private val remoteDatasource: IUserRemoteDatasource
) : IUserRepository {

    override fun getUserById(uuid: String): User {
        val local = localDatasource.getById(uuid)
        if (local != null) return local.toDomain()
        val remote = remoteDatasource.getById(uuid)
            ?: throw Exception("User not found")
        localDatasource.insert(remote)
        return remote.toDomain()
    }

    override fun getActiveUser(): User {
        val locals = localDatasource.getAll()
        if (locals.isNotEmpty()) return locals.first().toDomain()
        val remotes = remoteDatasource.getAll()
        if (remotes.isNotEmpty()) {
            remotes.forEach { localDatasource.insert(it) }
            return remotes.first().toDomain()
        }
        throw Exception("No active user found")
    }

    override fun setActiveUser(user: User) {
        localDatasource.insert(user.toModel())
    }

    override fun updateActiveUser(user: User) {
        localDatasource.update(user.toModel())
    }

    override fun deleteActiveUser(user: User) {
        localDatasource.delete(user.uuid)
    }

    override suspend fun login(credentials: Credentials): User? {
        val ok = remoteDatasource.login(credentials.email, credentials.password)
        if (!ok) return null
        val userModel = remoteDatasource.getAll().firstOrNull() ?: return null
        localDatasource.insert(userModel)
        return userModel.toDomain()
    }

    private fun UserModel.toDomain(): User = User(
        uuid = uuid, name = name, userName = userName, email = email,
        courseGroup = courseGroup, phoneNumber = phoneNumber, birthDate = birthDate,
        isInRenting = isInRenting, totalRentingTime = totalRentingTime,
        totalRents = totalRents, creditCardNumber = creditCardNumber,
        creditCardCvv = creditCardCvv,
        creditCardExpirationDateMonth = creditCardExpirationDateMonth,
        creditCardExpirationDateYear = creditCardExpirationDateYear
    )

    private fun User.toModel(): UserModel = UserModel(
        uuid = uuid, name = name, userName = userName, email = email,
        courseGroup = courseGroup, phoneNumber = phoneNumber, birthDate = birthDate,
        isInRenting = isInRenting, totalRentingTime = totalRentingTime,
        totalRents = totalRents, creditCardNumber = creditCardNumber,
        creditCardCvv = creditCardCvv,
        creditCardExpirationDateMonth = creditCardExpirationDateMonth,
        creditCardExpirationDateYear = creditCardExpirationDateYear
    )
}