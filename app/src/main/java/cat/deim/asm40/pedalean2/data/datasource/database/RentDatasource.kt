package cat.deim.asm40.pedalean2.data.datasource.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cat.deim.asm40.pedalean2.data.datasource.database.model.RentDTO

@Dao
interface RentDatasource {

    @Query("SELECT * FROM rents")
    fun getAll(): List<RentDTO>

    @Query("SELECT * FROM rents WHERE uuid = :uuid")
    fun getByUuid(uuid: String): RentDTO?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rentDTO: RentDTO)

    @Update
    fun update(rentDTO: RentDTO)

    @Delete
    fun delete(rentDTO: RentDTO)

    @Query("DELETE FROM rents WHERE uuid = :uuid")
    fun deleteByUuid(uuid: String): Int

    @Query("DELETE FROM rents")
    fun deleteAll(): Int
}