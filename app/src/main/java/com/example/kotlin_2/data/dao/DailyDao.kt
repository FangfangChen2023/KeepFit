package com.example.kotlin_2.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kotlin_2.data.model.DailyStatus
import com.example.kotlin_2.data.model.GoalItem
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyDao {
    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaily(dailyStatus: DailyStatus)

    @Delete
    suspend fun deleteDaily(dailyStatus: DailyStatus)

    // Query can only return a list
    @Query("select * from DailyStatus")
    fun getDaily() : List<DailyStatus>

    @Query("select * from DailyStatus")
    fun getOldDaily() : LiveData<List<DailyStatus>>

    @Query("select count(1) where exists (select * from DailyStatus)")
    fun checkIfEmpty(): Int

    @Update
    suspend fun updateDaily(dailyStatus: DailyStatus)


}