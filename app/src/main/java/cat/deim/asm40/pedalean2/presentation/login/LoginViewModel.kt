package cat.deim.asm40.pedalean2.presentation.login

import cat.deim.asm40.pedalean2.data.repository.UserRepository
import cat.deim.asm40.pedalean2.domain.usecase.Credentials
import cat.deim.asm40.pedalean2.domain.usecase.LoginUseCase


//testeig Credentials + loginUseCase
fun performLogin(email: String, pass: String, loginUseCase: LoginUseCase) {


    val myCredentials = Credentials(email, pass)
    val result = loginUseCase.execute(myCredentials)

    if (result != null) {
        println("Login exitoso para: ${result.name}")
    } else {
        println("Error de credenciales")
    }
}