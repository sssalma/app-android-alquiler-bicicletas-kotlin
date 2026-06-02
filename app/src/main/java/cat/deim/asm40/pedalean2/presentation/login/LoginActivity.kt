package cat.deim.asm40.pedalean2.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm40.pedalean2.data.datasource.api.RetrofitClient
import cat.deim.asm40.pedalean2.data.datasource.api.TokenStorage
import cat.deim.asm40.pedalean2.data.datasource.api.UserRemoteDatasource
import cat.deim.asm40.pedalean2.data.datasource.database.AppDatabase
import cat.deim.asm40.pedalean2.data.datasource.database.UserLocalDatasource
import cat.deim.asm40.pedalean2.data.repository.UserRepository
import cat.deim.asm40.pedalean2.domain.usecase.LoginUseCase
import cat.deim.asm40.pedalean2.presentation.bikelist.BikeListActivity
import cat.deim.asm40.pedalean2.ui.theme.ASM40Theme

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(applicationContext)
        val tokenStorage = TokenStorage(applicationContext)
        val apiService = RetrofitClient.apiService
        val serverToken = RetrofitClient.SERVER_TOKEN

        val userRepository = UserRepository(
            localDatasource = UserLocalDatasource(db.userDatasource()),
            remoteDatasource = UserRemoteDatasource(apiService, tokenStorage, serverToken)
        )
        val loginUseCase = LoginUseCase(userRepository)

        val viewModel: LoginViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(loginUseCase) as T
            }
        })[LoginViewModel::class.java]

        setContent {
            ASM40Theme {
                LoginScreen(
                    viewModel = viewModel,
                    onLoginSuccess = {
                        startActivity(Intent(this, BikeListActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}
