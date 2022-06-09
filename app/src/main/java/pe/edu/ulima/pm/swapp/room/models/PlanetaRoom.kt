package pe.edu.ulima.pm.swapp.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlanetaRoom(
    @ColumnInfo(name = "nombre")
    val nombre : String,
    @ColumnInfo(name = "terreno")
    val terreno : String,
    @ColumnInfo(name = "poblacion")
    val poblacion : String
) {
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0
}