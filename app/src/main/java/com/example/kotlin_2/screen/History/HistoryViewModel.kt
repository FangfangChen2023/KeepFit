package com.example.kotlin_2.screen.History

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.kotlin_2.AddHistoryWorker
import com.example.kotlin_2.data.model.HistoryItem
import com.example.kotlin_2.data.repository.DailyRepository
import com.example.kotlin_2.data.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository : HistoryRepository,
    private val dailyRepository: DailyRepository,
) :ViewModel(){

    var histories = MutableLiveData<List<HistoryItem>>()

    init {
        viewModelScope.launch (Dispatchers.IO){
            histories.postValue(historyRepository.getAllHistory())

            // -------------------------For Test----------------------------------//
//            if (histories.value == null || histories.value!!.isEmpty()){
//                historyRepository.insertHistory(
//                    HistoryItem(
//                        date = LocalDate.now().toString(),
//                        name = "history_sample_1",
//                        steps = 5000
//                    )
//                )
//                historyRepository.insertHistory(
//                    HistoryItem(
//                        date = LocalDate.now().toString(),
//                        name = "history_sample_2",
//                        steps = 5000
//                    )
//                )
//                historyRepository.insertHistory(
//                    HistoryItem(
//                        date = LocalDate.now().toString(),
//                        name = "history_sample_3",
//                        steps = 5000
//                    )
//                )
//            }
            //---------------------For Test--------------------------------------//

            histories.postValue(historyRepository.getAllHistory())
        }
    }

    private val addHistoryRequest = PeriodicWorkRequestBuilder<AddHistoryWorker>(
        24,
        TimeUnit.HOURS
    )
        .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_REQUIRED).build())
        .build()
    internal fun applyWorker(application: Application){
        WorkManager.getInstance(application).enqueue(addHistoryRequest)
    }

    fun clearAllHistories(){
        viewModelScope.launch (Dispatchers.IO) {
            val historyList = historyRepository.getAllHistory()
            for (item in historyList){
                historyRepository.deleteHistory(item)
            }
            histories.postValue(historyRepository.getAllHistory())
        }
    }

    fun deleteHistory(historyItem: HistoryItem){
        viewModelScope.launch (Dispatchers.IO) {
            historyRepository.deleteHistory(historyItem)
            histories.postValue(historyRepository.getAllHistory())
        }
    }

    fun updateHistory(historyItem: HistoryItem){
        viewModelScope.launch (Dispatchers.IO) {
            historyRepository.insertHistory(historyItem)
            histories.postValue(historyRepository.getAllHistory())
        }
    }


}