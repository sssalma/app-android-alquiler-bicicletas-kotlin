package cat.deim.asm40.pedalean2.domain.usecase

import cat.deim.asm40.pedalean2.domain.models.User
import cat.deim.asm40.pedalean2.domain.repository.IUserRepository

class GetActiveUserUseCase(private val userRepository: IUserRepository) {

    fun execute(): User {
        return userRepository.getActiveUser()
    }
}