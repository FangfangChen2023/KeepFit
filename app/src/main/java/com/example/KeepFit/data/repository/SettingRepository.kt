package com.example.KeepFit.data.repository

import androidx.lifecycle.LiveData
import com.example.KeepFit.data.model.Setting

interface SettingRepository {
    suspend fun deleteSetting(setting: Setting)

    suspend fun insertSetting(setting: Setting)

    /*suspend fun getEditableSetting(): Setting?

    suspend fun getHistoricalSetting(): Setting?*/

    suspend fun getSetting(): LiveData<Setting>

    suspend fun updateSetting(setting: Setting)

    suspend fun isEmpty(): Boolean
}