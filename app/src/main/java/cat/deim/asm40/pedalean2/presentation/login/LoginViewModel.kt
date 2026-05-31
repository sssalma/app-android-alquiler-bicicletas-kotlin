package cat.deim.asm40.pedalean2.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.deim.asm40.pedalean2.domain.usecase.Credentials
import cat.deim.asm40.pedalean2.domain.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    // Campos de texto del formulario
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    // Estado de la operación de login
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChanged(newValue: String) {
        _email.value = newValue
    }

    fun onPasswordChanged(newValue: String) {
        _password.value = newValue
    }

    fun isLoginEnabled(): Boolean {
        return _email.value.isNotBlank()
                && _password.value.isNotBlank()
                && _email.value.contains("@")
    }

    /**
     * Ejecuta el login en una coroutine de IO.
     * Actualiza el estado del UI según el resultado.
     */
    fun performLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = LoginUiState.Loading
            val credentials = Credentials(_email.value, _password.value)
            val user = loginUseCase.execute(credentials)
            _uiState.value = if (user != null) {
                LoginUiState.Success
            } else {
                LoginUiState.Error("Credenciales inválidas. Comprueba usuario y contraseña.")
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}
