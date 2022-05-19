package pe.edu.ulima.pm.swapp.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
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

        val listaPlanetas : List<Planeta> = GestorPlanetas().obtenerListaPlanetas()
        val adapter = ListadoPlanetasAdapter(listaPlanetas)
        mRviPlanetas.adapter = adapter

    }
}