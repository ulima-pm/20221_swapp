package pe.edu.ulima.pm.swapp.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton
class NetworkingManager(val service : SWService) {

    // Dentro de este objeto van las propiedades de clase
    companion object {
        private var instance: NetworkingManager? = null

        fun getInstance(): NetworkingManager {
            if (instance == null) {

                val retrofit : Retrofit = Retrofit.Builder()
                    .baseUrl("https://swapi.dev/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val service = retrofit.create(SWService::class.java)

                instance = NetworkingManager(service)
            }
            return instance!!
        }
    }
}