package cat.deim.asm40.pedalean2.domain.usecase

import cat.deim.asm40.pedalean2.data.datasource.api.UserRemoteDatasource
import cat.deim.asm40.pedalean2.data.datasource.database.UserLocalDatasource
import cat.deim.asm40.pedalean2.domain.models.User
import cat.deim.asm40.pedalean2.domain.repository.IUserRepository

/**
 * LoginUseCase gestiona el flujo de autenticación:
 * 1. Llama al servidor para obtener el token JWT.
 * 2. Si el servidor responde OK, descarga el perfil del usuario.
 * 3. Guarda el usuario en la base de datos local (Room).
 * 4. Retorna el User de dominio si todo va bien, null si falla.
 *
 * Es suspend porque realiza operaciones de red (que deben correr en IO).
 */
class LoginUseCase(
    private val userRemoteDatasource: UserRemoteDatasource,
    private val userLocalDatasource: UserLocalDatasource
) {

    suspend fun execute(credentials: Credentials): User? {
        // 1. Autenticar: obtener tokens del servidor
        val loginOk = userRemoteDatasource.login(
            username = credentials.email,
            password = credentials.password
        )
        if (!loginOk) return null

        // 2. Descargar perfil del usuario desde el servidor
        val user = userRemoteDatasource.getUserDomain() ?: return null

        // 3. Persistir usuario en Room (insert o update)
        val existing = userLocalDatasource.getById(user.uuid)
        if (existing == null) {
            userLocalDatasource.insert(user.toUserModel())
        } else {
            userLocalDatasource.update(user.toUserModel())
        }

        return user
    }

    private fun User.toUserModel(): com.pedalean2.common.datasource.local.model.UserModel {
        return com.pedalean2.common.datasource.local.model.UserModel(
            uuid = uuid,
            name = name,
            userName = userName,
            email = email,
            courseGroup = courseGroup,
            phoneNumber = phoneNumber,
            birthDate = birthDate,
            isInRenting = isInRenting,
            totalRentingTime = totalRentingTime,
            totalRents = totalRents,
            creditCardNumber = creditCardNumber,
            creditCardCvv = creditCardCvv,
            creditCardExpirationDateMonth = creditCardExpirationDateMonth,
            creditCardExpirationDateYear = creditCardExpirationDateYear
        )
    }
}
