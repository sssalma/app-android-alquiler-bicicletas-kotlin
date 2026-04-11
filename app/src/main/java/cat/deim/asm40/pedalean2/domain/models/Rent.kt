package cat.deim.asm40.pedalean2.domain.models

data class Rent(
    val uuid: String,
    val bikeUuid: String,
    val bikeName: String,
    val userEmail: String,
    val userUsername: String,
    val userFirstName: String,
    val userLastName: String,
    val isRented: Boolean,
    val rentMeters: Int,
    val rentStartLatitude: Double,
    val rentStartLongitude: Double,
    val rentTime: Int,
    val timeStart: String,
    val timeEnd: String
)