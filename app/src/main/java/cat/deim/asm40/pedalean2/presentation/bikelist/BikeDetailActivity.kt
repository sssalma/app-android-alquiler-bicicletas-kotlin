package cat.deim.asm40.pedalean2.presentation.bikelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm40.pedalean2.data.repository.BikeRepository
import cat.deim.asm40.pedalean2.domain.usecase.GetBikeByUuidUseCase
import cat.deim.asm40.pedalean2.ui.theme.ASM40Theme
import com.pedalean2.common.factory.DatasourceFactory

class BikeDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uuid = intent.getStringExtra("bike_uuid") ?: ""

        val datasource = DatasourceFactory.getInstance().createBikeDatasource()
        val repository = BikeRepository(datasource)
        val useCase = GetBikeByUuidUseCase(repository)

        val viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BikeDetailViewModel(useCase) as T
            }
        })[BikeDetailViewModel::class.java]

        viewModel.loadBike(uuid)

        setContent {
            ASM40Theme {
                BikeDetailScreen(
                    viewModel = viewModel,
                    onBackClick = { finish() }
                )
            }
        }
    }
}