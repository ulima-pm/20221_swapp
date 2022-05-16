package pe.edu.ulima.pm.swapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var mDlaMain : DrawerLayout
    private lateinit var mNviMain : NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDlaMain = findViewById(R.id.dlaMain)
        mNviMain = findViewById(R.id.nviMain)

        mNviMain.setNavigationItemSelectedListener {
            it.setChecked(true)

            when(it.itemId) {
                R.id.menEspecies -> mostrarFragmentEspecies()
                R.id.menNavesEspaciales -> mostrarFragmentNavesEspaciales()
                R.id.menPeliculas -> mostrarFragmentPeliculas()
                R.id.menPersonas -> mostrarFragmentPersonas()
                R.id.menPlanetas -> mostrarFragmentPlanetas()
                R.id.menVehiculos -> mostrarFragmentVehiculos()
            }

            mDlaMain.closeDrawers()
            true
        }
    }

    private fun mostrarFragmentVehiculos() {
        TODO("Not yet implemented")
    }

    private fun mostrarFragmentPlanetas() {
        TODO("Not yet implemented")
    }

    private fun mostrarFragmentPersonas() {
        TODO("Not yet implemented")
    }

    private fun mostrarFragmentPeliculas() {
        TODO("Not yet implemented")
    }

    private fun mostrarFragmentNavesEspaciales() {
        TODO("Not yet implemented")
    }

    private fun mostrarFragmentEspecies() {
        TODO("Not yet implemented")
    }
}