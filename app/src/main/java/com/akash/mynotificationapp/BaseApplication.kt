package com.akash.mynotificationapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.akash.mynotificationapp.util.Constants

class BaseApplication :Application(){


    override fun onCreate() {
        super.onCreate()
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(notificationManager)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          val channel = NotificationChannel(
                Constants.PUSH_NOTIFICATION_CHANNEL_ID,
                Constants.PUSH_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
            }
          notificationManager.createNotificationChannel(channel)

      } else {
            return
        }
    }
}