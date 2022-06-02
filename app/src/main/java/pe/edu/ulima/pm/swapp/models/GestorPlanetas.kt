package pe.edu.ulima.pm.swapp.models

import android.os.Handler
import kotlinx.coroutines.*
import pe.edu.ulima.pm.swapp.models.beans.Planeta
import pe.edu.ulima.pm.swapp.networking.NetworkingManager

class GestorPlanetas {
    val handler : Handler = Handler()

    fun obtenerListaPlanetas(callback : (List<Planeta>)->Unit) : Unit {
        var lista : List<Planeta> = ArrayList<Planeta>()
        val hilo = Thread {
            // Simulando conexion remota
            Thread.sleep(1000L)
            lista = listOf(
                Planeta("Tattoine", "desert", "200000"),
                Planeta("Alderaan", "grasslands, mountains", "2000000000"),
                Planeta("Yavin IV", "jungle, rainforests", "1000"),
            )

            // Como handler fue creado en hilo de UI (Main) el codigo que pasemos
            // en el post se ejecuta en el contexto del hilo de UI
            handler.post {
                callback(lista)
            }

        }
        hilo.start()
    }

    fun obtenerListaPlanetasCorutinas() : List<Planeta> {
        // Conexion Remota
        val networkingManager = NetworkingManager.getInstance()
        val response = networkingManager.service.obtenerPlanetas().execute()

        val planetsResponse = response.body()!!

        val resultado = planetsResponse.results.map {
            Planeta(
                it.name,
                it.terrain,
                it.population
            )
        }
        return resultado
    }


}