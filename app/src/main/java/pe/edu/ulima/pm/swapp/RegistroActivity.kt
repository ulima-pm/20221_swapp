package pe.edu.ulima.pm.swapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import pe.edu.ulima.pm.swapp.adapters.RegistroSlidePagerAdapter

class RegistroActivity : AppCompatActivity() {
    private lateinit var mViewPager : ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Obtenemos una referencia al widget Viewpager2
        mViewPager = findViewById(R.id.pager)

        // Configuramos el viewpager con el adapter creado
        val pagerAdapter = RegistroSlidePagerAdapter(this)
        mViewPager.adapter = pagerAdapter
    }

    override fun onBackPressed() {
        if (mViewPager.currentItem == 0){
            super.onBackPressed()
        }else {
            mViewPager.currentItem -= 1
        }

    }
}