package com.example.KeepFit.screen.Setting

//import DataBaseHandler

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {

    var editableGoals by rememberSaveable { mutableStateOf(true) }
    var editableNormal by rememberSaveable { mutableStateOf( true) }
    var editableHistories by rememberSaveable { mutableStateOf(false) }

//    val sharedPreferences = LocalContext.current.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

//    LaunchedEffect(Unit) {
//        option1Enabled = sharedPreferences.getBoolean("option1Enabled", true)
//        option2Enabled = sharedPreferences.getBoolean("option2Enabled", true)
//        option3Enabled = sharedPreferences.getBoolean("option3Enabled", false)
//    }
    val settingsCopy = settingsViewModel.changeSettings.value

    if(settingsCopy != null){
        editableGoals = settingsCopy.getBoolean("editableGoals", true)
    }
    if(settingsCopy != null){
        editableNormal = settingsCopy.getBoolean("editableNormal", true)
    }
    if(settingsCopy != null){
        editableHistories = settingsCopy.getBoolean("editableHistories", false)
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

        //TODO we have to refactor this to use ViewModel (no data should be stored here) also use SharedPreferences to save the Settings so that they can be accessed in the rest of the app

        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(Modifier.height(120.dp))
            LazyColumn {
                item {
                    Text("Editable Goals",
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold)
                    Switch(
                        checked = editableGoals,
                        onCheckedChange = { checked ->
                            editableGoals = checked

                            settingsViewModel.changeEditableGoals(editableGoals)

                        }
                    )
                }
                item {
                    Text("Normal Activity Recording",
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold)
                    Switch(
                        checked = editableNormal,
                        onCheckedChange = { checked ->
                            editableNormal = checked
                            editableHistories = !checked

                            settingsViewModel.historicalActivityRecording(editableHistories)
                            settingsViewModel.normalActivityRecording(editableNormal)
                        }
                    )
                }
                item {
                    Text("Historical Activity Recording",
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold)
                    Switch(
                        checked = editableHistories,
                        onCheckedChange = { checked ->
                            editableHistories = checked
                            editableNormal = !checked

//                            // Save the option enabled state to SharedPreferences
//                            with(sharedPreferences.edit()) {
//                                putBoolean("option3Enabled", checked)
//                                putBoolean("option2Enabled", !checked)
//                                apply()
//                            }
                            settingsViewModel.historicalActivityRecording(editableHistories)
                            settingsViewModel.normalActivityRecording(editableNormal)
                        }
                    )
                }
            }
        }
            /*Spacer(Modifier.height(120.dp))
            LazyColumn {

                item {
                    Text("Editable Goals",
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold)
                    Switch(
                        checked = option1Enabled,
                        onCheckedChange = { option1Enabled = it }
                    )
                }
                item {
                    Text("Normal Activity Recording",
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold)
                    Switch(
                        checked = option2Enabled,
                        onCheckedChange = { checked ->
                            option2Enabled = checked
                            option3Enabled = !checked
                        }
                    )
                }
                item {
                    Text("Historical Activity Recording",
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold)
                    Switch(
                        checked = option3Enabled,
                        onCheckedChange = { checked ->
                            option3Enabled = checked
                            option2Enabled = !checked
                        }
                    )
                }
            }
        }*/
}