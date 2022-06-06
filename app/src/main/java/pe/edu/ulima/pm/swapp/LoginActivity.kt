package pe.edu.ulima.pm.swapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {
    private lateinit var eteUsername : EditText
    private lateinit var etePassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (verificarLogin()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else {
            setContentView(R.layout.activity_login)

            eteUsername = findViewById(R.id.eteUsername)
            etePassword = findViewById(R.id.etePassword)

            val butLogin = findViewById<Button>(R.id.butLogin)
            butLogin.setOnClickListener {
                // 1. Guardar el usuario en el Shared Preference
                val editor = getSharedPreferences(
                    Constantes.NOMBRE_SP,
                    Context.MODE_PRIVATE).edit()
                editor.putString(Constantes.SP_USERNAME, eteUsername.text.toString())
                editor.commit()

                // 2. Luego pasar al MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    fun verificarLogin() : Boolean{
        // Leer del SP si existe un USERNAME
        val sp = getSharedPreferences(Constantes.NOMBRE_SP, Context.MODE_PRIVATE)
        val username = sp.getString(Constantes.SP_USERNAME, "")!!

        return username != ""
    }
}