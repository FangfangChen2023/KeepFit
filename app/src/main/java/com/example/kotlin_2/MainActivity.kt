package com.example.kotlin_2

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import com.example.kotlin_2.ui.theme.Kotlin_2Theme
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_2.screen.Goal.GoalViewModel
import com.example.kotlin_2.screen.Home.HomeViewModel
import com.example.kotlin_2.screen.Setting.SettingsScreen
import androidx.compose.material.Text
import com.example.kotlin_2.screen.Setting.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val PREFS_NAME = "currentSteps"
    val PREFS_DAY = "date"
    val PREFS_GOAL = "goal"

    val homeViewModel: HomeViewModel by viewModels()
    val goalViewModel: GoalViewModel by viewModels()
    val settingsViewModel: SettingsViewModel by viewModels()
    //val historyViewModel: HistoryViewModel by viewModels ()

    //val settingsViewModel: SettingsViewModel by viewModels()
    /*viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModal::class.java)*/


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSharedPreferences(PREFS_NAME, 0)
        getSharedPreferences(PREFS_DAY, 0)
        getSharedPreferences(PREFS_GOAL, 0)
        setContent {
            SettingsScreen(settingsViewModel)
            val navController = rememberNavController()
            /*Scaffold(
                    topBar = {
                        TopNavigationBar(
                            navController = navController,
                        )
                    }

            ) {
                TopNavGraph(navController = navController, homeViewModel = homeViewModel) //, settingsViewModel = settingsViewModel, goalViewModel = goalViewModel, historyViewModel = historyViewModel)
            }*/
            Kotlin_2Theme {
                // A surface container using the 'background' color from the theme
                //val context = this
                //create goal database, initialise with default goals
                //val db = DataBaseHandler(context)
                //TODO delete this before actually showing the app
                //db.clearDatabase()
                //val defaultGoal = GoalItem("default", 5000, true)
                //val testGoal = GoalItem("test", 3000, false)
                //db.insertGoal(testGoal)
                //db.insertGoal(defaultGoal)
            }
            //val navController = rememberNavController()
            val settingsRoute = ReplyTopLevelDestination(
                route = ReplyRoute.SETTINGS,
                selectedIcon = R.drawable.baseline_home_24,
                unselectedIcon = R.drawable.baseline_home_24,
                iconText = "Settings"
            )
            //val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "keepFit.db").build()
            //val goalDao = db.goalDao()
            //val historyDao = db.historyDao()
//            val goalViewModel = ViewModelProvider(this).get(GoalViewModel::class.java)
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("iWalk") },
                        actions = {
                            // RowScope here, so these icons will be placed horizontally
                            IconButton(onClick = { navController.navigate(settingsRoute.route) }) {
                                Icon(
                                    Icons.Filled.Settings,
                                    contentDescription = "Localized description",
                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomNavigationBar(
                        items = TOP_LEVEL_DESTINATIONS,
                        navController = navController,
                        onItemClick = {
                            navController.navigate(it.route)
                        }
                    )
                }
            ) {
                BottomNavGraph(
                    navController = navController,
                    homeViewModel = homeViewModel,
                    goalViewModel = goalViewModel,
                    settingsViewModel = settingsViewModel
                )
            }
        }
    }
}




