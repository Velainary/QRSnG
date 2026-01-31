package com.example.qrsng.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [QrHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class QrDatabase : RoomDatabase() {

    abstract fun historyDao(): QrHistoryDao

    companion object {
        @Volatile private var INSTANCE: QrDatabase? = null

        fun getInstance(context: Context): QrDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    QrDatabase::class.java,
                    "qr_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
