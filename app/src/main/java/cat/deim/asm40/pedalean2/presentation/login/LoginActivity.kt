package cat.deim.asm40.pedalean2.presentation.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm40.pedalean2.data.repository.UserRepository
import cat.deim.asm40.pedalean2.domain.usecase.LoginUseCase
import cat.deim.asm40.pedalean2.ui.theme.ASM40Theme
import com.pedalean2.common.factory.DatasourceFactory

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val datasourceFactory = DatasourceFactory.getInstance() //constr. privado (singleton)
        val userDatasource = datasourceFactory.createUserDatasource()
        val userRepository = UserRepository(userDatasource) //inyecto el datasource en elrepoo
        val loginUseCase = LoginUseCase(userRepository) //preparo el usecase
        //ciclo de vida del viewmodel manualmente pq necesita parámetros
        val viewModel: LoginViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(loginUseCase) as T
            }
        })[LoginViewModel::class.java]

        setContent {
            ASM40Theme { //renderizo y vinculo la pantalla de login al viewModel
                LoginScreen(viewModel = viewModel)
            }
        }
    }
}