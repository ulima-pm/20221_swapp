package pe.edu.ulima.pm.swapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import pe.edu.ulima.pm.swapp.fragments.Registro1Fragment
import pe.edu.ulima.pm.swapp.fragments.Registro2Fragment

class RegistroSlidePagerAdapter(activity : FragmentActivity) : FragmentStateAdapter(activity){

    private val fragmentsRegistro : List<Fragment> = listOf(
        Registro1Fragment(),
        Registro2Fragment()
    )

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentsRegistro[position]
    }

}