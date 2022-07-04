package pe.edu.ulima.pm.swapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FotoActivity : AppCompatActivity() {
    private lateinit var iviFoto : ImageView
    private var mFotoPath : String? = null

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
        val imageFile = crearArchivoImagen()
        if (imageFile != null) {
            val fotoURI = FileProvider.getUriForFile(
                this,
                "pe.edu.ulima.pm.swapp.fileprovider",
                imageFile
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoURI)
            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            // Vamos a recibir la foto y pintarla en el imageview
            /*val bitmap : Bitmap = data!!.extras!!.get("data") as Bitmap
            iviFoto.setImageBitmap(bitmap)*/

            mostrarFoto()

        }
    }

    private fun mostrarFoto() {
        val matrix = Matrix()
        val angulo = obtenerAnguloRotacion()
        matrix.postRotate(angulo)

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(mFotoPath!!, options)

        // Calculo de espacio disponible
        val iviHeight = iviFoto.height
        val iviWidth = iviFoto.width

        // Calcular el factor de escalamiento
        var scaleFactor = 1
        if (angulo == 90f || angulo == 270f){
            scaleFactor = Math.min(
                iviWidth / options.outHeight,
                iviHeight / options.outWidth
            )
        }else {
            scaleFactor = Math.min(
                iviWidth / options.outWidth,
                iviHeight / options.outHeight
            )
        }


        options.inJustDecodeBounds = false
        options.inSampleSize = scaleFactor

        val bitmap : Bitmap = BitmapFactory.decodeFile(mFotoPath!!, options)
        val bitmapRotated = Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
        iviFoto.setImageBitmap(bitmapRotated)
    }

    private fun crearArchivoImagen() : File? {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss")
            .format(Date())
        val nombreArchivo = "SWAPP_${timestamp}"
        val folderAlmacenamiento = getExternalFilesDir(
            Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(
            nombreArchivo,
            ".jpg",
            folderAlmacenamiento
        )
        mFotoPath = imageFile.absolutePath
        return imageFile
    }

    private fun obtenerAnguloRotacion() : Float {
        val exifInterface = ExifInterface(mFotoPath!!)
        val orientation = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90f
        }else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180f
        }else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270f
        }else {
            return 0f
        }
    }
}