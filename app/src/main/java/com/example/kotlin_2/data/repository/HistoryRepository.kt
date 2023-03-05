package com.example.kotlin_2.data.repository

import com.example.kotlin_2.data.model.HistoryItem
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun insertHistory(historyItem: HistoryItem)

    suspend fun deleteHistory(historyItem: HistoryItem)

    suspend fun getHistory(id: Int) : HistoryItem?

    fun getAllHistory() : List<HistoryItem>

    suspend fun updateHistory(historyItem: HistoryItem)
}