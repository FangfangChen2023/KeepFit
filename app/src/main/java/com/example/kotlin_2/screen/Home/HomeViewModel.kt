package com.example.kotlin_2.screen.Home


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.*
import com.example.kotlin_2.data.model.DailyStatus
import com.example.kotlin_2.data.model.GoalItem
import com.example.kotlin_2.data.repository.DailyRepository
import com.example.kotlin_2.data.repository.DailyRepositoryImpl
import com.example.kotlin_2.data.repository.GoalRepository
import com.example.kotlin_2.data.repository.GoalRepositoryImpl
import com.example.kotlin_2.screen.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dailyRepository: DailyRepository,
    private val goalRepository: GoalRepository

) : ViewModel() {
    //lateinit var goals : LiveData<List<GoalItem>>
    lateinit var dailyDB: LiveData<DailyStatus>
    /*lateinit var dailySteps: MutableLiveData<Int> //= MutableLiveData(dailyDB.currentSteps)
    lateinit var dailyGoalName: MutableLiveData<String> //= MutableLiveData(dailyDB.goalName)
    lateinit var dailyGoalSteps: MutableLiveData<Int> //= MutableLiveData(dailyDB.goalSteps)*/
//    lateinit var dailyStatus: LiveData<List<DailyStatus>>


    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (dailyRepository.checkIfEmpty()) {
                dailyRepository.insertDaily(
                    DailyStatus(
                        currentSteps = 0,
                        todayDate = LocalDate.now().toString(),
                        goalName = "default",
                        goalSteps = 5000
                    )
                )
                goalRepository.insertGoal(
                    GoalItem(
                        name = "default",
                        steps = 5000,
                        active = true
                    )
                )
            }
            dailyDB = dailyRepository.getDaily()
            //dailySteps = MutableLiveData(dailyDB.currentSteps)

            /*if (dailyDB == null) {
                isDailyNotNull.postValue(true)
            }*/
//            this@HomeViewModel.dailyDate = dailyRepository.getDaily()!!.todayDate
        }
    }

    fun isDailyDBInitialized() = ::dailyDB.isInitialized

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event:UIEvent){
//        when(event){
//            is UIEvent.RefreshDailyStatus -> {
//                viewModelScope.launch(Dispatchers.IO) {
//                    dailyDB = event.dailyStatus
//                }
//
//            }
//            else -> Unit
//        }

    }

//    var currentStepsPref = application.getSharedPreferences("currentSteps", Application.MODE_PRIVATE)
//    var datePref = application.getSharedPreferences("date",Application.MODE_PRIVATE)
//    var goalPref = application.getSharedPreferences("goal",Application.MODE_PRIVATE)

//    val editor = currentStepsPref.edit()
//    val editorDay = datePref.edit()
//    val editorGoal = goalPref.edit()

//    suspend fun onGoalClick(newValue:DailyStatus){
//        dailyRepository.updateDaily(newValue)
//    }
//    suspend fun onAddClick(newValue: DailyStatus){
//        dailyRepository.insertDaily(newValue)
//    }
    fun keyboardAction(stepsInput:Int=0, currentDaily:DailyStatus) {
        currentDaily.currentSteps += stepsInput
        viewModelScope.launch(Dispatchers.IO) {
            dailyRepository.updateDaily(currentDaily)
        }

    }

    /*

    private val _currentSteps = MutableLiveData( if(dailyDB!=null) dailyDB!!.currentSteps else 0)
    val currentSteps: LiveData<Int> = _currentSteps
    var steps = if(dailyDB!=null) dailyDB.currentSteps else 0


    fun keyboardAction(
        stepsInput: Int = 0
    ) {
        _currentSteps.postValue(steps + stepsInput)
        if(dailyDB!=null) {
            dailyDB!!.currentSteps = steps + stepsInput
            viewModelScope.launch(Dispatchers.IO) {
                //update the new steps into database daily status table
                dailyRepository.updateDaily(dailyDB!!)
            }
        }


        /*var currentDate = LocalDateTime.now()
        val oldDate = datePref.getString("date", null)
        var dateMemorised: LocalDateTime? = null
        if (oldDate != null) {
            dateMemorised = LocalDateTime.parse(oldDate)
        }
        //var dateMemorised = LocalDateTime.parse(sharedPreferenceDay.getString("date", null))
        //TODO change this to days
        val start = currentDate.minute
        var end = 0
        if (dateMemorised == null) {
            end = currentDate.minute
        } else {
            end = dateMemorised.minute
        }
        //Toast.makeText(context, "$start, $end, $oldDate", Toast.LENGTH_SHORT).show()
        if (start != end && (dateMemorised != null)) {
            // next day! commit to history data base, set to default values
//            val db = DataBaseHandler(application)
            //var activeGoal = db.getActiveGoal()
            var goal = goalPref.getString("goal", null)
            if (goal == null) {
                goal = "default"
            }
            var steps = currentStepsPref.getInt("currentSteps", 0)
            //Toast.makeText(context, "something happened at least,$dateMemorised, $goal, $steps", Toast.LENGTH_SHORT).show()
            val history = HistoryItem(
                dateMemorised,
                goal,
                currentStepsPref.getInt("currentSteps", 0)
            )
//            db.insertDayStatus(history)
            //var histories = db.readHistory()
            editor.putInt("currentSteps", 0)
            editorGoal.putString("name", goal)
            editor.commit()
            editorGoal.commit()
            //Toast.makeText(context, "something happened at least,$history, $histories", Toast.LENGTH_SHORT).show()
        }
        editorDay.putString("date", currentDate.toString());
        editorDay.commit()*/

        //var date = datePref.getString("date", null)
        //var curdate = currentDate.toString()
        //Toast.makeText(context, "commited current date, $curdate, $date", Toast.LENGTH_SHORT).show()
    }*/


}