package cat.deim.asm40.pedalean2.presentation.bikelist
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cat.deim.asm40.pedalean2.domain.models.Bike

@Composable
fun BikeCardItem(bike: Bike) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = bike.name, style = MaterialTheme.typography.titleLarge)
            Text(text = "Tipo: ${bike.type}", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = if (bike.isRented) "Alquilada" else "Disponible",
                color = if (!bike.isReserved) Color.Green else Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}