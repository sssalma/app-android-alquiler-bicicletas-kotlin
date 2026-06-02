package cat.deim.asm40.pedalean2.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm40.pedalean2.domain.models.Rent
import cat.deim.asm40.pedalean2.domain.models.User
import cat.deim.asm40.pedalean2.domain.usecase.GetActiveUserUseCase
import cat.deim.asm40.pedalean2.domain.usecase.GetUserRentsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val getActiveUserUseCase: GetActiveUserUseCase,
    private val getUserRentsUseCase: GetUserRentsUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _rents = MutableStateFlow<List<Rent>>(emptyList())
    val rents: StateFlow<List<Rent>> = _rents

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val userResult = getActiveUserUseCase.execute()
            val rentsResult = getUserRentsUseCase.execute()
            withContext(Dispatchers.Main) {
                _user.value = userResult
                _rents.value = rentsResult
            }
        }
    }
}