package cat.deim.asm40.pedalean2.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import cat.deim.asm40.pedalean2.domain.usecase.Credentials
import cat.deim.asm40.pedalean2.domain.usecase.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    // Estado para el campo de Email
    var email by mutableStateOf("")
        private set

    // Estado para el campo de Contraseña.mutableStateOf("")
    // actualiza la pantalla automaticamente mientras se escribe
    var password by mutableStateOf("")
        private set

    // Actualiza el estado desde la View (UI)
    fun onEmailChanged(newValue: String) {
        email = newValue
    }
    // Actualiza el estado desde la View (UI)
    fun onPasswordChanged(newValue: String) {
        password = newValue
    }

    // Lógica para el botón
    fun isLoginEnabled(): Boolean {
        return email.isNotBlank() && password.isNotBlank() && email.contains("@")
    }

    // Función principal para llamar al UseCase
    fun performLogin(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val credentials = Credentials(email, password)
        val user = loginUseCase.execute(credentials)

        if (user != null) {
            onSuccess()
        } else {
            onError("Error: Credenciales inválidas")
        }
    }
}