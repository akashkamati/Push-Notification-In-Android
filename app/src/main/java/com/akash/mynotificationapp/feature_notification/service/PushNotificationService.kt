package com.akash.mynotificationapp.feature_notification.service

import androidx.work.*
import com.akash.mynotificationapp.feature_notification.work.NotificationWorker
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        println(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (message.data.isNotEmpty()) {
            val title = message.data["title"]
            val body = message.data["body"]
            val deepLink = message.data["deepLink"] ?: ""
            val imageUrl = message.data["imageUrl"] ?: ""


            val inputWorkData = workDataOf(
                "title" to title,
                "body" to body,
                "deepLink" to deepLink,
                "imageUrl" to imageUrl
            )

            val notificationRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .setInputData(inputWorkData)
                .build()

            WorkManager.getInstance(applicationContext)
                .enqueue(notificationRequest)

        }
    }
}