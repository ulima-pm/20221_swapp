package pe.edu.ulima.pm.swapp.models

import pe.edu.ulima.pm.swapp.models.beans.Planeta

class GestorPlanetas {
    fun obtenerListaPlanetas() : List<Planeta> {
        return listOf(
            Planeta("Tattoine", "desert", 200000),
            Planeta("Alderaan", "grasslands, mountains", 2000000000),
            Planeta("Yavin IV", "jungle, rainforests", 1000),
        )
    }
}