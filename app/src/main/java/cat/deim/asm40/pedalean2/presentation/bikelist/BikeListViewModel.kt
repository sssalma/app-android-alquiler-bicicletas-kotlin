package cat.deim.asm40.pedalean2.presentation.bikelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm40.pedalean2.domain.models.Bike
import cat.deim.asm40.pedalean2.domain.usecase.GetAllBikesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BikeListViewModel(
    private val getAllBikesUseCase: GetAllBikesUseCase
) : ViewModel() {
    private val _bikes = MutableStateFlow<List<Bike>>(emptyList())
    val bikes: StateFlow<List<Bike>> = _bikes.asStateFlow()

    init {
        loadBikes()
    }

    private fun loadBikes() { //llamada al UseCase
        viewModelScope.launch {
            val result = getAllBikesUseCase.execute()
            _bikes.value = result
        }
    }
}