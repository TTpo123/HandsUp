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

class InicioActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Establecer el contenido de la vista primero
        setContentView(R.layout.activity_inicio)

        // Inicializar Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Acceder al botón y las TextViews después de establecer el contenido
        val btningresar: Button = findViewById(R.id.btnIngresar)
        val txtemail: TextView = findViewById(R.id.edtEmail)
        val txtpass: TextView = findViewById(R.id.edtPassword)
        val btnCrearCuentasNuevas: TextView = findViewById(R.id.btnCrearCuenta)
        val btnRecordar: TextView = findViewById(R.id.btnOlvidar)

        // Lógica para habilitar el modo Edge to Edge
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar el clic del botón para iniciar sesión
        btningresar.setOnClickListener {
            // Obtener los valores de los TextViews
            val email = txtemail.text.toString().trim()
            val password = txtpass.text.toString().trim()

            // Validación
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Iniciar sesión con Firebase
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Navegar a MainActivity
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish() // Opcional: Termina InicioActivity para que no se vuelva atrás
                        } else {
                            // Mostrar mensaje de error
                            Toast.makeText(this, "Verifica si Email y/o contraseña son correctos", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Datos incompletos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el clic del botón para crear una nueva cuenta
        btnCrearCuentasNuevas.setOnClickListener {
            val intent = Intent(this, CrearCuentaActivity::class.java)
            startActivity(intent)
        }

        btnRecordar.setOnClickListener()
        {
            val i = Intent(this, RecordarPassActivity::class.java)
            startActivity(i)
        }

    }
}