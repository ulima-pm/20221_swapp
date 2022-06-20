package pe.edu.ulima.pm.swapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater.from
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import pe.edu.ulima.pm.swapp.models.GestorUsuarios
import java.io.*
import java.nio.charset.Charset

class LoginActivity : AppCompatActivity() {
    private lateinit var eteUsername : EditText
    private lateinit var etePassword : EditText
    private lateinit var mFusedLocationClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mFusedLocationClient = LocationServices
            .getFusedLocationProviderClient(this)
        crearCanalNotificacion()

        if (verificarLoginAI()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else {
            setContentView(R.layout.activity_login)

            eteUsername = findViewById(R.id.eteUsername)
            etePassword = findViewById(R.id.etePassword)

            val butLogin = findViewById<Button>(R.id.butLogin)
            butLogin.setOnClickListener {
                // 0: Llamar al login firebase
                GestorUsuarios.getInstance().login(
                    eteUsername.text.toString(),
                    etePassword.text.toString()) {
                        if (it == null) {
                            // Error en login
                            val notif = crearNotificacion("Login SWApp",
                                "Error en login. Click para registrarse")
                            NotificationManagerCompat.from(this).notify(
                                1,
                                notif
                            )
                            Toast.makeText(this,
                                "Error Login", Toast.LENGTH_SHORT).show()
                        }else {
                            // 1. Guardar el usuario en el Shared Preference / AI
                            //guardarUsernameSP()
                            guardarUsernameAI()
                            // 2. Luego pasar al MainActivity
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
            }
        }
    }

    private fun guardarUsernameSP() {
        val editor = getSharedPreferences(
            Constantes.NOMBRE_SP,
            MODE_PRIVATE
        ).edit()
        editor.putString(Constantes.SP_USERNAME, eteUsername.text.toString())
        editor.commit()
    }

    private fun guardarUsernameAI() {
        /*val fos = openFileOutput("data.txt", Context.MODE_PRIVATE)
        fos.write(eteUsername.text.toString().toByteArray(
            Charset.defaultCharset()))
        fos.close()*/

        openFileOutput(Constantes.NOMBRE_AI, Context.MODE_PRIVATE).use {
            it.write(eteUsername.text.toString().toByteArray(
                Charset.forName("UTF-8")))
            it.close()
        }
    }

    private fun guardarUsernameAE() {
        // Guardamos la data en el almacenamiento externo
        val rutaArchivo = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS).absolutePath + Constantes.NOMBRE_AE
        val archivo = File(rutaArchivo)
        PrintWriter(archivo).use {
            it.println(eteUsername.text.toString())
        }
    }

    private fun verificarLoginAE() : Boolean {
        val rutaArchivo = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS).absolutePath + Constantes.NOMBRE_AE

        try {
            FileReader(rutaArchivo).use {
                val username = it.readText()
                return true
            }
        }catch( e : FileNotFoundException) {
            return false
        }

    }

    private fun verificarLoginAI() : Boolean{
        // Leer de un archivo
        try {
            openFileInput(Constantes.NOMBRE_AI).use {
                if (it != null) {
                    val username = it.bufferedReader(
                        Charset.forName("UTF-8")
                    ).readLine()
                    return true
                } else {
                    return false
                }
            }
        }catch (e : FileNotFoundException) {
            return false
        }

    }

    fun verificarLoginSP() : Boolean{
        // Leer del SP si existe un USERNAME
        val sp = getSharedPreferences(Constantes.NOMBRE_SP, Context.MODE_PRIVATE)
        val username = sp.getString(Constantes.SP_USERNAME, "")!!

        return username != ""
    }

    private fun crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = Constantes.NotificationData.NOTIFICATION_CHANNEL_NAME
            val descriptionText = Constantes.NotificationData.NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(
                Constantes.NotificationData.NOTIFICATION_CHANNEL_ID,
                name,
                importance
            ).apply {
                description = descriptionText
            }

            val notificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun crearNotificacion(title : String, content : String) : Notification {
        val intent = Intent(this, RegistroActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            this,
            Constantes.NotificationData.NOTIFICATION_CHANNEL_ID
        ).setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(androidx.core.R.drawable.notification_icon_background)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()

        return notification
    }

    private fun obtenerUltimaLocalizacion() {
        val permisoLoc = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permisoLoc != PackageManager.PERMISSION_GRANTED) {

        }else {
            // Ya tengo los permisos, tengo que pedir la
            // ultima localizacion
            mFusedLocationClient.lastLocation.addOnSuccessListener {
                Log.i(
                    "LoginActivity",
                    "Latitud:${it.latitude} Longitud:${it.longitude}")
            }
        }
    }
}














