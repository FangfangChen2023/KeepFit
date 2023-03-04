package com.example.kotlin_2.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity
    data class Setting(
        @ColumnInfo var historical : Boolean,
        @ColumnInfo var editable : Boolean,
        @PrimaryKey(autoGenerate = true) var id:Int?=null
    )
