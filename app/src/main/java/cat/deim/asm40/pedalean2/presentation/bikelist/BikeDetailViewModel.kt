package cat.deim.asm40.pedalean2.presentation.bikelist

import androidx.lifecycle.ViewModel
import cat.deim.asm40.pedalean2.domain.models.Bike
import cat.deim.asm40.pedalean2.domain.usecase.GetBikeByUuidUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BikeDetailViewModel(
    private val getBikeByUuidUseCase: GetBikeByUuidUseCase
) : ViewModel() {

    private val _bike = MutableStateFlow<Bike?>(null)
    val bike: StateFlow<Bike?> = _bike

    fun loadBike(uuid: String) {
        _bike.value = getBikeByUuidUseCase.execute(uuid)
    }
}