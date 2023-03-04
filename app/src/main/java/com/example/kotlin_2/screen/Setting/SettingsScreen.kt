package com.example.kotlin_2.screen.Setting

//import DataBaseHandler
import android.app.Application

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kotlin_2.data.model.Setting
import com.example.kotlin_2.screen.UIEvent


@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {

    var setting: Setting by remember {mutableStateOf(Setting(false, true))}
    if (settingsViewModel.isSettingDBInitialized()) {
        settingsViewModel.setting.observeForever {
            setting = it
        }
    }
    /*TopAppBar(
        title = { Text("iWalk") },
        actions = {
            // RowScope here, so these icons will be placed horizontally
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Settings, contentDescription = "Localized description")
            }
        }
    )*/

    //random comment
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(15.dp))
        Text(
            text = "Settings",
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold,
        )
    }

        val option1Enabled = remember { mutableStateOf(setting.editable)}//false) }
        val option2Enabled = remember { mutableStateOf(true) }
        val option3Enabled = remember { mutableStateOf(false) }

        //TODO we have to refactor this to use ViewModel (no data should be stored here) also use SharedPreferences to save the Settings so that they can be accessed in the rest of the app

        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(Modifier.height(120.dp))
            LazyColumn {
                item {
                    Text("Editable Goals",
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold)
                    Switch(
                        checked = option1Enabled.value,
                        onCheckedChange = { settingsViewModel.onEvent(
                            UIEvent.EditableGoals(option1Enabled.value))
                            //setting.editable = it}
                            option1Enabled.value = it }
                    )
                }
                item {
                    Text("Normal Activity Recording",
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold)
                    Switch(
                        checked = option2Enabled.value,
                        onCheckedChange = { checked ->
                            option2Enabled.value = checked
                            if (checked) {
                                option3Enabled.value = false
                            }
                        }
                    )
                }
                item {
                    Text("Historical Activity Recording",
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold)
                    Switch(
                        checked = option3Enabled.value,
                        onCheckedChange = { checked ->
                            option3Enabled.value = checked
                            if (checked) {
                                option2Enabled.value = false
                            }
                        }
                    )
                }
            }
        }
    }