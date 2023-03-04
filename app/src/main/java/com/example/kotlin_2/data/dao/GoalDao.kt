package com.example.kotlin_2.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kotlin_2.data.model.GoalItem

@Dao
interface GoalDao {
    @Query("SELECT * FROM GoalItem")
    fun readGoals(): LiveData<List<GoalItem>>

    @Query("SELECT * FROM GoalItem WHERE active = 1")
    fun getActiveGoal(): GoalItem

    @Query("SELECT * FROM GoalItem WHERE name = :name")
    fun getGoalByName(name: String): GoalItem?

    @Query("SELECT * FROM GoalItem WHERE steps = :steps")
    fun getGoalBySteps(steps: Int): GoalItem?

    @Insert
    fun insertGoal(goalItem: GoalItem)

    @Update
    fun updateGoal(goalItem: GoalItem)

    @Update
    fun updateGoals(goals: List<GoalItem>): Int

    @Delete
    fun deleteGoal(goalItem: GoalItem)


}