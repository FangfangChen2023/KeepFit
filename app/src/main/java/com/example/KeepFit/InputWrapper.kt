package com.example.KeepFit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class InputWrapper (
    val errorId: Int? = null,
    val value : String = ""
        ) : Parcelable