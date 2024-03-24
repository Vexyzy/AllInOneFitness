package com.example.all_in_one_fitness.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.all_in_one_fitness.timer.util.NotificationUtil
import com.example.all_in_one_fitness.timer.util.PrefUtil

class TimerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtil.showTimerExpired(context)

        PrefUtil.setTimerState(TimerFragment.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}