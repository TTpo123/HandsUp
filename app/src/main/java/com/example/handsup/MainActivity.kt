package com.example.handsup

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.handsup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Manejo del mensaje de notificación
        val message = intent.getStringExtra("notification_message")
        if (message != null) {
            // Crear un Bundle para pasar el mensaje a NotificationsFragment
            val bundle = Bundle().apply {
                putString("notification_message", message)
            }

            // Limpiar el historial de navegación antes de ir a NotificationsFragment
            navController.popBackStack(R.id.navigation_home, true)
            navController.navigate(R.id.navigation_notifications, bundle)
        }
    }
}
