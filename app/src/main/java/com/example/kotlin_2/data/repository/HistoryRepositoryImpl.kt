package com.example.kotlin_2.data.repository

import com.example.kotlin_2.data.dao.HistoryDao
import com.example.kotlin_2.data.model.HistoryItem
import kotlinx.coroutines.flow.Flow

class HistoryRepositoryImpl(
    private val dao: HistoryDao
):HistoryRepository {
    override suspend fun insertHistory(historyItem: HistoryItem) {
        dao.insertHistory(historyItem)
    }

    override suspend fun deleteHistory(historyItem: HistoryItem) {
        dao.deleteHistory(historyItem)
    }

    override suspend fun getHistory(id: Int): HistoryItem? {
        return dao.getHistory(id)
    }

    override fun getAllHistory(): Flow<List<HistoryItem>> {
        return dao.getHistory()
    }
}