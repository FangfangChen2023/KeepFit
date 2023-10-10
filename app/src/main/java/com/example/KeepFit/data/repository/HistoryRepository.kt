package com.example.KeepFit.data.repository

import com.example.KeepFit.data.model.HistoryItem

interface HistoryRepository {

    suspend fun insertHistory(historyItem: HistoryItem)

    suspend fun deleteHistory(historyItem: HistoryItem)

    suspend fun getHistory(id: Int) : HistoryItem?

    fun getAllHistory() : List<HistoryItem>

    suspend fun updateHistory(historyItem: HistoryItem)
}