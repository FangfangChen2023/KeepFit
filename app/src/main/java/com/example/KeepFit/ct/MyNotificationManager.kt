package com.example.KeepFit.ct
//TODO this class is called from the triggers and displays a new notification with the given message

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.KeepFit.R

class MyNotificationManager(private val context: Context) {
    private lateinit var notificationManager: NotificationManager
    companion object {
        const val CHANNEL_ID = "take_a_walk_channel"
        const val NOTIFICATION_ID = 1
    }

    init {
        createNotificationChannel()
    }

    @SuppressLint("ServiceCast")
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Take a Walk"
            val descriptionText = "Channel for walk reminder notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

             notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    fun sendTakeAWalkNotification(message: String) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            return
        }
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_walk_notification_foreground)
            .setContentTitle("Take a Walk")
            .setContentText(message)
//            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)

//        with(NotificationManagerCompat.from(context)) {
//            notify(NOTIFICATION_ID, builder.build())
//        }
//        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    fun sendRockMusicNotification() {

    }

    fun sendServiceNotification(): Notification {

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Service is running in the foreground")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

    }
}