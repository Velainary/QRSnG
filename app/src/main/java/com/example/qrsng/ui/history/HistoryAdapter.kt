package com.example.qrsng.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.qrsng.data.db.QrHistoryEntity

class HistoryAdapter :
    ListAdapter<QrHistoryEntity, HistoryAdapter.VH>(Diff()) {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.text.text = getItem(position).content
    }

    class Diff : DiffUtil.ItemCallback<QrHistoryEntity>() {
        override fun areItemsTheSame(
            oldItem: QrHistoryEntity,
            newItem: QrHistoryEntity
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: QrHistoryEntity,
            newItem: QrHistoryEntity
        ): Boolean = oldItem == newItem
    }
}
