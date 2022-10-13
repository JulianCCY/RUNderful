package com.example.running_app.views.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.running_app.CHANNEL_ID
import com.example.running_app.R
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class NotificationService: Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val sdf = SimpleDateFormat("HH:mm")
        val current = mutableStateOf("")

        val pendingIntent = PendingIntent.getActivity(this, 0 , intent, 0)

        val notify1 = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Settings")
            .setContentText("Notification setting has been turned on")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notify2 = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Good morning!")
            .setContentText("It's time to go for some exercises!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this).notify(123,notify1)

        // returns the status
        // of the program
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

}