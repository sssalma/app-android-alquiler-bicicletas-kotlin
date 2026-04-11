package cat.deim.asm40.pedalean2.domain.models

data class Bike(
    val uuid: String,
    val id: String,
    val name: String,
    val type: String,
    val batteryLevel: Int,
    val meters: Int,
    val isRented: Boolean,
    val isReserved: Boolean,
    val latitude: Double,
    val longitude: Double,
    val lastMaintenance: String,
    val lastUse: String
)