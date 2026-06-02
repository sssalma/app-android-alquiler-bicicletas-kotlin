package cat.deim.asm40.pedalean2.data.datasource.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cat.deim.asm40.pedalean2.domain.models.Bike
import com.pedalean2.common.datasource.local.model.BikeModel

@Entity(tableName = "bikes")
data class BikeDTO(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "battery_level") val batteryLevel: Int,
    @ColumnInfo(name = "meters") val meters: Int,
    @ColumnInfo(name = "is_rented") val isRented: Boolean,
    @ColumnInfo(name = "is_reserved") val isReserved: Boolean,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "last_maintenance") val lastMaintenance: String?,
    @ColumnInfo(name = "last_use") val lastUse: String?
) {
    companion object {
        fun fromDomain(bike: Bike): BikeDTO = BikeDTO(
            uuid = bike.uuid,
            id = bike.id,
            name = bike.name,
            type = bike.type,
            batteryLevel = bike.batteryLevel,
            meters = bike.meters,
            isRented = bike.isRented,
            isReserved = bike.isReserved,
            latitude = bike.latitude,
            longitude = bike.longitude,
            lastMaintenance = bike.lastMaintenance,
            lastUse = bike.lastUse
        )
        fun fromModel(model: BikeModel): BikeDTO = BikeDTO(
            uuid = model.uuid, id = model.id, name = model.name, type = model.type,
            batteryLevel = model.batteryLevel, meters = model.meters,
            isRented = model.isRented, isReserved = model.isReserved,
            latitude = model.latitude, longitude = model.longitude,
            lastMaintenance = model.lastMaintenance, lastUse = model.lastUse
        )
    }

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