package cat.deim.asm40.pedalean2.presentation.bikelist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.deim.asm40.pedalean2.data.datasource.api.BikeRemoteDatasource
import cat.deim.asm40.pedalean2.data.datasource.api.RetrofitClient
import cat.deim.asm40.pedalean2.data.datasource.api.TokenStorage
import cat.deim.asm40.pedalean2.data.datasource.database.AppDatabase
import cat.deim.asm40.pedalean2.data.datasource.database.BikeLocalDatasource
import cat.deim.asm40.pedalean2.data.repository.BikeRepository
import cat.deim.asm40.pedalean2.domain.usecase.GetAllBikesUseCase
import cat.deim.asm40.pedalean2.presentation.profile.ProfileActivity
import cat.deim.asm40.pedalean2.ui.theme.ASM40Theme

class BikeListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(applicationContext)
        val tokenStorage = TokenStorage(applicationContext)
        val apiService = RetrofitClient.apiService
        val serverToken = RetrofitClient.SERVER_TOKEN

        val repo = BikeRepository(
            localDatasource = BikeLocalDatasource(db.bikeDatasource()),
            remoteDatasource = BikeRemoteDatasource(apiService, tokenStorage, serverToken)
        )
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
                        startActivity(Intent(this, ProfileActivity::class.java))
                    },
                    onBikeClick = { uuid ->
                        val intent = Intent(this, BikeDetailActivity::class.java)
                        intent.putExtra("bike_uuid", uuid)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}