package com.example.handsup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CrearCuentaActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_cuenta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtnombrenuevo: TextView = findViewById(R.id.edtNombre)
        val txtemailnuevo: TextView = findViewById(R.id.edtEmailNuevo)
        val txtpassword1: TextView = findViewById(R.id.edtPasswordNuevo1)
        val txtpassword2: TextView = findViewById(R.id.edtPasswordNuevo2)
        val btnCrear: Button = findViewById(R.id.btnCrearCuentaNueva)

        btnCrear.setOnClickListener {
            val nombre = txtnombrenuevo.text.toString().trim()
            val email = txtemailnuevo.text.toString().trim()
            val pass1 = txtpassword1.text.toString()
            val pass2 = txtpassword2.text.toString()

            // Validar que todos los campos estén llenos
            if (nombre.isEmpty() || email.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
                Toast.makeText(baseContext, "Información incompleta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificar si las contraseñas coinciden
            if (pass1 == pass2) {
                createAccount(email, pass1)
            } else {
                Toast.makeText(baseContext, "Error: las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                txtpassword1.requestFocus()
            }
        }

        firebaseAuth = Firebase.auth
    }

    private fun createAccount(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                sendEmailVerification()
                Toast.makeText(baseContext, "Gracias por Acceder", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, InicioActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(baseContext, "Algo salió mal, Error: ${task.exception}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendEmailVerification() {
        val user = firebaseAuth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(baseContext, "Verificación de manera exitosa", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(baseContext, "Algo salió mal, Error: ${task.exception}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}