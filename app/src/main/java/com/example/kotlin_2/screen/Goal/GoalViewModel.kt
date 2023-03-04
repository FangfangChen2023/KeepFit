package com.example.kotlin_2.screen.Goal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_2.data.model.DailyStatus
import com.example.kotlin_2.data.model.GoalItem
import com.example.kotlin_2.data.repository.GoalRepository
import com.example.kotlin_2.screen.Home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
) : ViewModel() {
    //val allGoals : LiveData<List<GoalItem>>
    lateinit var goals : LiveData<List<GoalItem>>

    init {
        viewModelScope.launch {
              goals = goalRepository.getAllGoals()
        }
    }




    fun onClickOnGoal(
        goalItem: GoalItem,
        dailyStatus: DailyStatus,
        homeViewModel: HomeViewModel
    ) {
        /*add functionality what should happen when you click on goal in database*/
        viewModelScope.launch(Dispatchers.IO) {
            goalRepository.setActiveGoal(goalItem)

            dailyStatus.goalName = goalItem.name
            dailyStatus.goalSteps = goalItem.steps
            homeViewModel.onGoalClick(dailyStatus)
        }
    }

    fun onAddGoal(name: String, steps: Int, homeViewModel: HomeViewModel) {
        var newGoal = GoalItem(name, steps, false)
        // if there is no other goal, make that goal active
        viewModelScope.launch(Dispatchers.IO) {
            if (goalRepository.getActiveGoal() == null) {
                newGoal.active = true
                //add to day status
                var status = DailyStatus(currentSteps = 0, todayDate = LocalDate.now().toString(), goalName = newGoal.name, goalSteps = newGoal.steps)
                //dailyRepository.insertDaily(status)
                homeViewModel.onAddClick(status)
            }
            goalRepository.insertGoal(newGoal)
        }
    }

    fun onDeleteGoal(goalItem: GoalItem){
        viewModelScope.launch(Dispatchers.IO) {
            goalRepository.deleteGoal(goalItem)
        }
    }

    fun onUpdateGoal(goalItem: GoalItem){
        viewModelScope.launch(Dispatchers.IO) {
            goalRepository.updateGoal(goalItem)
        }
    }

}