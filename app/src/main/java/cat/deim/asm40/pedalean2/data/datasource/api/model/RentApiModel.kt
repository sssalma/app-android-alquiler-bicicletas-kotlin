package cat.deim.asm40.pedalean2.data.datasource.api.model

import cat.deim.asm40.pedalean2.domain.models.Rent
import com.google.gson.annotations.SerializedName

data class RentApiModel(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("bike") val bike: RentBikeApiModel,
    @SerializedName("user") val user: RentUserApiModel,
    @SerializedName("is_rented") val isRented: Boolean,
    @SerializedName("rent_meters") val rentMeters: Int,
    @SerializedName("rent_start_lat") val rentStartLat: Double,
    @SerializedName("rent_start_lng") val rentStartLng: Double,
    @SerializedName("rent_time") val rentTime: Int,
    @SerializedName("time_start") val timeStart: String?,
    @SerializedName("time_end") val timeEnd: String?   // null en alquileres activos
) {
    fun toDomain(): Rent = Rent(
        uuid = uuid,
        bikeUuid = bike.uuid,
        bikeName = bike.name,
        userEmail = user.email,
        userUsername = user.username,
        userFirstName = user.firstName,
        userLastName = user.lastName,
        isRented = isRented,
        rentMeters = rentMeters,
        rentStartLatitude = rentStartLat,
        rentStartLongitude = rentStartLng,
        rentTime = rentTime,
        timeStart = timeStart ?: "",
        timeEnd = timeEnd ?: ""
    )
}

data class RentBikeApiModel(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("name") val name: String
)

data class RentUserApiModel(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String
)

data class RentApiResponse(
    @SerializedName("rents") val rent: List<RentApiModel>   // la clave es "rents", no "rent"
)

data class RentStartRequestApiModel(
    @SerializedName("bike_uuid") val bikeUuid: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)

data class RentStopRequestApiModel(
    @SerializedName("bike_uuid") val bikeUuid: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)