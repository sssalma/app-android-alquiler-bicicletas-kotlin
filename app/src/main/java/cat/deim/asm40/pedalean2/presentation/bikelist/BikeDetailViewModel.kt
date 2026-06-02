package cat.deim.asm40.pedalean2.presentation.bikelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm40.pedalean2.domain.models.Bike
import cat.deim.asm40.pedalean2.domain.usecase.GetBikeByUuidUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BikeDetailViewModel(
    private val getBikeByUuidUseCase: GetBikeByUuidUseCase
) : ViewModel() {

    private val _bike = MutableStateFlow<Bike?>(null)
    val bike: StateFlow<Bike?> = _bike

    fun loadBike(uuid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getBikeByUuidUseCase.execute(uuid)
            withContext(Dispatchers.Main) {
                _bike.value = result
            }
        }
    }
}