package com.example.KeepFit

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.KeepFit.screen.Goal.GoalViewModel
import com.example.KeepFit.screen.Home.HomeViewModel
import com.example.KeepFit.screen.Setting.SettingsScreen
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.KeepFit.ct.User
import com.example.KeepFit.ct.UserManager
import com.example.KeepFit.ct.UserType
import com.example.KeepFit.screen.History.HistoryViewModel
import com.example.KeepFit.screen.Setting.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val PERMISSIONS_REQUEST_CODE = 123

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val PREFS_NAME = "currentSteps"
    val PREFS_DAY = "date"
    val PREFS_GOAL = "goal"
    val PREFS_SETTING = "setting"

    val homeViewModel: HomeViewModel by viewModels()
    val goalViewModel: GoalViewModel by viewModels()
    val settingsViewModel: SettingsViewModel by viewModels()
    val historyViewModel: HistoryViewModel by viewModels ()

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

//    private lateinit var musicBrowser: MusicBrowser

//    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSharedPreferences(PREFS_NAME, 0)
        getSharedPreferences(PREFS_DAY, 0)
        getSharedPreferences(PREFS_GOAL, 0)
        getSharedPreferences(PREFS_SETTING, 0)
        historyViewModel.applyWorker(this.application)
        setContent {
            SettingsScreen(settingsViewModel)
            val navController = rememberNavController()

            val settingsRoute = ReplyTopLevelDestination(
                route = ReplyRoute.SETTINGS,
                selectedIcon = R.drawable.baseline_home_24,
                unselectedIcon = R.drawable.baseline_home_24,
                iconText = "Settings"
            )

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("KeepFit") },
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
//                    innerPadding ->
//                // Apply the padding globally to the whole BottomNavScreensController
//                Box(modifier = Modifier.padding(innerPadding)) {
//                    BottomNavScreensController(navController = navController)
//                }
                Box(modifier = Modifier.padding(it)){
                    BottomNavGraph(
                        navController = navController,
                        homeViewModel = homeViewModel,
                        goalViewModel = goalViewModel,
                        settingsViewModel = settingsViewModel,
                        historyViewModel = historyViewModel
                    )
                }

            }
        }
        // Notifications
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val speed = location.speed // Speed in meters/second

                // Create a User object (assuming you have a User class in your project)
                val user = User(type = UserType.UNKNOWN) // Replace with the appropriate constructor for your User class

                // Create an instance of UserManager and update the user type based on the walking speed
                val userManager = UserManager(user = user, context = this@MainActivity)
                userManager.checkUserType(walkingSpeed = speed.toDouble())

                // Use the speed value here, e.g., update the user type
                // val userManager = UserManager()
                // userManager.checkUserType(walkingSpeed = speed.toDouble())
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
        val permissions = arrayOf(
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_NOTIFICATION_POLICY
        )
        val checkPermission = hasPermissions(this, permissions)
        if (checkPermission.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, checkPermission, PERMISSIONS_REQUEST_CODE)
        }

        // Check and request location permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            startLocationUpdates()
        }

    }
    private fun startLocationUpdates() {
//        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, locationListener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//            musicBrowser.disconnect()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(locationListener)
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, locationListener)
        }
    }

}
private fun hasPermissions(mainActivity: MainActivity, permissions: Array<String>): Array<String> {
    if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED
    ) {
        // Permission is  granted
        permissions.toMutableList().removeAt(1)
    }
    if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.READ_CALENDAR)
        == PackageManager.PERMISSION_GRANTED
    ) {
        // Permission is  granted
        permissions.toMutableList().removeAt(0)
    }
//        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.POST_NOTIFICATIONS)
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//            // Permission is  granted
//            permissions.toMutableList().removeAt(2)
////            val permissionLauncher = rememberLauncherForActivityResult()
//            // Sets up permissions request launcher.
//        }

    return permissions
}





