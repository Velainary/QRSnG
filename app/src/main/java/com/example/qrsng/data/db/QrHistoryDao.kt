package com.example.qrsng.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QrHistoryDao {

    @Query("SELECT * FROM qr_history WHERE type = :type ORDER BY timestamp DESC")
    fun getByType(type: String): Flow<List<QrHistoryEntity>>

    @Insert
    suspend fun insert(item: QrHistoryEntity)

    @Query("DELETE FROM qr_history")
    suspend fun clearAll()
}
