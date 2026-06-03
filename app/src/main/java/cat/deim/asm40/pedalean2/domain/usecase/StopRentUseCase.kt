// StopRentUseCase.kt
package cat.deim.asm40.pedalean2.domain.usecase

import cat.deim.asm40.pedalean2.domain.repository.IRentRepository

class StopRentUseCase(private val rentRepository: IRentRepository) {
    suspend fun execute(bikeUuid: String, latitude: Double, longitude: Double): Boolean =
        rentRepository.stopRent(bikeUuid, latitude, longitude)
}