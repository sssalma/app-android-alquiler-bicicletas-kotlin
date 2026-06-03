package cat.deim.asm40.pedalean2.presentation.bikelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm40.pedalean2.data.datasource.api.BikeRemoteDatasource
import cat.deim.asm40.pedalean2.data.datasource.api.RentRemoteDatasource
import cat.deim.asm40.pedalean2.data.datasource.api.RetrofitClient
import cat.deim.asm40.pedalean2.data.datasource.api.TokenStorage
import cat.deim.asm40.pedalean2.data.datasource.database.AppDatabase
import cat.deim.asm40.pedalean2.data.datasource.database.BikeLocalDatasource
import cat.deim.asm40.pedalean2.data.datasource.database.RentLocalDatasource
import cat.deim.asm40.pedalean2.data.repository.BikeRepository
import cat.deim.asm40.pedalean2.data.repository.RentRepository
import cat.deim.asm40.pedalean2.domain.usecase.GetBikeByUuidUseCase
import cat.deim.asm40.pedalean2.domain.usecase.StartRentUseCase
import cat.deim.asm40.pedalean2.domain.usecase.StopRentUseCase
import cat.deim.asm40.pedalean2.ui.theme.ASM40Theme

class BikeDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uuid = intent.getStringExtra("bike_uuid") ?: ""

        val db = AppDatabase.getInstance(applicationContext)
        val tokenStorage = TokenStorage(applicationContext)
        val apiService = RetrofitClient.apiService
        val serverToken = RetrofitClient.SERVER_TOKEN

        val repository = BikeRepository(
            localDatasource = BikeLocalDatasource(db.bikeDatasource()),
            remoteDatasource = BikeRemoteDatasource(apiService, tokenStorage, serverToken)
        )
        val getBikeUseCase = GetBikeByUuidUseCase(repository)

        val rentRepository = RentRepository(
            localDatasource = RentLocalDatasource(db.rentDatasource()),
            remoteDatasource = RentRemoteDatasource(apiService, tokenStorage, serverToken)
        )

        val startRentUseCase = StartRentUseCase(rentRepository)
        val stopRentUseCase = StopRentUseCase(rentRepository)

        val viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return BikeDetailViewModel(
                    getBikeUseCase,
                    startRentUseCase,
                    stopRentUseCase
                ) as T
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