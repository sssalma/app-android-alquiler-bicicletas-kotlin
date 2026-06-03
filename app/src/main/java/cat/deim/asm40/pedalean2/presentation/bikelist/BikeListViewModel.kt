package cat.deim.asm40.pedalean2.presentation.bikelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm40.pedalean2.domain.models.Bike
import cat.deim.asm40.pedalean2.domain.usecase.GetAllBikesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BikeListViewModel(
    private val getAllBikesUseCase: GetAllBikesUseCase
) : ViewModel() {
    private val _bikes = MutableStateFlow<List<Bike>>(emptyList())
    val bikes: StateFlow<List<Bike>> = _bikes.asStateFlow()

    init {
        loadBikes()
    }

    fun loadBikes() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getAllBikesUseCase.execute()
            withContext(Dispatchers.Main) {
                _bikes.value = result
            }
        }
    }
}