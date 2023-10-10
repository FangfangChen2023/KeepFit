package com.example.KeepFit.data.repository

import com.example.KeepFit.data.dao.HistoryDao
import com.example.KeepFit.data.model.HistoryItem

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

    override fun getAllHistory(): List<HistoryItem> {
        return dao.getAllHistory()
    }

    override suspend fun updateHistory(historyItem: HistoryItem) {
        dao.updateHistory(historyItem)
    }
}