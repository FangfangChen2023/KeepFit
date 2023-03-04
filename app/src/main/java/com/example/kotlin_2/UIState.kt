package com.example.kotlin_2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlin_2.data.model.DailyStatus
import com.example.kotlin_2.data.model.GoalItem
import com.example.kotlin_2.data.model.Setting

data class UIState (
    var goals: LiveData<List<GoalItem>> = MutableLiveData(listOf(
        GoalItem(name = "default",
            steps = 5000,
            active = true))),
    var setting: LiveData<Setting> = MutableLiveData(),
    var dailyDB: LiveData<DailyStatus> = MutableLiveData()

        )