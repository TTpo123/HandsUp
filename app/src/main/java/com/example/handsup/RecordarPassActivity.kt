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

class RecordarPassActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recordar_pass)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = Firebase.auth

        val txtEmailCambio: TextView = findViewById(R.id.txtEmailCambio)
        val btnCambiar: Button = findViewById(R.id.btnCambiar)

        btnCambiar.setOnClickListener {
            val email = txtEmailCambio.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Favor de llenar el campo", Toast.LENGTH_SHORT).show()
            } else {
                sendPasswordReset(email)
            }
        }
    }

    private fun sendPasswordReset(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Verificación Enviada Correctamente", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, InicioActivity::class.java)
                    startActivity(i)
                } else {
                    Toast.makeText(baseContext, "Error, no se pudo verificar el correo", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
