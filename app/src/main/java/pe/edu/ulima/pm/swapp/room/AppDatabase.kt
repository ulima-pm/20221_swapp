package pe.edu.ulima.pm.swapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.edu.ulima.pm.swapp.room.dao.PlanetaRoomDAO
import pe.edu.ulima.pm.swapp.room.models.PlanetaRoom

@Database(entities = arrayOf(PlanetaRoom::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPlanetaRoomDAO() : PlanetaRoomDAO

    companion object {
        private var mInstance : AppDatabase? = null

        fun getInstance(context : Context) : AppDatabase {
            if (mInstance == null) {
                mInstance = Room.inMemoryDatabaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java
                ).allowMainThreadQueries().build()
            }
            return mInstance!!
        }
    }
}