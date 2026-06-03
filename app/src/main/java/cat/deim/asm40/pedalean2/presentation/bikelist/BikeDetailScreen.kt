package cat.deim.asm40.pedalean2.presentation.bikelist

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cat.deim.asm40.pedalean2.presentation.util.toReadableDateTime
import cat.deim.asm40.pedalean2.ui.theme.PedaleanYellowLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeDetailScreen(
    viewModel: BikeDetailViewModel,
    onBackClick: () -> Unit
) {
    val bike by viewModel.bike.collectAsState()

    // NUEVO: Variables para mostrar los mensajes de Toast
    val message by viewModel.message.collectAsState()
    val context = LocalContext.current

    // NUEVO: Escuchador para mostrar el Toast cuando el mensaje cambie
    LaunchedEffect(message) {
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pedalean2") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        bike?.let { b ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Urban bike",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = PedaleanYellowLight
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = if (!b.isRented) "Available" else "Rented",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (!b.isRented) Color(0xFF4CAF50) else Color(0xFFE53935),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = b.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }

                            // NUEVO: Surface ahora es clickable y llama a toggleRent
                            Surface(
                                onClick = { viewModel.toggleRent(b) },
                                shape = RoundedCornerShape(50),
                                color = if (!b.isRented) Color(0xFF4CAF50) else Color(0xFFE53935)
                            ) {
                                Text(
                                    text = if (!b.isRented) "Rent" else "Occupied",
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = Color.Black.copy(alpha = 0.1f))
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color.Black.copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${b.batteryLevel}%",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Place,
                                contentDescription = null,
                                tint = Color.Black.copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${b.meters} m",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Last use: ",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black.copy(alpha = 0.6f)
                            )
                            Text(
                                text = b.lastUse.toReadableDateTime(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}