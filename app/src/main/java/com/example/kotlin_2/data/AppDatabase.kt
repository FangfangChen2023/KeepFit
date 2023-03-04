package com.example.kotlin_2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kotlin_2.Converters
import com.example.kotlin_2.data.dao.DailyDao
import com.example.kotlin_2.data.dao.GoalDao
import com.example.kotlin_2.data.dao.HistoryDao
import com.example.kotlin_2.data.dao.SettingDao
import com.example.kotlin_2.data.model.DailyStatus
import com.example.kotlin_2.data.model.GoalItem
import com.example.kotlin_2.data.model.HistoryItem
import com.example.kotlin_2.data.model.Setting

@Database(entities = [GoalItem::class, HistoryItem::class, DailyStatus::class, Setting::class], version = 1, exportSchema=false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao
    abstract fun historyDao(): HistoryDao
    abstract fun settingDao(): SettingDao
    abstract fun dailyDao(): DailyDao

}

