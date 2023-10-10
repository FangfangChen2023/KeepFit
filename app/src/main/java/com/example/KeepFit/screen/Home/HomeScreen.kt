package com.example.KeepFit.screen


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
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text

import com.example.KeepFit.customComponents.CustomProgressBar
import com.example.KeepFit.data.model.DailyStatus
import com.example.KeepFit.screen.Home.HomeViewModel
import com.example.KeepFit.screen.Setting.SettingsViewModel
import java.time.LocalDate


@Composable
fun HomeScreen(
    homeViewModel:HomeViewModel,
    settingsViewModel: SettingsViewModel
) {
    var steps by remember { mutableStateOf(0) }
    var stepsInput by remember { mutableStateOf(0) }
    val focusManager = LocalFocusManager.current

    // Setting
    var editableNormal by remember { mutableStateOf(true) }

    settingsViewModel.changeSettings.observeForever{
        editableNormal = it.getBoolean("editableNormal", true)
    }


    var currentDaily: DailyStatus by remember {
        mutableStateOf (
            DailyStatus(currentSteps = 0,
                todayDate = LocalDate.now().toString(),
                goalName ="default", goalSteps = 5000
            )
    ) }
    //TODO on restart of the app it always first displays the default goal, then when you switch between tabs it displays the currently active goal?
    if(homeViewModel.isDailyDBInitialized()){
        homeViewModel.dailyDB.observeForever {
            currentDaily = it
        }
    }

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
                        enabled = editableNormal,
//                        isError = true,
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