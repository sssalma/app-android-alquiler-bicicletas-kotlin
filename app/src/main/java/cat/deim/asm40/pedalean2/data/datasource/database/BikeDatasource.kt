package cat.deim.asm40.pedalean2.data.datasource.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cat.deim.asm40.pedalean2.data.datasource.database.model.BikeDTO

@Dao
interface BikeDatasource {

    @Query("SELECT * FROM bikes")
    fun getAll(): List<BikeDTO>

    @Query("SELECT * FROM bikes WHERE uuid = :uuid")
    fun getByUuid(uuid: String): BikeDTO?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bikeDTO: BikeDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(bikes: List<BikeDTO>)

    @Update
    fun update(bikeDTO: BikeDTO)

    @Delete
    fun delete(bikeDTO: BikeDTO)

    @Query("DELETE FROM bikes WHERE uuid = :uuid")
    fun deleteByUuid(uuid: String): Int

    @Query("DELETE FROM bikes")
    fun deleteAll(): Int
}