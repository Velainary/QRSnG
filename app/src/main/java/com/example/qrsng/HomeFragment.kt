package com.example.qrsng

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.qrsng.databinding.FragmentHomeBinding
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        // Button wiring will go here next
        binding.btnScan.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_scan)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
