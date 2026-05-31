package cat.deim.asm40.pedalean2.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cat.deim.asm40.pedalean2.data.datasource.database.model.BikeDTO
import cat.deim.asm40.pedalean2.data.datasource.database.model.RentDTO
import cat.deim.asm40.pedalean2.data.datasource.database.model.UserDTO

@Database(entities = [UserDTO::class, BikeDTO::class, RentDTO::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDatasource(): UserDatasource
    abstract fun bikeDatasource(): BikeDatasource
    abstract fun rentDatasource(): RentDatasource

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pedalean2_database.db"
                ).build().also { INSTANCE = it }
            }
    }
}