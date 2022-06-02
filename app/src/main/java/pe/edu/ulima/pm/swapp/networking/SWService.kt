package pe.edu.ulima.pm.swapp.networking

import pe.edu.ulima.pm.swapp.networking.beans.PlanetsResponse
import retrofit2.Call
import retrofit2.http.GET

interface SWService {
    @GET("planets")
    fun obtenerPlanetas() : Call<PlanetsResponse>
}