package com.example.all_in_one_fitness.timer.util

import android.content.Context
import android.preference.PreferenceManager
import com.example.all_in_one_fitness.timer.TimerFragment


class PrefUtil {
    companion object{

        fun getTimerLength(context: Context): Long{
            //placeholder
            return 210L
        }

        private const val PREVIOUS_TIMER_LENGTH_SECONDS_ID = "com.example.all_in_one_fitness.timer" +
                ".previous_timer_length"

        fun getPreviousTimerLengthSeconds(context: Context) : Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0)
        }

        fun setPreviousTimerLengthSeconds(seconds: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds)
            editor.apply()
        }

        private const val TIMER_STATE_ID = "com.example.all_in_one_fitness.timer.timer_state"

        fun getTimerState(context: Context): TimerFragment.TimerState{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ordinal = preferences.getInt(TIMER_STATE_ID, 0)
            return TimerFragment.TimerState.values()[ordinal]
        }

        fun setTimerState(state: TimerFragment.TimerState, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ordinal = state.ordinal
            editor.putInt(TIMER_STATE_ID, ordinal)
            editor.apply()
        }

        private const val SECONDS_REMAINING_ID = "com.example.all_in_one_fitness.timer" +
                ".seconds_remainig"

        fun getSecondRemaining(context: Context) : Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(SECONDS_REMAINING_ID, 0)
        }

        fun setSecondRemaining(seconds: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SECONDS_REMAINING_ID, seconds)
            editor.apply()
        }

        private const val ALARM_SET_TIME_ID = "com.example.all_in_one_fitness.timer" +
                ".backgrounded_time"

        fun getAlarmSetTime(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(ALARM_SET_TIME_ID, 0)
        }

        fun setAlarmSetTime(time: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(ALARM_SET_TIME_ID, time)
            editor.apply()
        }
    }
}