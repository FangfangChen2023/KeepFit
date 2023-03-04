package com.example.kotlin_2.data.repository

import androidx.lifecycle.LiveData
import com.example.kotlin_2.data.model.GoalItem
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    suspend fun insertGoal(goalItem: GoalItem)

    suspend fun deleteGoal(goalItem: GoalItem)

    suspend fun getGoal(id: Int) : GoalItem?

    suspend fun getAllGoals() : LiveData<List<GoalItem>>

    suspend fun getActiveGoal(): GoalItem?

    suspend fun setActiveGoal(item: GoalItem)

    suspend fun updateGoal(goalItem: GoalItem)
}