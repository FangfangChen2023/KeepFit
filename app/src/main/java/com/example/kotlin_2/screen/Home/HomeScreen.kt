package com.example.kotlin_2.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text

import androidx.lifecycle.Observer
import com.example.kotlin_2.customComponents.CustomProgressBar
import com.example.kotlin_2.data.model.DailyStatus
import com.example.kotlin_2.screen.Goal.GoalViewModel
import com.example.kotlin_2.screen.Home.HomeViewModel
import com.example.kotlin_2.screen.Setting.SettingsViewModel
import java.lang.Math.abs
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime



@Composable
fun HomeScreen(homeViewModel:HomeViewModel) {
//    var steps by remember { mutableStateOf(0) }
    var stepsInput by remember { mutableStateOf(0) }
    val focusManager = LocalFocusManager.current
    var inputEnabled   by remember { mutableStateOf(true) }
    /*var allGoals: List<GoalItem> by remember { mutableStateOf( emptyList()) }
        goalViewModel.goals.observeForever {
            allGoals = it
        }*/
    var currentDaily: DailyStatus by remember {mutableStateOf (
        DailyStatus(currentSteps = 0, todayDate = LocalDate.now().toString(), goalName ="test", goalSteps = 5000)
    ) }
    //TODO on restart of the app it always first displays the default goal, then when you switch between tabs it displays the currently active goal?
    if(homeViewModel.isDailyDBInitialized()){
        homeViewModel.dailyDB.observeForever {
            currentDaily = it
        }
    }

    /*var allGoals: List<GoalItem> by remember { mutableStateOf( emptyList()) }
        goalViewModel.goals.observeForever {
            allGoals = it
        }*/
    /*
    homeViewModel.isDailyNotNull.observeForever(Observer {
        inputEnabled = it
    })*/


    /*TopAppBar(
        title = { Text("iWalk") },
        actions = {
            // RowScope here, so these icons will be placed horizontally
            IconButton(onClick = {settingsViewModel.navigateOnClick()}) {
                Icon(Icons.Filled.Settings, contentDescription = "Localized description")
            }
        }
    )*/

                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.height(75.dp))
                    Text(
                        text = "Today",
                        fontSize = MaterialTheme.typography.h4.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(30.dp))

                    CustomProgressBar(
                        indicatorValue = currentDaily.currentSteps,
                        //smallText = "default",
                        currentDaily = currentDaily
                    )

                    Spacer(Modifier.height(30.dp))
                    //TextField(value = "Progress: " +"h"+ "%" , onValueChange = "h" )

                    Spacer(Modifier.height(20.dp))
                    OutlinedTextField(
                        enabled = inputEnabled,
                        modifier = Modifier
                            .background(Color.Transparent),

                        value = stepsInput.toString(),
                        onValueChange = {
                            stepsInput = if (it.isNotEmpty()) {
                                it.toInt()
                            } else {
                                0
                            }

                        },
                        label = { Text(text = "Add Steps") },
                        placeholder = { Text(text = "0") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Blue,
                            unfocusedBorderColor = Color.Blue
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                homeViewModel.keyboardAction(stepsInput = stepsInput, currentDaily = currentDaily)

                                stepsInput = 0;
                                focusManager.clearFocus()
                            })

                    )


                }
            }