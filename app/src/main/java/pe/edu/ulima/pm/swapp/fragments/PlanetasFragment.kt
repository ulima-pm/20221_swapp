package pe.edu.ulima.pm.swapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import pe.edu.ulima.pm.swapp.Constantes
import pe.edu.ulima.pm.swapp.R
import pe.edu.ulima.pm.swapp.adapters.ListadoPlanetasAdapter
import pe.edu.ulima.pm.swapp.models.GestorPlanetas
import pe.edu.ulima.pm.swapp.models.beans.Planeta

class PlanetasFragment : Fragment() {
    private lateinit var mRviPlanetas : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = "Planetas"
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_planetas, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_planetas, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRviPlanetas = view.findViewById(R.id.rviPlanetas)

        //val listaPlanetas : List<Planeta> = GestorPlanetas().obtenerListaPlanetas()
        /*GestorPlanetas().obtenerListaPlanetas {
            val adapter = ListadoPlanetasAdapter(it) {
                Log.i("PlanetasFragment","Se hizo click en el planeta " + it.nombre);
            }
            mRviPlanetas.adapter = adapter
        }*/
        val gestor = GestorPlanetas()

        /*GlobalScope.launch(Dispatchers.IO) {
            // Defecto : Default -> Tareas de alto costo computacional
            // IO : -> Tareas que no tinen costo alto pero tienen paradas
            val lista = gestor.obtenerListaPlanetasCorutinas()

            withContext(Dispatchers.Main) {
                val adapter = ListadoPlanetasAdapter(lista) {
                    Log.i("PlanetasFragment","Se hizo click en el planeta " + it.nombre);
                }
                mRviPlanetas.adapter = adapter
            }
        }*/

        val sp = requireActivity().getSharedPreferences(
            Constantes.NOMBRE_SP, Context.MODE_PRIVATE)

        GlobalScope.launch(Dispatchers.Main) {
            val estaSincronizado = sp.getBoolean(Constantes.SP_ESTA_SINCRONIZADO,
                false)
            var lista : List<Planeta> = mutableListOf()
            if (!estaSincronizado) {
                // Obtenemos la data del servicio externo
                lista = withContext(Dispatchers.IO) {
                    gestor.obtenerListaPlanetasCorutinas()
                }

                // Guardamos los planetas obtenidos en el servicio en Room
                gestor.guardarListaPlanetasRoom(
                    requireActivity().applicationContext,
                    lista
                )
                sp.edit().putBoolean(
                    Constantes.SP_ESTA_SINCRONIZADO, true).commit()
            } else {
                // Obtenemos la data de Room (base de datos interna)
                lista = gestor.obtenerListaPlanetasRoom(
                    requireContext().applicationContext)
            }


            val adapter = ListadoPlanetasAdapter(lista) {
                Log.i("PlanetasFragment","Se hizo click en el planeta " + it.nombre);
            }
            mRviPlanetas.adapter = adapter
        }

    }
}