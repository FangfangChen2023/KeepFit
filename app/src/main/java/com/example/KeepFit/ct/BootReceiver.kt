package com.example.KeepFit.ct

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


// TODO  It'll take a while to get to this function. If the foreground service starts in the MainActivity, it'll faster.
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, WalkReminderService::class.java)
        context.startForegroundService(serviceIntent)
    }
}