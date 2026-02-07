package com.example.qrsng.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.qrsng.R
import com.example.qrsng.data.db.QrDatabase
import com.example.qrsng.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch
import androidx.core.content.edit

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        val prefs = requireContext()
            .getSharedPreferences("settings", 0)

        // Load toggles
        binding.switchCopy.isChecked =
            prefs.getBoolean("auto_copy", true)

        binding.switchVibrate.isChecked =
            prefs.getBoolean("vibrate", true)

        // Save toggles
        binding.switchCopy.setOnCheckedChangeListener { _, value ->
            prefs.edit { putBoolean("auto_copy", value) }
        }

        binding.switchVibrate.setOnCheckedChangeListener { _, value ->
            prefs.edit { putBoolean("vibrate", value) }
        }

        // Clear history
        binding.btnClearHistory.setOnClickListener {
            lifecycleScope.launch {
                QrDatabase.getInstance(requireContext())
                    .historyDao()
                    .clearAll()

                Toast.makeText(
                    requireContext(),
                    "History cleared",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
