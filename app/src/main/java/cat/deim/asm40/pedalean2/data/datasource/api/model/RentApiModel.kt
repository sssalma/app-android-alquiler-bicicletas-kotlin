package cat.deim.asm40.pedalean2.data.datasource.api.model

import cat.deim.asm40.pedalean2.domain.models.Rent
import com.google.gson.annotations.SerializedName

data class RentApiModel(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("bike_uuid") val bikeUuid: String,
    @SerializedName("bike_name") val bikeName: String,
    @SerializedName("user_email") val userEmail: String,
    @SerializedName("user_username") val userUsername: String,
    @SerializedName("user_first_name") val userFirstName: String,
    @SerializedName("user_last_name") val userLastName: String,
    @SerializedName("is_rented") val isRented: Boolean,
    @SerializedName("rent_meters") val rentMeters: Int,
    @SerializedName("rent_start_latitude") val rentStartLatitude: Double,
    @SerializedName("rent_start_longitude") val rentStartLongitude: Double,
    @SerializedName("rent_time") val rentTime: Int,
    @SerializedName("time_start") val timeStart: String,
    @SerializedName("time_end") val timeEnd: String
) {
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
data class RentApiResponse(
    @SerializedName("rent") val rent: List<RentApiModel>
)
data class RentStartRequestApiModel(
    @SerializedName("bike_uuid") val bikeUuid: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)

data class RentStopRequestApiModel(
    @SerializedName("rent_uuid") val rentUuid: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)
