package com.verindrarizya.teramovie.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.verindrarizya.teramovie.R

object NotificationHelper {

    const val NEW_MOVIE_DATA_NOTIFICATION_ID = 1
    const val NEW_MOVIE_DATA_CHANNEL_ID = "NEW_MOVIE_DATA_CHANNEL_ID"
    const val NEW_MOVIE_DATA_CHANNEL_NAME = "New Movie Data"
    const val NEW_MOVIE_DATA_CHANNEL_DESC = "New Movie Data Channel Description"

    fun createNotification(
        context: Context,
        notificationId: Int,
        channelId: String,
        channelName: String,
        channelDescription: String = "",
        timeOut: Long? = null
    ) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle("New Data Is Available")
            .setContentText("Come and see newest movie!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        if (timeOut != null) {
            builder.setTimeoutAfter(timeOut)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, builder.build())
    }

}