package com.example.all_in_one_fitness.timer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.all_in_one_fitness.Dialog
import com.example.all_in_one_fitness.MainActivity
import com.example.all_in_one_fitness.R
import com.example.all_in_one_fitness.fragment.StepsFragment
import com.example.all_in_one_fitness.timer.util.NotificationUtil
import com.example.all_in_one_fitness.timer.util.PrefUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import org.w3c.dom.Text
import java.sql.Time
import java.util.Calendar

class TimerFragment : Fragment() {

    companion object{
        var timerLenInt = 1
        var isNewTimeSet = false;
        fun setAlarm(context: Context, nowSeconds: Long, secondsRemaining: Long): Long{
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            PrefUtil.setAlarmSetTime(nowSeconds, context)
            return wakeUpTime
        }

        fun removeAlarm(context: Context){
            val intent = Intent(context, TimerReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            PrefUtil.setAlarmSetTime(0, context)
        }
        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000
    }

    enum class TimerState{
        Stopped, Paused, Running
    }
    private lateinit var settings: Button
    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 0
    private var timerState = TimerState.Stopped

    private lateinit var timerString: TextView
    private lateinit var buttonStart: FloatingActionButton
    private lateinit var buttonPause: FloatingActionButton
    private lateinit var buttonStop: FloatingActionButton
    private lateinit var progressCountdown: CircularProgressBar
    private lateinit var restore: Button
    private var secondsRemaining = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonStart = requireView().findViewById<FloatingActionButton>(R.id.button_start)
        buttonPause = requireView().findViewById<FloatingActionButton>(R.id.button_pause)
        buttonStop = requireView().findViewById<FloatingActionButton>(R.id.button_stop)
        timerString = requireView().findViewById(R.id.timer)
        progressCountdown = requireView().findViewById(R.id.progress_circular)
        settings = requireView().findViewById(R.id.settings)
        restore = requireView().findViewById(R.id.restore)

        settings.setOnClickListener{
            val myDialogFragment = Dialog()
            val manager = requireFragmentManager()
            myDialogFragment.show(manager, "myDialog")
            onTimerFinished()
        }

        timer = object : CountDownTimer(secondsRemaining * 1000, 1000){
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
            }
        }
        progressCountdown.progress = 0f
        progressCountdown.progressMax = timerLengthSeconds.toFloat()

        buttonStart.setOnClickListener {v ->
            startTimer()
            timerState = TimerState.Running
            updateButtons()
        }

        buttonPause.setOnClickListener {v ->
            timer.cancel()
            timerState = TimerState.Paused
            updateButtons()
        }

        buttonStop.setOnClickListener {v ->
            timer.cancel()
            onTimerFinished()
        }

        restore.setOnClickListener{
            onTimerFinished()
        }
    }

    override fun onResume(){
        super.onResume()
        setNewTimerLength()
        initTimer()

        removeAlarm(requireContext())
        NotificationUtil.hideTimerNotification(requireContext())
    }

    override fun onPause(){
        super.onPause()

        if(timerState == TimerState.Running){
            timer.cancel()
            val wakeUpTime = setAlarm(requireContext(), nowSeconds, secondsRemaining)
            NotificationUtil.showTimerRunning(requireContext(), wakeUpTime)
        }
        else if(timerState == TimerState.Paused) {
                NotificationUtil.showTimerPaused(requireContext())
            }
        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, requireContext())
        PrefUtil.setSecondRemaining(secondsRemaining, requireContext())
        PrefUtil.setTimerState(timerState, requireContext())
    }

    private fun initTimer(){
        timerState = PrefUtil.getTimerState(requireContext())

        if(timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLenght()

        secondsRemaining = if (timerState == TimerState.Running || timerState == TimerState.Paused)
            PrefUtil.getSecondRemaining(requireContext())
        else
            timerLengthSeconds

        val alarmSetTime = PrefUtil.getAlarmSetTime(requireContext())
        if(alarmSetTime > 0){
            secondsRemaining -= nowSeconds - alarmSetTime
        }

        if(secondsRemaining <= 0){
            onTimerFinished()
        }
        if(timerState == TimerState.Running)
            startTimer()

        updateButtons()
        updateCountdownUI()
    }

    private fun onTimerFinished(){
        timerState = TimerState.Stopped

        setNewTimerLength()

        progressCountdown.progress = 0f

        PrefUtil.setSecondRemaining(timerLengthSeconds, requireContext())
        secondsRemaining = timerLengthSeconds

        updateButtons()
        updateCountdownUI()
    }

    private fun startTimer(){
        timerState = TimerState.Running

        timer = object : CountDownTimer(secondsRemaining * 1000, 1000){
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                updateCountdownUI()
            }
        }.start()
    }

    private fun setNewTimerLength(){
        val count = PrefUtil.getTimerLength(requireContext())/60L
        val seconds = count % 60
        val minute = count / 60
        timerString.text = ("$minute:$seconds")
        val lengthInSeconds = PrefUtil.getTimerLength(requireContext())
        timerLengthSeconds = (lengthInSeconds)
        progressCountdown.progressMax = timerLengthSeconds.toFloat()
    }

    private fun setPreviousTimerLenght(){
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(requireContext())
        progressCountdown.progressMax = timerLengthSeconds.toFloat()
    }

    private fun updateCountdownUI(){
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        timerString.text = "$minutesUntilFinished:${
            if (secondsStr.length == 2) secondsStr
            else "0$secondsStr"
        }"
        progressCountdown.progress = (timerLengthSeconds - secondsRemaining).toFloat()
    }

    private fun updateButtons(){
        when(timerState){
            TimerState.Running -> {
                buttonStart.isEnabled = false
                buttonPause.isEnabled = true
                buttonStop.isEnabled = true
                settings.isEnabled = false
                restore.isEnabled = false
            }
            TimerState.Stopped -> {
                buttonStart.isEnabled = true
                buttonPause.isEnabled = false
                buttonStop.isEnabled = false
                settings.isEnabled = true
                restore.isEnabled = true
            }
            TimerState.Paused -> {
                buttonStart.isEnabled = true
                buttonPause.isEnabled = false
                buttonStop.isEnabled = true
                settings.isEnabled = false
                restore.isEnabled = false
            }
        }
    }
}