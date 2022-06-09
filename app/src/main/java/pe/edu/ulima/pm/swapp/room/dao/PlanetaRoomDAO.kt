package pe.edu.ulima.pm.swapp.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pe.edu.ulima.pm.swapp.room.models.PlanetaRoom

@Dao
interface PlanetaRoomDAO {
    @Query("SELECT * FROM PlanetaRoom")
    fun getAll() : List<PlanetaRoom>

    @Query("SELECT * FROM PlanetaRoom WHERE id=:id")
    fun findById(id : Int) : PlanetaRoom

    @Insert
    fun insert(planeta : PlanetaRoom)

    @Delete
    fun delete(planeta: PlanetaRoom)
}