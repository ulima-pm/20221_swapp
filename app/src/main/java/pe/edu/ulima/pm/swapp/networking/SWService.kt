package pe.edu.ulima.pm.swapp.networking

import pe.edu.ulima.pm.swapp.networking.beans.PlanetsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SWService {
    @GET("planets")
    fun obtenerPlanetas(@Query("page") page : String?) : Call<PlanetsResponse>
}