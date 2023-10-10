package com.example.KeepFit.data.dao

import androidx.room.*
import com.example.KeepFit.data.model.HistoryItem

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(historyItem: HistoryItem)

    @Delete()
    suspend fun deleteHistory(historyItem: HistoryItem)

    @Query("select * from HistoryItem where id=:id")
    suspend fun getHistory(id: Int) :HistoryItem?

    @Query("select * from HistoryItem")
     fun getAllHistory() : List<HistoryItem>
     @Update
     suspend fun updateHistory(historyItem: HistoryItem)
}
