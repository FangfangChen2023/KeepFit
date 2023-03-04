package com.example.kotlin_2.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlin_2.data.dao.DailyDao
import com.example.kotlin_2.data.model.DailyStatus
import com.example.kotlin_2.data.model.GoalItem
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

    // Query dao can only return a list
    override suspend fun getDaily(): LiveData<DailyStatus> {
        return if(dao.getDaily().isNotEmpty()){
            MutableLiveData(dao.getDaily()[0])
        }else MutableLiveData()

    }

    override suspend fun updateDaily(dailyStatus: DailyStatus) {
        return dao.updateDaily(dailyStatus)
    }

    override suspend fun checkIfEmpty(): Boolean {
        return dao.checkIfEmpty() == 0
    }

    override suspend fun refreshDailyStatus(goalItem: GoalItem){
        // Delete current daily status (only allow one daily status)
        if(getDaily().value != null){
            Log.d("deleted: ", getDaily().value.toString())
            deleteDaily(getDaily().value!!)

        }
        // Add new daily status
        insertDaily(DailyStatus(
            currentSteps = 0,
            todayDate = LocalDate.now().toString(),
            goalName = goalItem.name,
            goalSteps = goalItem.steps
        ))
        Log.d("inserted: ", getDaily().value.toString())
    }

//    override suspend fun getOldDaily(): LiveData<List<DailyStatus>> {
//        return dao.getOldDaily()
//    }

}