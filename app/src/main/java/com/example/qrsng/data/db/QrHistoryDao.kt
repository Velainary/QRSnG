package com.example.qrsng.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QrHistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: QrHistoryEntity)

    @Query("""
        SELECT * FROM qr_history
        WHERE type = :type
        ORDER BY timestamp DESC
    """)
    fun getByType(type: String): Flow<List<QrHistoryEntity>>

    @Query("DELETE FROM qr_history")
    suspend fun clearAll()
}
