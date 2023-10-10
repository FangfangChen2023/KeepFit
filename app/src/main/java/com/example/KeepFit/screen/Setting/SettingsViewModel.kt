package com.example.KeepFit.screen.Setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

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