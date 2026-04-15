package cat.deim.asm40.pedalean2.presentation.bikelist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeListScreen(viewModel: BikeListViewModel, onProfileClick: () -> Unit, onBikeClick: (String) -> Unit) {
    // StateFlow como estado de Compose
    val bikes by viewModel.bikes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pedalean2 - Bicis") },
                actions = {
                    IconButton(onClick = onProfileClick) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(bikes) { bike ->
                BikeCardItem(
                    bike = bike,
                    onClick = { onBikeClick(bike.uuid) }
                )
            }
        }
    }
}