package com.example.KeepFit.data.repository

import androidx.lifecycle.LiveData
import com.example.KeepFit.data.model.DailyStatus

interface DailyRepository {
    suspend fun insertDaily(dailyStatus: DailyStatus)

    suspend fun deleteDaily(dailyStatus: DailyStatus)

    //This table can only have one data

    suspend fun getDaily() : LiveData<DailyStatus>

    suspend fun updateDaily(dailyStatus: DailyStatus)

    suspend fun getOldDaily(): LiveData<List<DailyStatus>>

    suspend fun checkIfEmpty() : Boolean

}