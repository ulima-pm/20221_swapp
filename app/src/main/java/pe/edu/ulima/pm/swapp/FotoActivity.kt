package pe.edu.ulima.pm.swapp

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView

class FotoActivity : AppCompatActivity() {
    private lateinit var iviFoto : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foto)

        iviFoto = findViewById(R.id.iviFoto)

        val butTomarFoto = findViewById<Button>(R.id.butTomarFoto)
        butTomarFoto.setOnClickListener {
            tomarFoto()
        }
    }

    private fun tomarFoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        Log.i("FotoActivity" , "CP1")
        startActivityForResult(intent, 100)
        /*intent.resolveActivity(packageManager)?.also {
            Log.i("FotoActivity" , "CP2")

        }*/


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            // Vamos a recibir la foto y pintarla en el imageview
            val bitmap : Bitmap = data!!.extras!!.get("data") as Bitmap
            iviFoto.setImageBitmap(bitmap)
        }
    }
}