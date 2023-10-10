package com.example.KeepFit.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.KeepFit.Converters
import com.example.KeepFit.data.dao.DailyDao
import com.example.KeepFit.data.dao.GoalDao
import com.example.KeepFit.data.dao.HistoryDao
import com.example.KeepFit.data.model.DailyStatus
import com.example.KeepFit.data.model.GoalItem
import com.example.KeepFit.data.model.HistoryItem

@Database(entities = [GoalItem::class, HistoryItem::class, DailyStatus::class], version = 1, exportSchema=false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao
    abstract fun historyDao(): HistoryDao

    abstract fun dailyDao(): DailyDao

}

