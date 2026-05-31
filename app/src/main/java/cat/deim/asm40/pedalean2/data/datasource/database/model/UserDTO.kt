package cat.deim.asm40.pedalean2.data.datasource.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cat.deim.asm40.pedalean2.domain.models.User

@Entity(tableName = "users")
data class UserDTO(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "course_group") val courseGroup: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "birth_date") val birthDate: String,
    @ColumnInfo(name = "is_in_renting") val isInRenting: Boolean,
    @ColumnInfo(name = "total_renting_time") val totalRentingTime: Int,
    @ColumnInfo(name = "total_rents") val totalRents: Int,
    @ColumnInfo(name = "credit_card_number") val creditCardNumber: String,
    @ColumnInfo(name = "credit_card_cvv") val creditCardCvv: Int,
    @ColumnInfo(name = "credit_card_exp_month") val creditCardExpirationDateMonth: Int,
    @ColumnInfo(name = "credit_card_exp_year") val creditCardExpirationDateYear: Int
) {
    companion object {
        fun fromDomain(user: User): UserDTO = UserDTO(
            uuid = user.uuid,
            name = user.name,
            userName = user.userName,
            email = user.email,
            courseGroup = user.courseGroup,
            phoneNumber = user.phoneNumber,
            birthDate = user.birthDate,
            isInRenting = user.isInRenting,
            totalRentingTime = user.totalRentingTime,
            totalRents = user.totalRents,
            creditCardNumber = user.creditCardNumber,
            creditCardCvv = user.creditCardCvv,
            creditCardExpirationDateMonth = user.creditCardExpirationDateMonth,
            creditCardExpirationDateYear = user.creditCardExpirationDateYear
        )
    }

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