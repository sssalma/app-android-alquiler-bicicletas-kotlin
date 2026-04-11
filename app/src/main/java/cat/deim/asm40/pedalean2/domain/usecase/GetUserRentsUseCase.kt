package cat.deim.asm40.pedalean2.domain.usecase

import cat.deim.asm40.pedalean2.domain.models.Rent
import cat.deim.asm40.pedalean2.domain.repository.IRentRepository

class GetUserRentsUseCase(private val rentRepository: IRentRepository) {

    fun execute(): List<Rent> {
        return rentRepository.getAllRents()
    }
}