package com.akash.mynotificationapp.feature_notification.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters


class NotificationWorker(
     context: Context,
     workerParameters: WorkerParameters
) : CoroutineWorker(context,workerParameters) {


    override suspend fun doWork(): Result {

        val title = inputData.getString("title")
        val body = inputData.getString("body")
        val imageUrl = inputData.getString("imageUrl")
        val link = inputData.getString("deepLink")

        sendNotification(title!!, body!!, imageUrl!!, link!!,applicationContext)
        return Result.success()
    }

}

