package com.example.kotlin_2.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.kotlin_2.HistoryListItem
import com.example.kotlin_2.R
import kotlinx.coroutines.*
import com.example.kotlin_2.data.model.HistoryItem
import com.example.kotlin_2.screen.History.HistoryViewModel
import com.example.kotlin_2.screen.Setting.SettingsViewModel

//@Preview
@Composable
@SuppressLint("UnrememberedMutableState")
fun HistoryScreen(
    historyViewModel: HistoryViewModel,
    settingsViewModel: SettingsViewModel
) {
    /*TopAppBar(
        title = { Text("iWalk") },
        actions = {
            // RowScope here, so these icons will be placed horizontally
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Settings, contentDescription = "Localized description")
            }
        }
    )*/
    //Spacer(Modifier.height(50.dp))

    var allHistory: List<HistoryItem> by remember { mutableStateOf(emptyList()) }

    historyViewModel.histories.observeForever {
        allHistory = it
    }

    // Setting
    var editableHistories by remember { mutableStateOf(false) }

    settingsViewModel.changeSettings.observeForever{
        editableHistories = it.getBoolean("editableHistories", false)
    }


    var openDialog = remember { mutableStateOf(false) }


    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Clear History") },
            text = {
                Text("Are you sure you want to clear all histories?")
            },
            confirmButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    historyViewModel.clearAllHistories()
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

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center

    ) {
        ExtendedFloatingActionButton(
            onClick = {
                openDialog.value = true
            },
            icon = { Icon(Icons.Default.Delete, null) },
            text = { Text(text = " Clear History") },
        )

    }



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, bottom = 60.dp, start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(items = allHistory) { index,
                                           historyItem ->
            HistoryListItem(historyItem = historyItem,
                onClick = { print("test") }
            )
            var editDialog = remember { mutableStateOf(false) }
            if (editableHistories) {
                Row() {
                    IconButton(onClick = {
                        editDialog.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit"
                        )
                    }
                    IconButton(onClick = {
                        historyViewModel.deleteHistory(historyItem)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            }
            val focusManager = LocalFocusManager.current
            var historycopy by remember {
                mutableStateOf(historyItem)
            }

            if (editDialog.value) {
                AlertDialog(
                    onDismissRequest = { editDialog.value = false },
                    title = { Text("Edit History") },
                    text = {
                        Column {
                            TextField(
                                value = historycopy.name,
                                onValueChange = {
                                    historycopy = historycopy.copy(name = it)
                                },
                                label = { Text("New History name")},
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
                                value = historycopy.steps.toString(),
                                onValueChange = { historycopy = historycopy.copy(steps = it.toInt()) },
                                label = { Text("New History target") },
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
                            historyViewModel.updateHistory(historycopy)
                            focusManager.clearFocus()
                            editDialog.value = false
                        }) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            editDialog.value = false
                        }) {
                            Text("Cancel")
                        }
                    }
                )
            }

        }
    }

}

