// StartRentUseCase.kt
package cat.deim.asm40.pedalean2.domain.usecase

import cat.deim.asm40.pedalean2.domain.repository.IRentRepository

class StartRentUseCase(private val rentRepository: IRentRepository) {
    suspend fun execute(bikeUuid: String, latitude: Double, longitude: Double): Boolean =
        rentRepository.startRent(bikeUuid, latitude, longitude)
}