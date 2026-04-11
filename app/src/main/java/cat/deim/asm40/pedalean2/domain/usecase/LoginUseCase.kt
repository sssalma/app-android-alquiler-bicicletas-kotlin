package cat.deim.asm40.pedalean2.domain.usecase

import cat.deim.asm40.pedalean2.domain.models.User
import cat.deim.asm40.pedalean2.domain.repository.IUserRepository

class LoginUseCase(private val userRepository: IUserRepository) {

    fun execute(email: String, password: String): User? {
        return try {
            val user = userRepository.getActiveUser()
            if (user.email == email) user else null
        } catch (e: Exception) {
            null
        }
    }
}