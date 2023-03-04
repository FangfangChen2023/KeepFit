package com.example.kotlin_2.screen.History

import androidx.lifecycle.ViewModel
import com.example.kotlin_2.data.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository : HistoryRepository
) :ViewModel(){

}