package com.example.KeepFit.screen.History

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.KeepFit.AddHistoryWorker
import com.example.KeepFit.data.model.HistoryItem
import com.example.KeepFit.data.repository.DailyRepository
import com.example.KeepFit.data.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            if (histories.value == null || histories.value!!.isEmpty()){
                historyRepository.insertHistory(
                    HistoryItem(
                        date = LocalDate.now().minusDays(2).toString(),
                        name = LocalDate.now().minusDays(2).toString(),
                        steps = 5000,
                        id = LocalDate.now().minusDays(2).dayOfYear
                    )
                )
                historyRepository.insertHistory(
                    HistoryItem(
                        date = LocalDate.now().minusDays(1).toString(),
                        name = LocalDate.now().minusDays(1).toString(),
                        steps = 5000,
                        id = LocalDate.now().minusDays(1).dayOfYear
                    )
                )
//                historyRepository.insertHistory(
//                    HistoryItem(
//                        date = LocalDate.now().toString(),
//                        name = "keepgoing",
//                        steps = 10000,
//                        id = LocalDate.now().dayOfYear
//                    )
//                )
//                historyRepository.insertHistory(
//                    HistoryItem(
//                        date = LocalDate.now().toString(),
//                        name = "history_sample_3",
//                        steps = 5000
//                    )
//                )
            }
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