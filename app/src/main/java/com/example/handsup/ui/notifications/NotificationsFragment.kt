package com.example.handsup.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.handsup.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Recibir el mensaje de notificación y mostrarlo en el TextView
        val message = arguments?.getString("notification_message")
        binding.TextNot.text = message ?: "No hay mensaje" // Texto predeterminado si el mensaje es nulo

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
