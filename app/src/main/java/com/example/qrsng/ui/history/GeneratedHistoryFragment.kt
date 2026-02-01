package com.example.qrsng.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qrsng.R
import com.example.qrsng.data.db.QrDatabase
import kotlinx.coroutines.launch

class GeneratedHistoryFragment : Fragment(R.layout.fragment_history_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        val adapter = HistoryAdapter()

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        lifecycleScope.launch {
            QrDatabase.getInstance(requireContext())
                .historyDao()
                .getByType("GENERATE")
                .collect { list ->
                    adapter.submitList(list)
                }
        }
    }
}
