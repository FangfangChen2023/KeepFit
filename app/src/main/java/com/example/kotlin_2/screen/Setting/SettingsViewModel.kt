package com.example.kotlin_2.screen.Setting

import androidx.lifecycle.*
import com.example.kotlin_2.data.model.Setting
import com.example.kotlin_2.data.repository.SettingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_2.screen.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingRepository : SettingRepository
) : ViewModel() {

    lateinit var setting: LiveData<Setting>

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (settingRepository.isEmpty()) {
                settingRepository.insertSetting(Setting(false, true))
            }
            setting = settingRepository.getSetting()
        }
    }

    fun isSettingDBInitialized() = ::setting.isInitialized

    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.EditableGoals -> {
                viewModelScope.launch(Dispatchers.IO) {
                    var cur = settingRepository.getSetting().value
                    if (!event.enabled) {
                        if (cur == null) {
                            settingRepository.insertSetting(Setting(false, true))
                        } else {
                            cur.editable = true
                            if (cur != null) settingRepository.updateSetting(cur)
                        }
                    } else {
                        if (cur == null) {
                            settingRepository.insertSetting(Setting(false, false))
                        } else {
                            cur.editable = false
                            if (cur != null) settingRepository.updateSetting(cur)
                        }
                    }
                }
            }
            is UIEvent.HistoricalActivityRecording -> {
            }


            //var date = datePref.getString("date", null)
            //var curdate = currentDate.toString()
            //Toast.makeText(context, "commited current date, $curdate, $date", Toast.LENGTH_SHORT).show()
            else -> {}
        }
    }
}