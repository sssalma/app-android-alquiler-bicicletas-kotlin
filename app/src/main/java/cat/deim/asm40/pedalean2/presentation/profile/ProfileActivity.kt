package cat.deim.asm40.pedalean2.presentation.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cat.deim.asm40.pedalean2.data.datasource.api.RetrofitClient
import cat.deim.asm40.pedalean2.data.datasource.api.RentRemoteDatasource
import cat.deim.asm40.pedalean2.data.datasource.api.UserRemoteDatasource
import cat.deim.asm40.pedalean2.data.datasource.api.TokenStorage
import cat.deim.asm40.pedalean2.data.datasource.database.AppDatabase
import cat.deim.asm40.pedalean2.data.datasource.database.RentLocalDatasource
import cat.deim.asm40.pedalean2.data.datasource.database.UserLocalDatasource
import cat.deim.asm40.pedalean2.data.repository.RentRepository
import cat.deim.asm40.pedalean2.data.repository.UserRepository
import cat.deim.asm40.pedalean2.domain.usecase.GetActiveUserUseCase
import cat.deim.asm40.pedalean2.domain.usecase.GetUserRentsUseCase
import cat.deim.asm40.pedalean2.domain.usecase.UpdateUserUseCase
import cat.deim.asm40.pedalean2.ui.theme.ASM40Theme

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getInstance(applicationContext)
        val tokenStorage = TokenStorage(applicationContext)
        val apiService = RetrofitClient.apiService
        val serverToken = RetrofitClient.SERVER_TOKEN

        val userRepository = UserRepository(
            localDatasource = UserLocalDatasource(db.userDatasource()),
            remoteDatasource = UserRemoteDatasource(apiService, tokenStorage, serverToken)
        )
        val rentRepository = RentRepository(
            localDatasource = RentLocalDatasource(db.rentDatasource()),
            remoteDatasource = RentRemoteDatasource(apiService, tokenStorage, serverToken)
        )

        val viewModel = ProfileViewModel(
            getActiveUserUseCase = GetActiveUserUseCase(userRepository),
            getUserRentsUseCase = GetUserRentsUseCase(rentRepository),
            updateUserUseCase = UpdateUserUseCase(userRepository)
        )

        setContent {
            ASM40Theme {
                ProfileScreen(
                    viewModel = viewModel,
                    onBackClick = { finish() }
                )
            }
        }
    }
}