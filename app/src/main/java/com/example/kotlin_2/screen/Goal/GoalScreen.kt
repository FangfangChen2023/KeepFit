package com.example.kotlin_2.screen

import android.annotation.SuppressLint
import android.app.Application
import android.app.PendingIntent.getActivity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState

import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlin_2.GoalListItem
import com.example.kotlin_2.data.model.GoalItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlin_2.UIState
import com.example.kotlin_2.data.model.DailyStatus
import com.example.kotlin_2.data.model.Setting
import com.example.kotlin_2.screen.Goal.GoalViewModel
import com.example.kotlin_2.screen.Home.HomeViewModel
import com.example.kotlin_2.screen.Setting.SettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate


@Composable
fun ButtonDialogExample(goalViewModel: GoalViewModel, homeViewModel: HomeViewModel) {
    // State variable for dialog visibility
    var showDialog by remember { mutableStateOf(false) }
    // State variables for text fields
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf(0) }

    var allGoals: List<GoalItem> by remember { mutableStateOf(emptyList()) }
    goalViewModel.goals.observeForever {
        allGoals = it
    }
//    val uistate = goalViewModel.uistate.value
//    if(uistate.goals.value!=null){
//        uistate.goals.observeForever{
//            allGoals = it
//        }
//    }


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

    /*var stepsInput by remember { mutableStateOf(text2) }
    var nameInput by remember { mutableStateOf(text1) }*/
    //var newGoal = GoalItem("new", 0, false)
    val focusManager = LocalFocusManager.current
    var (snackbarVisibleState, setSnackBarState) = remember { mutableStateOf(false) }
    var (setSnackBarText) = remember { mutableStateOf("") }
    //var (snackbarVisibleState) = remember { mutableStateOf(false) }

    goalViewModel.message.observeForever {
        setSnackBarText = it
    }
    /*goalViewModel.snackbarVisibleState.observeForever {
        snackbarVisibleState = it
    }*/


    if (snackbarVisibleState) {
        Snackbar(
            modifier = Modifier.padding(10.dp)
        ) {
            //Text("Sorry! An active goal can't be deleted!")
            Text(setSnackBarText)
            LaunchedEffect(key1 = null, block = {
                delay(3000L)
                setSnackBarState(!snackbarVisibleState)
                //goalViewModel.onEvent(UIEvent.DeactivateSnackBar(false))
            })


        }
    }
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
                        label = { Text("New goals name") },
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
                        onValueChange = { //text2 = it.toInt()
                            if (it != ""){
                                text2 = it.toInt()}
                            else{
                                text2 = 0
                            }
                        },
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
                    goalViewModel.onEvent(//setSnackBarState(!snackbarVisibleState)
                            UIEvent.AddGoal(text1, text2)
                    )
                    setSnackBarState(!snackbarVisibleState)

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
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun GoalScreen(goalViewModel: GoalViewModel, homeViewModel: HomeViewModel){ //settingsViewModel: SettingViewModel) {

    var setting: Setting by remember {mutableStateOf(Setting(false, true))}
    if (goalViewModel.isSettingDBInitialized()) {
        goalViewModel.setting.observeForever {
            setting = it
        }
    }

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
//        val uistate = goalViewModel.uistate.value
//        if(uistate.goals.value!= null){
//             uistate.goals.observeForever{
//                 allGoals = it
//            }
//        }
        goalViewModel.goals.observeForever {
            allGoals = it
        }

        /*goalViewModel.message.observeForever( Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(Context, it, Toast.LENGTH_LONG).show()

            }
        })*/
//        if(allGoals.count()==1){
//            allGoals.first().active=true
//        }
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

        val openDialog = remember { mutableStateOf(false) }
        var (snackbarVisibleState, setSnackBarState) = remember { mutableStateOf(false) }
        var (setSnackBarText) = remember { mutableStateOf("") }
        //var (snackbarVisibleState) = remember { mutableStateOf(false) }

        goalViewModel.message.observeForever {
            setSnackBarText = it
        }
        /*goalViewModel.snackbarVisibleState.observeForever {
            snackbarVisibleState = it
        }*/


        // When delete active goal, show message
        if (snackbarVisibleState) {
            Snackbar(
                modifier = Modifier.padding(10.dp)
            ) {
                //Text("Sorry! An active goal can't be deleted!")
                Text(setSnackBarText)
                LaunchedEffect(key1 = null, block = {
                    delay(3000L)
                    setSnackBarState(!snackbarVisibleState)
                    //goalViewModel.onEvent(UIEvent.DeactivateSnackBar(false))

                })


            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {

            itemsIndexed(items = allGoals) { index,
                                             goalItem ->
//                Log.d("goal", index.toString())

                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            if (goalItem.active) {
                                goalViewModel.onEvent(UIEvent.DeleteActiveGoal(GoalItem("default", 5000, false)))
                                setSnackBarState(!snackbarVisibleState)
                            }
                            else openDialog.value = true
                            //if (!goalItem.active) openDialog.value = true
                        }
                        false
                    }
                )
                // check dialog. Delete inactive goals
                if (openDialog.value) {
                    AlertDialog(
                        onDismissRequest = { openDialog.value = false },
                        title = { Text(text = "Delete Goal") },
                        text = {
                            Text("Are you sure you want to delete this goal?")
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                openDialog.value = false
                                goalViewModel.onEvent(UIEvent.DeleteGoal(goalItem))
                            }) {
                                Text("Confirm")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                openDialog.value = false
                            }) {
                                Text("Cancel")
                            }
                        },

                        )
                }

                // Goals can be swiped to dismiss and delete.
                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier.fillMaxWidth(),
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(0.25f) },
                    background = {
                        val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> Color.LightGray
                                DismissValue.DismissedToEnd -> Color.Green
                                DismissValue.DismissedToStart -> Color.Yellow
                            }
                        )
                        val alignment = when (direction) {
                            DismissDirection.StartToEnd -> Alignment.CenterStart
                            DismissDirection.EndToStart -> Alignment.CenterEnd
                        }
                        val icon = when (direction) {
                            DismissDirection.StartToEnd -> Icons.Default.Done
                            DismissDirection.EndToStart -> Icons.Default.Delete
                        }
                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 1f else 1.5f
                        )
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment,
                        ) {
                            Icon(
                                icon,
                                contentDescription = "Localized description",
                                modifier = Modifier.scale(scale)
                            )
                        }
                    },
                    dismissContent = {
                        GoalListItem(
                            goalItem = goalItem,
                            onClick = {},
                            onEvent = goalViewModel::onEvent
                        )
                    }
                )
//                GoalListItem(goalItem = goalItem,
//                    onClick = {
//                        goalViewModel.onClickOnGoal(
//                            goalItem,
//                            current,
//                            homeViewModel
//                        )
//                    }
//                    /*onClick = {
//                            db.updateGoals(goalItem)
//                            allGoals = db.readGoals()
//                            editorGoal.putString("goal", goalItem.name);
//                            editorGoal.commit();
//                        }*/
//                )
                if (!goalItem.active) {
                    Row() {
                        /*TODO change this into prettier button*/
                        if (setting.editable) {
                            IconButton(onClick = {
                                goalViewModel.onEvent(UIEvent.EditGoal(goalItem))
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit"
                                )
                            }
                        }
                        IconButton(onClick = {
                            goalViewModel.onEvent(UIEvent.DeleteGoal(goalItem))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
                        /* TODO make goal editable  depending on preferences*/
                    }
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        ButtonDialogExample(goalViewModel, homeViewModel)

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


