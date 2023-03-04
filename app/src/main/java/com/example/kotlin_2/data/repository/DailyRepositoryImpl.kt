package com.example.kotlin_2.data.repository

import androidx.lifecycle.LiveData
import com.example.kotlin_2.data.dao.DailyDao
import com.example.kotlin_2.data.model.DailyStatus
import java.time.LocalDate

class DailyRepositoryImpl(
    private val dao: DailyDao
) : DailyRepository {
    override suspend fun insertDaily(dailyStatus: DailyStatus) {
        dao.insertDaily(dailyStatus)
    }

    override suspend fun deleteDaily(dailyStatus: DailyStatus) {
        dao.deleteDaily(dailyStatus)
    }

    override suspend fun getDaily(): LiveData<DailyStatus> {


        return dao.getDaily()
    }

    override suspend fun updateDaily(dailyStatus: DailyStatus) {
        return dao.updateDaily(dailyStatus)
    }

    override suspend fun checkIfEmpty(): Boolean {
        return dao.checkIfEmpty() == 0
    }

    override suspend fun getOldDaily(): LiveData<List<DailyStatus>> {
        return dao.getOldDaily()
    }

}