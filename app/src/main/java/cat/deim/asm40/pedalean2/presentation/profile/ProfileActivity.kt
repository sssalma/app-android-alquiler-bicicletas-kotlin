package cat.deim.asm40.pedalean2.presentation.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cat.deim.asm40.pedalean2.data.repository.RentRepository
import cat.deim.asm40.pedalean2.data.repository.UserRepository
import cat.deim.asm40.pedalean2.domain.usecase.GetActiveUserUseCase
import cat.deim.asm40.pedalean2.domain.usecase.GetUserRentsUseCase
import cat.deim.asm40.pedalean2.ui.theme.ASM40Theme
import com.pedalean2.common.factory.DatasourceFactory

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userRepository = UserRepository(
            DatasourceFactory.getInstance().createUserDatasource()
        )
        val rentRepository = RentRepository(
            DatasourceFactory.getInstance().createRentDatasource()
        )

        val viewModel = ProfileViewModel(
            getActiveUserUseCase = GetActiveUserUseCase(userRepository),
            getUserRentsUseCase = GetUserRentsUseCase(rentRepository)
        )

        setContent {
            ASM40Theme {
                ProfileScreen(viewModel = viewModel)
            }
        }
    }
}