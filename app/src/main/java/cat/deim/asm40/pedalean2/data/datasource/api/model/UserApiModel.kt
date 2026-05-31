package cat.deim.asm40.pedalean2.data.datasource.api.model


import cat.deim.asm40.pedalean2.domain.models.User
import com.google.gson.annotations.SerializedName

data class UserApiModel(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val userName: String,
    @SerializedName("email") val email: String,
    @SerializedName("course_group") val courseGroup: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("birth_date") val birthDate: String,
    @SerializedName("is_in_renting") val isInRenting: Boolean,
    @SerializedName("total_renting_time") val totalRentingTime: Int,
    @SerializedName("total_rents") val totalRents: Int,
    @SerializedName("credit_card_number") val creditCardNumber: String,
    @SerializedName("credit_card_cvv") val creditCardCvv: Int,
    @SerializedName("credit_card_expiration_date_month") val creditCardExpirationDateMonth: Int,
    @SerializedName("credit_card_expiration_date_year") val creditCardExpirationDateYear: Int
) {
    fun toDomain(): User = User(
        uuid = uuid,
        name = name,
        userName = userName,
        email = email,
        courseGroup = courseGroup,
        phoneNumber = phoneNumber,
        birthDate = birthDate,
        isInRenting = isInRenting,
        totalRentingTime = totalRentingTime,
        totalRents = totalRents,
        creditCardNumber = creditCardNumber,
        creditCardCvv = creditCardCvv,
        creditCardExpirationDateMonth = creditCardExpirationDateMonth,
        creditCardExpirationDateYear = creditCardExpirationDateYear
    )
}
