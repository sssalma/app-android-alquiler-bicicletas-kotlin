package cat.deim.asm40.pedalean2.domain.usecase

import cat.deim.asm40.pedalean2.data.datasource.api.UserRemoteDatasource
import cat.deim.asm40.pedalean2.data.datasource.database.UserLocalDatasource
import cat.deim.asm40.pedalean2.domain.models.User
import com.pedalean2.common.datasource.local.model.UserModel

class LoginUseCase(
    private val userRemoteDatasource: UserRemoteDatasource,
    private val userLocalDatasource: UserLocalDatasource
) {

    suspend fun execute(credentials: Credentials): User? {
        val loginOk = userRemoteDatasource.login(
            username = credentials.email,
            password = credentials.password
        )
        android.util.Log.d("LoginUseCase", "loginOk: $loginOk")
        if (!loginOk) return null

        val userModel = userRemoteDatasource.getAll().firstOrNull()
        android.util.Log.d("LoginUseCase", "userModel: $userModel")
        if (userModel == null) return null

        val user = userModel.toDomain()
        android.util.Log.d("LoginUseCase", "user: $user")
        return user
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

    private fun User.toUserModel(): UserModel = UserModel(
        uuid = uuid, name = name, userName = userName, email = email,
        courseGroup = courseGroup, phoneNumber = phoneNumber, birthDate = birthDate,
        isInRenting = isInRenting, totalRentingTime = totalRentingTime,
        totalRents = totalRents, creditCardNumber = creditCardNumber,
        creditCardCvv = creditCardCvv,
        creditCardExpirationDateMonth = creditCardExpirationDateMonth,
        creditCardExpirationDateYear = creditCardExpirationDateYear
    )

}