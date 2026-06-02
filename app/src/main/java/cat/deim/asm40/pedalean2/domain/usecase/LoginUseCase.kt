package cat.deim.asm40.pedalean2.domain.usecase

import cat.deim.asm40.pedalean2.domain.models.Credentials
import cat.deim.asm40.pedalean2.domain.models.User
import cat.deim.asm40.pedalean2.domain.repository.IUserRepository

class LoginUseCase(private val userRepository: IUserRepository) {
    suspend fun execute(credentials: Credentials): User? =
        userRepository.login(credentials)
}