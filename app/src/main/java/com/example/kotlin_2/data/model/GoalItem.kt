package com.example.kotlin_2.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GoalItem (
    @ColumnInfo(name = "name") var name : String,
    @ColumnInfo(name = "steps") var steps : Int,
    @ColumnInfo var active : Boolean,
    @PrimaryKey var id:Int?=null
)