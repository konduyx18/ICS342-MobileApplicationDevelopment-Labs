// The application's main activity is responsible for requesting
// the POST_NOTIFICATIONS permission from the user, showing a rationale dialog if needed,
// and displaying a message when the permission is granted.
// Resources:
//https://developer.android.com/develop/ui/views/notifications/build-notification

package com.ics342.labs

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

// Define a NotificationService class that extends Android Service
class NotificationService : Service() {
    // onBind method should be implemented for a Service,
    // but in this case, it returns null, as binding is not used
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    // onStartCommand is called when the service is started.
    // It's responsible for displaying the notification
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Create a notification using NotificationCompat
        // Builder, specifying the channel, title, text, icon, and priority
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("New Content Available!")
            .setContentText("Check out the latest updates in Lab 8.")
            .setSmallIcon(R.drawable.star)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        // Retrieve the NotificationManager system service, responsible for issuing notifications
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Notify the NotificationManager to display the notification, using the defined notification ID
        notificationManager.notify(NOTIFICATION_ID, notification)
        // Return the constant from the parent onStartCommand,
        // -dictates how the system should proceed with the service if it's killed
        return super.onStartCommand(intent, flags, startId)
    }
    // Define constants for the channel ID and notification ID within a companion object,
    // can be referenced statically
    companion object {
        const val CHANNEL_ID = "LAB_8_CHANNEL_ID"
        const val NOTIFICATION_ID =  1234
    }
}

