package com.example.kotlin_2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

//When one goal is set active, we use the goal data to create a daily data

@Entity
data class DailyStatus(
    var currentSteps: Int,
    //todayDate will be generated by us (not by users) everytime when first create the this instance
    var todayDate: String,
    //Only allow one dailystatus
    @PrimaryKey(autoGenerate = true) var id:Int? = null,
    //I would rather save the goal name for history purposes - goal name should stay there when we delete the goal after the fact? (also name is supposed to be unqiue)
    //var goalId: Int
    var goalName: String,
    var goalSteps: Int
)
