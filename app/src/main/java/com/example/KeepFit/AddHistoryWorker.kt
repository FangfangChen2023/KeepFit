package com.example.KeepFit

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.KeepFit.data.model.HistoryItem
import com.example.KeepFit.data.repository.DailyRepository
import com.example.KeepFit.data.repository.HistoryRepository
import java.time.LocalDate
import java.time.LocalTime

class AddHistoryWorker(
    context : Context,
    workerParameters: WorkerParameters,
    private val dailyRepository: DailyRepository,
    private val historyRepository: HistoryRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

            var today = dailyRepository.getDaily().value
        if (today != null){
            historyRepository.insertHistory(
                HistoryItem(
                    date = LocalTime.now().toString(),
                    name = today.goalName,
                    steps = today.currentSteps,
                    id = LocalDate.now().dayOfYear   // Make sure inserting only one data each day
                )
            )
            today.currentSteps = 0
            dailyRepository.updateDaily(today)
        }

           return Result.success()

    }
}