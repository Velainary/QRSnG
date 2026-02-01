package com.example.qrsng.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qr_history")
data class QrHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val content: String,
    val type: String, // "SCAN" or "GENERATE"
    val timestamp: Long = System.currentTimeMillis()
)
