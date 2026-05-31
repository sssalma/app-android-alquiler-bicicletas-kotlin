package cat.deim.asm40.pedalean2.data.datasource.api.model
import cat.deim.asm40.pedalean2.domain.models.Bike
import com.google.gson.annotations.SerializedName

data class BikeApiModel(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("battery_level") val batteryLevel: Int,
    @SerializedName("meters") val meters: Int,
    @SerializedName("is_rented") val isRented: Boolean,
    @SerializedName("is_reserved") val isReserved: Boolean,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("last_maintenance") val lastMaintenance: String?,
    @SerializedName("last_use") val lastUse: String?
) {
    fun toDomain(): Bike = Bike(
        uuid = uuid,
        id = id,
        name = name,
        type = type,
        batteryLevel = batteryLevel,
        meters = meters,
        isRented = isRented,
        isReserved = isReserved,
        latitude = latitude,
        longitude = longitude,
        lastMaintenance = lastMaintenance,
        lastUse = lastUse
    )
}
