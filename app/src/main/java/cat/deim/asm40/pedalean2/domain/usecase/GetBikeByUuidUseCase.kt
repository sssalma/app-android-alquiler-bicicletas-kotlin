package cat.deim.asm40.pedalean2.domain.usecase

import cat.deim.asm40.pedalean2.domain.models.Bike
import cat.deim.asm40.pedalean2.domain.repository.IBikeRepository

class GetBikeByUuidUseCase(private val bikeRepository: IBikeRepository) {

    fun execute(uuid: String): Bike? {
        return bikeRepository.getByUuid(uuid)
    }
}