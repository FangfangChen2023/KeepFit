package com.example.kotlin_2.screen.Goal

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_2.Event
import com.example.kotlin_2.UIState
import com.example.kotlin_2.data.model.DailyStatus
import com.example.kotlin_2.data.model.GoalItem
import com.example.kotlin_2.data.model.Setting
import com.example.kotlin_2.data.repository.DailyRepository
import com.example.kotlin_2.data.repository.GoalRepository
import com.example.kotlin_2.data.repository.SettingRepository
import com.example.kotlin_2.screen.Home.HomeViewModel
import com.example.kotlin_2.screen.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
    private val dailyRepository: DailyRepository,
    private val settingRepository: SettingRepository,
) : ViewModel() {
    //val allGoals : LiveData<List<GoalItem>>
    lateinit var goals: LiveData<List<GoalItem>>
//    private val _uistate = mutableStateOf(UIState())
//    val uistate : State<UIState> = _uistate

    init {
        viewModelScope.launch {
            goals = goalRepository.getAllGoals()
//            _uistate.value = uistate.value.copy(
//                goals = goalRepository.getAllGoals()
//            )
//            Log.d("Goals: ", _uistate.value.toString())
        }
    }

    lateinit var setting: LiveData<Setting>

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (settingRepository.isEmpty()){
                settingRepository.insertSetting(Setting(false, true))
            }
            setting = settingRepository.getSetting()
        }
    }
    fun isSettingDBInitialized() = ::setting.isInitialized

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var message = MutableLiveData<String>()



    //var snackbarVisibleState = MutableLiveData<Boolean>()
    //var setSnackBarState = MutableLiveData<Boolean>()

    //var message : MutableLiveData<String>
    //    get() = statusMessage
        //set(value) {statusMessage = value}

    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.SetGoalActive -> {
                viewModelScope.launch(Dispatchers.IO) {
                    goalRepository.setActiveGoal(event.goalItem)
                    goals = goalRepository.getAllGoals()
//                    _uistate.value = uistate.value.copy(
//                        goals = goalRepository.getAllGoals()
//                    )
                    // Refresh daily status
                    dailyRepository.refreshDailyStatus(event.goalItem)

//                    val daily = dailyRepository.getDaily()
//                        UIEvent.RefreshDailyStatus(daily)
                }
            }

            /*is UIEvent.DeactivateSnackBar -> {
                viewModelScope.launch(Dispatchers.Main.immediate) {
                    snackbarVisibleState.setValue(false)
                }
            }*/

            // Maybe click on goals can be used to edit goals?
            is UIEvent.ClickOnGoal -> {
                /*add functionality what should happen when you click on goal in database*/
                viewModelScope.launch(Dispatchers.IO) {
//                    goalRepository.setActiveGoal(event.goalItem)
//
//                    event.dailyStatus.goalName = event.goalItem.name
//                    event.dailyStatus.goalSteps = event.goalItem.steps
//                    // Should we reset the currentSteps after users set a new goal?
//                    event.dailyStatus.currentSteps = 0
//                    dailyRepository.updateDaily(event.dailyStatus)
                }
            }

            is UIEvent.AddGoal -> {
                //var messageNew = "test"
                //message = MutableLiveData(messageNew)
                var fault = -1
                viewModelScope.launch(Dispatchers.IO){//Dispatchers.IO) {
                    //some checks that the name is legal, not duplicate, steps are legal etc.
                    if (event.steps == 0) {
                        //snackbarVisibleState.postValue(true)
                        viewModelScope.launch(Dispatchers.Main.immediate) {
                        fault = 0
                    }
                        return@launch
                    } else if (goalRepository.getGoalByName(event.name) != null) {
                        //snackbarVisibleState.postValue(true)
                        viewModelScope.launch(Dispatchers.Main.immediate) {
                            fault = 1
                        }
                    } else if (goalRepository.getGoalBySteps(event.steps) != null) {
                        fault = 2
                        return@launch
                    } else {
                        var newGoal = GoalItem(event.name, event.steps, false)
                        // if there is no other goal, make that goal active
                        if (goalRepository.getActiveGoal() == null) {
                            newGoal.active = true
                            //add to day status
                            if (dailyRepository.getDaily().value == null) {
                                var status = DailyStatus(
                                    currentSteps = 0,
                                    todayDate = LocalDate.now().toString(),
                                    goalName = newGoal.name,
                                    goalSteps = newGoal.steps
                                )
                                dailyRepository.insertDaily(status)
                            } else {
                                var currentDaily = dailyRepository.getDaily().value
                                currentDaily!!.currentSteps = 0
                                currentDaily!!.todayDate = LocalDate.now().toString()
                                currentDaily!!.goalName = newGoal.name
                                currentDaily!!.goalSteps = newGoal.steps
                                dailyRepository.updateDaily(currentDaily)
                            }
                        }
                        goalRepository.insertGoal(newGoal)
                        //messageNew ="You successfully created a new goal!"
                    }
                    //return@launch
                }
                var messageNew = ""
                print(fault)
                when (fault) {
                    0 -> {
                        messageNew = "Your goal must have more than 0 steps."
                    }

                    1 -> {
                        messageNew = "Please give your new goal a unique name."
                    }

                    2 -> {
                        messageNew = "You cannot make another goal with the same amount of steps."

                    } else -> {
                        messageNew ="You successfully created a new goal!"
                    }
                }
                //messageNew = "test2"
                writeMessageToSnackbar(messageNew)
                //snackbarVisibleState = MutableLiveData(true)
            }

            is UIEvent.DeleteGoal -> {
                viewModelScope.launch(Dispatchers.IO) {
                    goalRepository.deleteGoal(event.goalItem)
                }
            }

            is UIEvent.EditGoal -> {
                viewModelScope.launch(Dispatchers.IO) {
                    goalRepository.updateGoal(event.goalItem)
                }
            }
            is UIEvent.DeleteActiveGoal -> {
                //viewModelScope.launch(Dispatchers.Main.immediate){//Dispatchers.IO) {
                //message = MutableLiveData("An active goal cannot be deleted.")
                //snackbarVisibleState = MutableLiveData(true)
                    //return
                writeMessageToSnackbar("An active goal cannot be deleted.")
            }
            else -> Unit
        }
    }

    fun writeMessageToSnackbar(msg: String){
        message = MutableLiveData(msg)
    }


