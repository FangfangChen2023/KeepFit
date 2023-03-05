package com.example.kotlin_2.screen.Setting

import android.app.Application
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_2.R
import com.example.kotlin_2.ReplyRoute
import com.example.kotlin_2.ReplyTopLevelDestination
import com.example.kotlin_2.data.model.HistoryItem
import com.example.kotlin_2.screen.Home.HomeViewModel
import com.example.kotlin_2.screen.HomeScreen
import java.time.LocalDateTime

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    val changeSettings = MutableLiveData(
        application.getSharedPreferences("my_preferences", Application.MODE_PRIVATE))

    fun changeEditableGoals(editableGoals : Boolean) {
        with(changeSettings.value!!.edit()){
            putBoolean("editableGoals", editableGoals)
            apply()
        }

    }
    fun historicalActivityRecording(editableHistories : Boolean){
        with(changeSettings.value!!.edit()){
            putBoolean("editableHistories", editableHistories)
            apply()
        }

    }

    fun normalActivityRecording(editableNormal : Boolean) {
        with(changeSettings.value!!.edit()){
            putBoolean("editableNormal", editableNormal)
            apply()
        }

    }


        //var date = datePref.getString("date", null)
        //var curdate = currentDate.toString()
        //Toast.makeText(context, "commited current date, $curdate, $date", Toast.LENGTH_SHORT).show()
}