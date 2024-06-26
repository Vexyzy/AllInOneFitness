package com.example.all_in_one_fitness.timer.util

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.all_in_one_fitness.AppConstants
import com.example.all_in_one_fitness.R
import com.example.all_in_one_fitness.timer.TimerFragment
import com.example.all_in_one_fitness.timer.TimerNotificationActionReceiver
import java.text.SimpleDateFormat
import java.util.Date

class NotificationUtil {

    companion object{
        private const val CHANNEL_ID_TIME = "menu_timer"
        private const val CHANNEL_NAME_TIER= "Timer App Timer"
        private const val TIMER_ID = 0

        fun showTimerExpired(context: Context){
            val startIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            startIntent.action = AppConstants.ACTION_START
            val startPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                startIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIME, true)
            nBuilder
                .setContentTitle("Timer Expired!")
                .setContentText("Start again?")
                .setContentIntent(getPendingIntentWithStack(context, TimerFragment::class.java))
                .addAction(R.drawable.baseline_play, "Start", startPendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIME, CHANNEL_NAME_TIER, true)

            nManager.notify(TIMER_ID, nBuilder.build())
        }

        fun showTimerRunning(context: Context, wakeUpTime: Long){
            val stopIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            stopIntent.action = AppConstants.ACTION_STOP
            val stopPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                stopIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val pauseIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            pauseIntent.action = AppConstants.ACTION_PAUSE
            val pausePendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                pauseIntent,
                PendingIntent.FLAG_MUTABLE
            )

            val df = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIME, true)
            nBuilder
                .setContentTitle("Timer is Running!")
                .setContentText("End: ${df.format(Date(wakeUpTime))}")
                .setContentIntent(getPendingIntentWithStack(context, TimerFragment::class.java))
                .setOngoing(true)
                .addAction(R.drawable.baseline_stop, "Stop", stopPendingIntent)
                .addAction(R.drawable.baseline_pause, "Pause", pausePendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIME, CHANNEL_NAME_TIER, true)

            nManager.notify(TIMER_ID, nBuilder.build())
        }

        fun showTimerPaused(context: Context){
            val resumeIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            resumeIntent.action = AppConstants.ACTION_RESUME
            val resumePendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                resumeIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIME, true)
            nBuilder
                .setContentTitle("Timer is Paused!")
                .setContentText("Resume?")
                .setContentIntent(getPendingIntentWithStack(context, TimerFragment::class.java))
                .setOngoing(true)

                .addAction(R.drawable.baseline_play, "Resume", resumePendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIME, CHANNEL_NAME_TIER, true)

            nManager.notify(TIMER_ID, nBuilder.build())
        }

        private fun getBasicNotificationBuilder(
            context: Context,
            channelId: String,
            playSound: Boolean): NotificationCompat.Builder {

            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val nBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.baseline_timer_24)
                .setAutoCancel(true)
                .setDefaults(0)
            if (playSound) nBuilder.setSound(notificationSound)
            return nBuilder
        }

        private fun <T> getPendingIntentWithStack(context: Context, javaClass: Class<T>):
                PendingIntent{
            val resultIntent = Intent(context, javaClass)
            resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val stackBuilder = TaskStackBuilder.create(context)
            //stackBuilder.addParentStack(javaClass)
            stackBuilder.addNextIntent(resultIntent)

            return stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_MUTABLE)
        }

        @TargetApi(26)
        private fun NotificationManager.createNotificationChannel(
                                                channelId: String,
                                                channelName: String,
                                                playSound: Boolean
                                                                ){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelImportance = if (playSound) NotificationManager.IMPORTANCE_DEFAULT
                else NotificationManager.IMPORTANCE_LOW
                val nChannel = NotificationChannel(channelId, channelName, channelImportance)
                nChannel.enableLights(true)
                nChannel.lightColor = Color.BLUE
                this.createNotificationChannel(nChannel)
            }
        }

        fun hideTimerNotification(context: Context)
        {
            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.cancel(TIMER_ID)
        }
    }
}