//    fun onClickOnGoal(goalItem: GoalItem, dailyStatus: DailyStatus, homeViewModel: HomeViewModel) {
//        /*add functionality what should happen when you click on goal in database*/
//        viewModelScope.launch(Dispatchers.IO) {
//            goalRepository.setActiveGoal(goalItem)
//
//            dailyStatus.goalName = goalItem.name
//            dailyStatus.goalSteps = goalItem.steps
//            homeViewModel.onGoalClick(dailyStatus)
//
//        }
//    }

//    fun onAddGoal(name: String, steps: Int, homeViewModel: HomeViewModel) {
//        var newGoal = GoalItem(name, steps, false)
//        // if there is no other goal, make that goal active
//        viewModelScope.launch(Dispatchers.IO) {
//            if (goalRepository.getActiveGoal() == null) {
//                newGoal.active = true
//                //add to day status
//                var status = DailyStatus(currentSteps = 0, todayDate = LocalDate.now().toString(), goalName = newGoal.name, goalSteps = newGoal.steps)
//                //dailyRepository.insertDaily(status)
//                homeViewModel.onAddClick(status)
//            }
//            goalRepository.insertGoal(newGoal)
//        }
//
//    }

//    fun onDeleteGoal(goalItem: GoalItem){
//        viewModelScope.launch(Dispatchers.IO) {
//            goalRepository.deleteGoal(goalItem)
//        }
//    }

//    fun onEditGoal(goalItem: GoalItem){
//        viewModelScope.launch(Dispatchers.IO) {
//            goalRepository.deleteGoal(goalItem)
//        }
//    }

}