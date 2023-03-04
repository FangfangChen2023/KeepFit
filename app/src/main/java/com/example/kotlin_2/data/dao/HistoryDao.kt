package com.example.kotlin_2.data.dao

import androidx.room.*
import com.example.kotlin_2.data.model.HistoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(historyItem: HistoryItem)

    @Delete()
    suspend fun deleteHistory(historyItem: HistoryItem)

    @Query("select * from HistoryItem where id=:id")
    suspend fun getHistory(id: Int) :HistoryItem?

    @Query("select * from HistoryItem")
     fun getHistory() : Flow<List<HistoryItem>>
}
