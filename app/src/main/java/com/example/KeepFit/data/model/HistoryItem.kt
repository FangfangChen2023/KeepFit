package com.example.KeepFit.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryItem(
    val date: String,
    var name: String,
    var steps: Int,
    @PrimaryKey var id:Int? = null
)

