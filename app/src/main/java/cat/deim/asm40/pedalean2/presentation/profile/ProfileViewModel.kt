package cat.deim.asm40.pedalean2.presentation.profile

import androidx.lifecycle.ViewModel
import cat.deim.asm40.pedalean2.domain.models.Rent
import cat.deim.asm40.pedalean2.domain.models.User
import cat.deim.asm40.pedalean2.domain.usecase.GetActiveUserUseCase
import cat.deim.asm40.pedalean2.domain.usecase.GetUserRentsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel(
    private val getActiveUserUseCase: GetActiveUserUseCase,
    private val getUserRentsUseCase: GetUserRentsUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _rents = MutableStateFlow<List<Rent>>(emptyList())
    val rents: StateFlow<List<Rent>> = _rents

    fun loadData() {
        _user.value = getActiveUserUseCase.execute()
        _rents.value = getUserRentsUseCase.execute()
    }
}