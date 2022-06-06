package pe.edu.ulima.pm.swapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    private lateinit var mToolbar: Toolbar

    private val fragmentEspecies = EspeciesFragment()
    private val fragmentNavesEspaciales = NavesEspacialesFragment()
    private val fragmentPlanetas = PlanetasFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDlaMain = findViewById(R.id.dlaMain)
        mNviMain = findViewById(R.id.nviMain)
        mToolbar = findViewById(R.id.toolbar)

        //setSupportActionBar(mToolbar)

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
                R.id.menCerrarSesion -> cerrarSesion()
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
        //ejecutarThread()
    }

    private fun cerrarSesion() {
        val editor = getSharedPreferences(Constantes.NOMBRE_SP, Context.MODE_PRIVATE).edit()
        editor.putString(Constantes.SP_USERNAME, null)
        editor.commit()
        finish()
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

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_planetas, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.menIrRegistro) {
//            val intent = Intent(this, RegistroActivity::class.java)
//            startActivity(intent)
//        }
//        return true
//    }

    fun ejecutarThread() {
        val thread : Thread = Thread {
            Thread.sleep(2000L)
            mToolbar.title = "Titulo 2"
            println("Prueba de hilo")
        }
        thread.start()
        mToolbar.title = "Titulo 0"
        println(mToolbar.title)
        println("Fuera del hilo")

    }
}








































