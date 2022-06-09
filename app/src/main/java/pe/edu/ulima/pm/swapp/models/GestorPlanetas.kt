package pe.edu.ulima.pm.swapp.models

import android.content.Context
import android.os.Handler
import android.util.Log
import kotlinx.coroutines.*
import pe.edu.ulima.pm.swapp.models.beans.Planeta
import pe.edu.ulima.pm.swapp.networking.NetworkingManager
import pe.edu.ulima.pm.swapp.room.AppDatabase
import pe.edu.ulima.pm.swapp.room.dao.PlanetaRoomDAO
import pe.edu.ulima.pm.swapp.room.models.PlanetaRoom

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

        val resultado = mutableListOf<Planeta>()

        var cont : Int = 1
        while (true){
            val response = networkingManager.service.obtenerPlanetas(cont.toString()).execute()

            val planetsResponse = response.body()!!

            // llenar un arreglo de planetas
            planetsResponse.results.forEach {
                val planeta = Planeta(
                    it.name,
                    it.terrain,
                    it.population
                )
                resultado.add(planeta)
            }

            if (planetsResponse.next == null) break

            cont++
        }



        /*val resultado = planetsResponse.results.map {
            Planeta(
                it.name,
                it.terrain,
                it.population
            )
        }*/
        return resultado
    }

    fun obtenerListaPlanetasRoom(context : Context) : List<Planeta> {
        val daoPlaneta : PlanetaRoomDAO = AppDatabase.getInstance(
            context).getPlanetaRoomDAO()

        val listaPlanetasRoom = daoPlaneta.getAll() // consulta Room
        val listaPlanetas = listaPlanetasRoom.map {
            Planeta(it.nombre, it.terreno, it.poblacion)
        }
        return listaPlanetas
    }

    fun guardarListaPlanetasRoom(context : Context, planetas : List<Planeta>) {
        val daoPlaneta : PlanetaRoomDAO = AppDatabase.getInstance(
            context).getPlanetaRoomDAO()

        planetas.forEach {
            daoPlaneta.insert(
                PlanetaRoom(0, it.nombre, it.terreno, it.poblacion)
            )
        }
    }


}