package pe.edu.ulima.pm.swapp.networking.beans

data class PlanetsResponse(
    val count : Int,
    val next : String,
    val previous : String?,
    val results : List<Planet>
)