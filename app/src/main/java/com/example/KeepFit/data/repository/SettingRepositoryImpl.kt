package com.example.KeepFit.data.repository

import androidx.lifecycle.LiveData
import com.example.KeepFit.data.dao.SettingDao
import com.example.KeepFit.data.model.Setting

class SettingRepositoryImpl(
    private val settingDao: SettingDao
) : SettingRepository {

    override suspend fun deleteSetting(setting: Setting){
        settingDao.deleteSetting(setting)
    }

    override suspend fun insertSetting(setting: Setting){
        settingDao.insertSetting(setting)
    }

    /*override suspend fun getEditableSetting(): Setting?{
        return settingDao.getEditableSetting()
    }

    override suspend fun getHistoricalSetting(): Setting?{
        return settingDao.getHistoricalSetting()
    }*/

    override suspend fun getSetting(): LiveData<Setting> {
        return settingDao.getSetting()
    }

    override suspend fun updateSetting(setting: Setting) {
        if (setting != null) {
            settingDao.updateSetting(setting)
        }
    }

    override suspend fun isEmpty(): Boolean {
        return settingDao.getSetting().value == null
    }
}