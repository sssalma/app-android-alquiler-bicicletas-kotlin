package cat.deim.asm40.pedalean2.domain.models

data class User(
    val uuid: String,
    val name: String,
    val userName: String,
    val email: String,
    val courseGroup: String,
    val phoneNumber: String,
    val birthDate: String,
    val isInRenting: Boolean,
    val totalRentingTime: Int,
    val totalRents: Int,
    val creditCardNumber: String,
    val creditCardCvv: Int,
    val creditCardExpirationDateMonth: Int,
    val creditCardExpirationDateYear: Int
)