package cat.deim.asm40.pedalean2.domain.usecase

import cat.deim.asm40.pedalean2.domain.models.User
import cat.deim.asm40.pedalean2.domain.repository.IUserRepository

class UpdateUserUseCase(private val userRepository: IUserRepository) {
    /** Guarda los cambios del perfil en local (la sincronización con el servidor es de una fase posterior). */
    fun execute(user: User) {
        userRepository.updateActiveUser(user)
    }
}