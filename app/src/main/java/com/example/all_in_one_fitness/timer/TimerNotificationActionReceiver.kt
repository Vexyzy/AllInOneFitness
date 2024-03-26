package com.example.all_in_one_fitness.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.fragment.app.DialogFragment
import com.example.all_in_one_fitness.AppConstants
import com.example.all_in_one_fitness.timer.util.NotificationUtil
import com.example.all_in_one_fitness.timer.util.PrefUtil
import java.sql.Time

class TimerNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action){
            AppConstants.ACTION_STOP -> {
                TimerFragment.removeAlarm(context)
                PrefUtil.setTimerState(TimerFragment.TimerState.Stopped, context)
                NotificationUtil.showTimerExpired(context)
                NotificationUtil.hideTimerNotification(context)
            }
            AppConstants.ACTION_PAUSE -> {
                var secondsRemaining = PrefUtil.getSecondRemaining(context)
                val alarmSetTime = PrefUtil.getAlarmSetTime(context)
                val nowSeconds = TimerFragment.nowSeconds
                secondsRemaining -= nowSeconds - alarmSetTime
                PrefUtil.setSecondRemaining(secondsRemaining, context)
                TimerFragment.removeAlarm(context)
                PrefUtil.setTimerState(TimerFragment.TimerState.Paused, context)
                NotificationUtil.showTimerPaused(context)
            }
            AppConstants.ACTION_RESUME -> {
                var secondsRemaining = PrefUtil.getSecondRemaining(context)
                val wakeUpTime = TimerFragment.setAlarm(context, TimerFragment.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(TimerFragment.TimerState.Running, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
            AppConstants.ACTION_START ->{
                val minutesRemaining = PrefUtil.getTimerLength(context)
                val secondsRemaining = (minutesRemaining * 60L)
                val wakeUpTime = TimerFragment.setAlarm(context, TimerFragment.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(TimerFragment.TimerState.Running, context)
                PrefUtil.setSecondRemaining(secondsRemaining, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
        }
    }
}