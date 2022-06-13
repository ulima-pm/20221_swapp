package pe.edu.ulima.pm.swapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import pe.edu.ulima.pm.swapp.models.GestorUsuarios
import java.io.*
import java.nio.charset.Charset

class LoginActivity : AppCompatActivity() {
    private lateinit var eteUsername : EditText
    private lateinit var etePassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    etePassword.text.toString())
                // 1. Guardar el usuario en el Shared Preference / AI
                //guardarUsernameSP()
                //guardarUsernameAI()

                // 2. Luego pasar al MainActivity
                //startActivity(Intent(this, MainActivity::class.java))
                //finish()
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


}