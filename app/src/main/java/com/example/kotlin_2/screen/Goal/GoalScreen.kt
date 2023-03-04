package com.example.kotlin_2.screen

//import DataBaseHandler
import android.annotation.SuppressLint
import android.util.Log

import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.kotlin_2.GoalListItem
import com.example.kotlin_2.data.model.GoalItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import com.example.kotlin_2.data.model.DailyStatus
import com.example.kotlin_2.screen.Goal.GoalViewModel
import com.example.kotlin_2.screen.Home.HomeViewModel
import com.example.kotlin_2.screen.Setting.SettingsViewModel
import java.time.LocalDate

@Composable
fun ButtonDialogExample(goalViewModel: GoalViewModel, homeViewModel: HomeViewModel) {
    // State variable for dialog visibility
    var showDialog by remember { mutableStateOf(false) }
    // State variables for text fields
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf(0) }

    var allGoals: List<GoalItem> by remember { mutableStateOf( emptyList()) }
    goalViewModel.goals.observeForever {
        allGoals = it
    }
    var current: DailyStatus by remember { mutableStateOf(DailyStatus(currentSteps = 0, todayDate = LocalDate.now().toString(), goalName ="default", goalSteps = 5000)) }
    homeViewModel.dailyDB.observeForever {
        current = it
    }

    /*var stepsInput by remember { mutableStateOf(text2) }
    var nameInput by remember { mutableStateOf(text1) }*/
    //var newGoal = GoalItem("new", 0, false)
    val focusManager = LocalFocusManager.current
    // Button to show dialog
    Button(onClick = { showDialog = true }) {
        Text("Add Goal")
    }

    // Dialog with two text fields and a confirm button
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Create new goal") },
            text = {
                Column {
                    TextField(
                        value = text1,
                        onValueChange = { text1 = it },
                        label = { Text("New goals name")},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            })
                    )
                    TextField(
                        value = text2.toString(),
                        onValueChange = { text2 = it.toInt() },
                        label = { Text("New goal target") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            })
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    goalViewModel.onAddGoal(text1, text2, homeViewModel)
                    focusManager.clearFocus()
                    /*text1 = it
                    text2 = it*/
                    showDialog = false
                    // Do something with text1 and text2 here
                }) {
                    Text("Confirm")
                }
            }
        )
    }
}

//@Preview
@SuppressLint("UnrememberedMutableState")
@Composable
fun GoalScreen(goalViewModel:GoalViewModel, homeViewModel: HomeViewModel) {
    /*TopAppBar(
        title = { Text("iWalk") },
        actions = {
            // RowScope here, so these icons will be placed horizontally
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Settings, contentDescription = "Localized description")
            }
        }
    )*/


    Column(
        modifier = Modifier
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(25.dp))

        //Text(text = "Goals")

        //var goalRepository by remember {mutableStateOf(GoalRepository())}
        //var allGoals by remember {mutableStateOf(goalRepository.getAllGoals())}
        //val context = LocalContext.current
        //val db by remember { mutableStateOf(DataBaseHandler(context)) }
        //var allGoals = db.readGoals()
        //var allGoals by remember { mutableStateOf(db.readGoals()) }
        /*homeViewModel.currentSteps.observeForever(Observer {
            steps = it
    })*/
        var allGoals: List<GoalItem> by remember { mutableStateOf(emptyList()) }
        goalViewModel.goals.observeForever {
            allGoals = it
        }
        var current: DailyStatus by remember {
            mutableStateOf(
                DailyStatus(
                    currentSteps = 0,
                    todayDate = LocalDate.now().toString(),
                    goalName = "default",
                    goalSteps = 5000
                )
            )
        }
        homeViewModel.dailyDB.observeForever {
            current = it
        }
        //val sharedPreferenceGoal =  context.getSharedPreferences("goal", Context.MODE_PRIVATE)
        //val editorGoal = sharedPreferenceGoal.edit()

        Spacer(Modifier.height(10.dp))
        ButtonDialogExample(goalViewModel, homeViewModel)
        Spacer(Modifier.height(10.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            itemsIndexed(items = allGoals) { index,
                                             goalItem ->
                Log.d("goal", index.toString())
                GoalListItem(goalItem = goalItem,
                    onClick = {
                        goalViewModel.onClickOnGoal(
                            goalItem,
                            current,
                            homeViewModel
                        )
                    }
                    /*onClick = {
                            db.updateGoals(goalItem)
                            allGoals = db.readGoals()
                            editorGoal.putString("goal", goalItem.name);
                            editorGoal.commit();
                        }*/
                )
                if (!goalItem.active) {
                    Row() {
                        /*TODO change this into prettier button*/
                        IconButton(onClick = {
                            goalViewModel.onUpdateGoal(goalItem)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit"
                            )
                        }
                        IconButton(onClick = {
                            goalViewModel.onDeleteGoal(goalItem)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
                        /* TODO make goal editable  depending on preferences*/
                    }
                }
                Spacer(Modifier.height(50.dp))
            }
        }
    }
}

        /*var stepsInput by remember { mutableStateOf(0) }
        var nameInput by remember { mutableStateOf("...") }
        //var newGoal = GoalItem("new", 0, false)
        val focusManager = LocalFocusManager.current

        OutlinedTextField(
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
            *//*TODO this should be a separate dialog and test for errors -> name has to be unique, steps too?, no emojis or numbers as names, also 0 steps does not make sense*//*
            label = { Text(text = "Add Steps for New Goal") },
            placeholder = { Text(text = "") },
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
                    focusManager.clearFocus()
                })
        )

        OutlinedTextField(
            modifier = Modifier
                .background(Color.Transparent),

            value = nameInput,
            onValueChange = {
                nameInput = if (it.isNotEmpty()) {
                    it
                } else {
                    ""
                }

            },
            label = { Text(text = "Add Name for New Goal") },
            placeholder = { Text(text = "") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Blue
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    //steps += stepsInput
                    //var steps = sharedPreference.getInt("currentSteps", 0)
                    *//*newGoal.name = nameInput
                    newGoal.steps = stepsInput
                    db.insertGoal(newGoal)
                    allGoals = db.readGoals()*//*
                    goalViewModel.onAddGoal(stepsInput, nameInput, homeViewModel)
                    focusManager.clearFocus()
                    nameInput = "..."
                    stepsInput = 0;
                })
        )
    }*/


