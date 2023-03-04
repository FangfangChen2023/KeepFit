package com.example.kotlin_2.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kotlin_2.data.model.Setting

@Dao
interface SettingDao {
    @Delete
    fun deleteSetting(setting: Setting)

    @Insert
    fun insertSetting(setting: Setting)

    /*@Query("SELECT * FROM Setting WHERE editable=1")
    fun getEditableSetting(): Setting?

    @Query("SELECT * FROM Setting WHERE historical=1")
    fun getHistoricalSetting(): Setting?*/

    @Query("SELECT * FROM Setting")
    fun getSetting(): LiveData<Setting>

    @Update
    fun updateSetting(setting: Setting)
}