package com.example.KeepFit.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.KeepFit.data.model.DailyStatus

@Dao
interface DailyDao {
    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaily(dailyStatus: DailyStatus)

    @Delete
    suspend fun deleteDaily(dailyStatus: DailyStatus)

    @Query("select * from DailyStatus")
    fun getDaily() : LiveData<DailyStatus>

    @Query("select * from DailyStatus")
    fun getOldDaily() : LiveData<List<DailyStatus>>

    @Query("select count(1) where exists (select * from DailyStatus)")
    fun checkIfEmpty(): Int

    @Update
    suspend fun updateDaily(dailyStatus: DailyStatus)

}