package cat.deim.asm40.pedalean2.data.datasource.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cat.deim.asm40.pedalean2.data.datasource.database.model.UserDTO

@Dao
interface UserDatasource {

    @Query("SELECT * FROM users")
    fun getAll(): List<UserDTO>

    @Query("SELECT * FROM users WHERE uuid = :uuid")
    fun getByUuid(uuid: String): UserDTO?

    @Query("SELECT * FROM users WHERE email = :email")
    fun getByEmail(email: String): UserDTO?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userDTO: UserDTO)

    @Update
    fun update(userDTO: UserDTO)

    @Delete
    fun delete(userDTO: UserDTO)

    @Query("DELETE FROM users WHERE uuid = :uuid")
    fun deleteByUuid(uuid: String): Int

    @Query("DELETE FROM users")
    fun deleteAll(): Int
}