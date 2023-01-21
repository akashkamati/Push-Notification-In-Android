package com.akash.mynotificationapp.feature_notification.work

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.akash.mynotificationapp.MainActivity
import com.akash.mynotificationapp.R
import com.akash.mynotificationapp.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import kotlin.random.Random


suspend fun sendNotification(
    title: String,
    body: String,
    imageUrl: String,
    deepLink: String,
    context: Context,
) {


    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val taskDetailIntent = Intent(
        Intent.ACTION_VIEW,
        deepLink.toUri(),
        context,
        MainActivity::class.java
    )


    val imageBitmap  = if (imageUrl.isNotBlank()) getBitmapFromUrl(imageUrl) else null

    val pendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
        addNextIntentWithParentStack(taskDetailIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE )

        } else {
            getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)

        }

    }


    val notificationBuilder = NotificationCompat.Builder(context, Constants.PUSH_NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.notification_icon)
        .setContentTitle(title)
        .setContentText(body)
        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)

    if (imageBitmap != null){
        notificationBuilder.setLargeIcon(imageBitmap)
    }

    notificationManager.notify(Random.nextInt(), notificationBuilder.build())
}

private suspend fun getBitmapFromUrl(imageUrl:String) : Bitmap?{

    return withContext(Dispatchers.IO) {
        try {
            val url = URL(imageUrl)
            BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: Exception) {
            null
        }

    }


}




