package pe.edu.ulima.pm.swapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import pe.edu.ulima.pm.swapp.fragments.EspeciesFragment
import pe.edu.ulima.pm.swapp.fragments.NavesEspacialesFragment
import pe.edu.ulima.pm.swapp.fragments.PlanetasFragment

class MainActivity : AppCompatActivity() {
    private lateinit var mDlaMain : DrawerLayout
    private lateinit var mNviMain : NavigationView

    private val fragmentEspecies = EspeciesFragment()
    private val fragmentNavesEspaciales = NavesEspacialesFragment()
    private val fragmentPlanetas = PlanetasFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDlaMain = findViewById(R.id.dlaMain)
        mNviMain = findViewById(R.id.nviMain)

        mNviMain.setNavigationItemSelectedListener {
            it.setChecked(true)

            val ft = supportFragmentManager.beginTransaction()

            when(it.itemId) {
                R.id.menEspecies -> mostrarFragmentEspecies(ft)
                R.id.menNavesEspaciales -> mostrarFragmentNavesEspaciales(ft)
                R.id.menPeliculas -> mostrarFragmentPeliculas(ft)
                R.id.menPersonas -> mostrarFragmentPersonas(ft)
                R.id.menPlanetas -> mostrarFragmentPlanetas(ft)
                R.id.menVehiculos -> mostrarFragmentVehiculos(ft)
            }
            ft.addToBackStack(null)

            ft.commit()

            mDlaMain.closeDrawers()
            true
        }

        // Cargar el fragment por defecto
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.fcvSecciones, fragmentPlanetas)
        ft.commit()

        // Configurando toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun mostrarFragmentVehiculos(ft : FragmentTransaction) {
        TODO("Not yet implemented")
    }

    private fun mostrarFragmentPlanetas(ft : FragmentTransaction) {
        ft.replace(R.id.fcvSecciones, fragmentPlanetas)
    }

    private fun mostrarFragmentPersonas(ft : FragmentTransaction) {
        TODO("Not yet implemented")
    }

    private fun mostrarFragmentPeliculas(ft : FragmentTransaction) {
        TODO("Not yet implemented")
    }

    private fun mostrarFragmentNavesEspaciales(ft : FragmentTransaction) {
        ft.replace(R.id.fcvSecciones, fragmentNavesEspaciales)
    }

    private fun mostrarFragmentEspecies(ft : FragmentTransaction) {
        ft.replace(R.id.fcvSecciones, fragmentEspecies)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_planetas, menu)
        return true
    }
}