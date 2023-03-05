package com.example.kotlin_2.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class HistoryItem(
    val date: String,
    var name: String,
    var steps: Int,
    @PrimaryKey var id:Int? = null
)

