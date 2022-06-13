package pe.edu.ulima.pm.swapp.models

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GestorUsuarios {
    val dbFirebase = Firebase.firestore

    companion object {
        private var instance : GestorUsuarios? = null

        fun getInstance() : GestorUsuarios {
            if (instance == null) {
                instance = GestorUsuarios()
            }
            return instance!!
        }
    }

    fun login(username : String, password : String) {
        val usuariosCol =  dbFirebase.collection("usuarios")

        usuariosCol.whereEqualTo("username", username)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener {
                if (it!!.documents.size > 0) {
                    Log.i("GestorUsuarios",
                        it!!.documents[0]!!["nombre"].toString())
                }else {
                    Log.i("GestorUsuarios", "Error en login")
                }

            }

    }
}