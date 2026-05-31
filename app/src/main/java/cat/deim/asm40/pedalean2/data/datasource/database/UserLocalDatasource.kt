package cat.deim.asm40.pedalean2.data.datasource.database

import com.pedalean2.common.datasource.local.model.UserModel
import com.pedalean2.common.interfaces.IDatasource
import cat.deim.asm40.pedalean2.data.datasource.database.model.UserDTO

class UserLocalDatasource(private val userDatasource: UserDatasource) : IDatasource<UserModel> {

    override fun getAll(): List<UserModel> {
        return userDatasource.getAll().map { it.toModel() }
    }

    override fun getById(uuid: String): UserModel? {
        return userDatasource.getByUuid(uuid)?.toModel()
    }

    override fun insert(dataModel: UserModel): Boolean {
        return try {
            userDatasource.insert(UserDTO.fromModel(dataModel))
            true
        } catch (e: Exception) { false }
    }

    override fun update(dataModel: UserModel): Boolean {
        return try {
            userDatasource.update(UserDTO.fromModel(dataModel))
            true
        } catch (e: Exception) { false }
    }

    override fun delete(uuid: String): Boolean {
        return userDatasource.deleteByUuid(uuid) > 0
    }

    fun getByEmail(email: String): UserModel? {
        return userDatasource.getByEmail(email)?.toModel()
    }

    private fun UserDTO.toModel(): UserModel = UserModel(
        uuid = uuid, name = name, userName = userName, email = email,
        courseGroup = courseGroup, phoneNumber = phoneNumber, birthDate = birthDate,
        isInRenting = isInRenting, totalRentingTime = totalRentingTime,
        totalRents = totalRents, creditCardNumber = creditCardNumber,
        creditCardCvv = creditCardCvv,
        creditCardExpirationDateMonth = creditCardExpirationDateMonth,
        creditCardExpirationDateYear = creditCardExpirationDateYear
    )
}