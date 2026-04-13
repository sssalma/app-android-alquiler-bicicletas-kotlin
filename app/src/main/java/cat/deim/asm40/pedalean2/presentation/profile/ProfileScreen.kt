package cat.deim.asm40.pedalean2.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cat.deim.asm40.pedalean2.domain.models.Rent
import cat.deim.asm40.pedalean2.domain.models.User

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {

    val user by viewModel.user.collectAsState()
    val rents by viewModel.rents.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        user?.let { u ->
            item {
                UserInfoCard(user = u)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        item {
            Text(
                text = "Rental History",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(rents) { rent ->
            RentCardItem(rent = rent)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun UserInfoCard(user: User) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = user.name, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = user.email, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = user.userName, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = user.phoneNumber, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = user.birthDate, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = user.courseGroup, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Total rents: ${user.totalRents}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Total renting time: ${user.totalRentingTime} min", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun RentCardItem(rent: Rent) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = rent.bikeName, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Start: ${rent.timeStart}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "End: ${rent.timeEnd}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Distance: ${rent.rentMeters} m", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Duration: ${rent.rentTime} min", style = MaterialTheme.typography.bodyMedium)
        }
    }
}