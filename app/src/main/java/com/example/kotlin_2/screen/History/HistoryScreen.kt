package com.example.kotlin_2.screen

//import DataBaseHandler
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlin_2.GoalListItem
//import com.example.kotlin_2.HistoryListItem
import com.example.kotlin_2.data.model.GoalItem
import kotlinx.coroutines.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import com.example.kotlin_2.screen.History.HistoryViewModel
import com.example.kotlin_2.screen.Setting.SettingsViewModel

//@Preview
@Composable
@SuppressLint("UnrememberedMutableState")
fun HistoryScreen(){
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

    /*Column(
        modifier = Modifier
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(75.dp))

        val context = LocalContext.current
        val db by remember { mutableStateOf(DataBaseHandler(context)) }
        var allHistory by remember { mutableStateOf(db.readHistory()) }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(items = allHistory) { index,
                                             historyItem ->
                Log.d("history", index.toString())
                HistoryListItem(historyItem = historyItem,
                    onClick = {
                        print("test")
                    }
                )

            }
        }

    }*/
/*
    Column (Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomBarChart()
//        val historyRepository = HistoryRepository()
//        val allHistory = historyRepository.getAllHistory()
//
//        LazyColumn(
//            contentPadding = PaddingValues(all = 12.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ){
//            itemsIndexed(items = allHistory){ index,
//                historyItem ->
//                Log.d("history", index.toString())
//                HistoryListItem(historyItem = historyItem)
//            }
//        }

    }*/
}

//val scope = CoroutineScope(Job() + Dispatchers.Main)
//fun exampleMethod() {
//    scope.launch {
//        CustomBarChart()
//    }
//
//fun cleanUp() {
//    // Cancel the scope to cancel ongoing coroutines work
//    scope.cancel()
//}
