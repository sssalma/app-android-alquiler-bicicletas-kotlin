package cat.deim.asm40.pedalean2.presentation.bikelist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm40.pedalean2.data.repository.BikeRepository
import cat.deim.asm40.pedalean2.domain.usecase.GetAllBikesUseCase
import cat.deim.asm40.pedalean2.presentation.profile.ProfileActivity
import cat.deim.asm40.pedalean2.ui.theme.ASM40Theme
import com.pedalean2.common.factory.DatasourceFactory

class BikeListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val datasource = DatasourceFactory.getInstance().createBikeDatasource()
        val repo = BikeRepository(datasource)
        val useCase = GetAllBikesUseCase(repo)

        val viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BikeListViewModel(useCase) as T
            }
        })[BikeListViewModel::class.java]

        setContent {
            ASM40Theme {
                BikeListScreen(
                    viewModel = viewModel,
                    onProfileClick = {
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}