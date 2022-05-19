package pe.edu.ulima.pm.swapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.swapp.R
import pe.edu.ulima.pm.swapp.models.beans.Planeta


class ListadoPlanetasAdapter(private val mListaPlanetas : List<Planeta>)
    : RecyclerView.Adapter<ListadoPlanetasAdapter.ViewHolder>(){
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tviPlanetaNombre : TextView

        init {
            tviPlanetaNombre = view.findViewById(R.id.tviPlanetaNombre)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_planeta, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val planeta = mListaPlanetas[position]
        holder.tviPlanetaNombre.text = planeta.nombre
    }

    override fun getItemCount(): Int {
        // Devolver el nro de items a mostrar
        return mListaPlanetas.size
    }
}