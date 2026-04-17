package com.boxinfotech.manager.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.boxinfotech.manager.R

const val CHANNEL_ID_ASSIGN = "assignments"

fun createAssignmentChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            CHANNEL_ID_ASSIGN, "Project Assignments",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val nm = context.getSystemService(NotificationManager::class.java)
        nm.createNotificationChannel(channel)
    }
}

fun notifyAssigned(context: Context, projectName: String, memberName: String) {
    val notification = NotificationCompat.Builder(context, CHANNEL_ID_ASSIGN)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Project Assigned")
        .setContentText("Assigned \"" + projectName + "\" to " + memberName)
        .setAutoCancel(true)
        .build()
    NotificationManagerCompat.from(context).notify((System.currentTimeMillis()%100000).toInt(), notification)
}