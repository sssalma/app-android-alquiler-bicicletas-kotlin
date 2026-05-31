package cat.deim.asm40.pedalean2.data.datasource.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cat.deim.asm40.pedalean2.domain.models.Rent

@Entity(tableName = "rents")
data class RentDTO(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "bike_uuid") val bikeUuid: String,
    @ColumnInfo(name = "bike_name") val bikeName: String,
    @ColumnInfo(name = "user_email") val userEmail: String,
    @ColumnInfo(name = "user_username") val userUsername: String,
    @ColumnInfo(name = "user_first_name") val userFirstName: String,
    @ColumnInfo(name = "user_last_name") val userLastName: String,
    @ColumnInfo(name = "is_rented") val isRented: Boolean,
    @ColumnInfo(name = "rent_meters") val rentMeters: Int,
    @ColumnInfo(name = "rent_start_latitude") val rentStartLatitude: Double,
    @ColumnInfo(name = "rent_start_longitude") val rentStartLongitude: Double,
    @ColumnInfo(name = "rent_time") val rentTime: Int,
    @ColumnInfo(name = "time_start") val timeStart: String,
    @ColumnInfo(name = "time_end") val timeEnd: String
) {
    companion object {
        fun fromDomain(rent: Rent): RentDTO = RentDTO(
            uuid = rent.uuid,
            bikeUuid = rent.bikeUuid,
            bikeName = rent.bikeName,
            userEmail = rent.userEmail,
            userUsername = rent.userUsername,
            userFirstName = rent.userFirstName,
            userLastName = rent.userLastName,
            isRented = rent.isRented,
            rentMeters = rent.rentMeters,
            rentStartLatitude = rent.rentStartLatitude,
            rentStartLongitude = rent.rentStartLongitude,
            rentTime = rent.rentTime,
            timeStart = rent.timeStart,
            timeEnd = rent.timeEnd
        )
    }

    fun toDomain(): Rent = Rent(
        uuid = uuid,
        bikeUuid = bikeUuid,
        bikeName = bikeName,
        userEmail = userEmail,
        userUsername = userUsername,
        userFirstName = userFirstName,
        userLastName = userLastName,
        isRented = isRented,
        rentMeters = rentMeters,
        rentStartLatitude = rentStartLatitude,
        rentStartLongitude = rentStartLongitude,
        rentTime = rentTime,
        timeStart = timeStart,
        timeEnd = timeEnd
    )
}