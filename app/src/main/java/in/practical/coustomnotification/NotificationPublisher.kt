package practical.coustomnotification

import `in`.practical.coustomnotification.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationPublisher : BroadcastReceiver() {

    companion object {

        var NOTIFICATION = "notification"
        var NOTIFICATION_ID = "notification-id"
    }

    override fun onReceive(context: Context, intent: Intent) {
        var uri: Uri? = null

     val data: String? =intent.getStringExtra("data")
        val notificationId: Int =intent.getIntExtra(NotificationPublisher.NOTIFICATION_ID,0)
        createNotification(context,notificationId)

        when(data){
            "normal"->{
                uri =
                    Uri.parse("android.resource://" + context.packageName.toString()
                            + "/" + R.raw.sneeze)
            }
            "alert"->{
                uri =
                    Uri.parse("android.resource://" + context.packageName.toString()
                            + "/" + R.raw.alter)
            }
            "basic"->{
                uri =
                    Uri.parse("android.resource://" + context.packageName.toString()
                            + "/" + R.raw.notification)
            }
        }

        if (uri != null) {
            setNotificationSound(context,uri)
        }
    }

    private fun setNotificationSound(context: Context ,uri: Uri) {
        val r = RingtoneManager.getRingtone(context.applicationContext, uri)
        r.play()    }


    private fun createNotification(context: Context, notificationId: Int) {
        var builder = NotificationCompat.Builder(context, "mainChannel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Notification")
            .setContentText("Hello This my Message Data So please we get sound ")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        createNotificationChannel(context)
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }


    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("mainChannel", "NotificationMain", importance).apply {
                description = "this is a main channel "
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}