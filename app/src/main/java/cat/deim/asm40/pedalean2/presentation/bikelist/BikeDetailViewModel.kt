package cat.deim.asm40.pedalean2.presentation.bikelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm40.pedalean2.domain.models.Bike
import cat.deim.asm40.pedalean2.domain.usecase.GetBikeByUuidUseCase
import cat.deim.asm40.pedalean2.domain.usecase.StartRentUseCase
import cat.deim.asm40.pedalean2.domain.usecase.StopRentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BikeDetailViewModel(
    private val getBikeByUuidUseCase: GetBikeByUuidUseCase,
    private val startRentUseCase: StartRentUseCase,
    private val stopRentUseCase: StopRentUseCase
) : ViewModel() {

    private val _bike = MutableStateFlow<Bike?>(null)
    val bike: StateFlow<Bike?> = _bike

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    fun loadBike(uuid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getBikeByUuidUseCase.execute(uuid)
            withContext(Dispatchers.Main) { _bike.value = result }
        }
    }

    fun toggleRent(bike: Bike) {
        viewModelScope.launch(Dispatchers.IO) {
            val ok = if (!bike.isRented) {
                startRentUseCase.execute(bike.uuid, bike.latitude, bike.longitude)
            } else {
                stopRentUseCase.execute(bike.uuid, bike.latitude, bike.longitude)
            }
            withContext(Dispatchers.Main) {
                if (ok) {
                    _bike.value = bike.copy(isRented = !bike.isRented)
                    _message.value = if (!bike.isRented) "Alquiler iniciado" else "Alquiler finalizado"
                } else {
                    _message.value = "No se pudo completar la operación"
                }
            }
        }
    }

    fun clearMessage() { _message.value = null }
}