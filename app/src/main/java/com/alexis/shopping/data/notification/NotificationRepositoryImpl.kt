package com.alexis.shopping.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.alexis.shopping.R
import com.alexis.shopping.domain.repository.INotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val context: Context
) : INotificationRepository {

    private val channelId = "local_notification_channel"
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotification()
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notificaciones Locales"
            val descriptionText = "Canal para notificaciones de la aplicación"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun sendNotification(message: String, title: String) {
        val notificationId = System.currentTimeMillis().toInt()

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        notificationManager.notify(notificationId, builder.build())
    }
}