package com.example.all_in_one_fitness.timer

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.TimePicker
import com.example.all_in_one_fitness.R
import com.example.all_in_one_fitness.timer.util.NotificationUtil
import com.example.all_in_one_fitness.timer.util.PrefUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.util.Calendar

class TimerFragment : Fragment() {

    companion object{
        var timerLenInt = 60
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

    private lateinit var settings: ImageButton
    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 0
    private var timerState = TimerState.Stopped

    private lateinit var timerString: TextView
    private lateinit var buttonStart: ImageButton
    private lateinit var buttonPause: ImageButton
    private lateinit var buttonStop: ImageButton
    private lateinit var progressCountdown: CircularProgressBar
    private lateinit var restore: Button
    private var secondsRemaining = 0L

    private var minutes: Int = 1
    private var seconds: Int = 0

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

        timer = object : CountDownTimer(secondsRemaining * 1000, 1000){
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
            }
        }

        if(isNewTimeSet) {
            onTimerFinished()
            isNewTimeSet = false
        }
        settings.setOnClickListener{
            val intent = Intent(requireContext(), TimerSettings::class.java)
            startActivity(intent)
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
        
        timerString.setOnClickListener{
            popTimePicker(requireView())
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
        progressCountdown.setProgressWithAnimation((timerLengthSeconds - secondsRemaining).toFloat())
        if((timerLengthSeconds - secondsRemaining).toInt() == 0){
            for(i in 1..3){
                vibratePhone()
                //set sound
            }
        }
    }

    private fun updateButtons(){
        when(timerState){
            TimerState.Running -> {
                buttonStart.isEnabled = false
                buttonStart.setBackgroundResource(R.drawable.baseline_play_arrow_off)

                buttonPause.isEnabled = true
                buttonPause.setBackgroundResource(R.drawable.baseline_pause)

                buttonStop.isEnabled = true
                buttonStop.setBackgroundResource(R.drawable.baseline_stop)

                settings.isEnabled = false
                settings.setBackgroundResource(R.drawable.baseline_settings_off)
            }
            TimerState.Stopped -> {
                buttonStart.isEnabled = true
                buttonStart.setBackgroundResource(R.drawable.baseline_play_arrow_24)

                buttonPause.isEnabled = false
                buttonPause.setBackgroundResource(R.drawable.baseline_pause_off)

                buttonStop.isEnabled = false
                buttonStop.setBackgroundResource(R.drawable.baseline_stop_off)

                settings.isEnabled = true
                settings.setBackgroundResource(R.drawable.baseline_settings_24)
            }
            TimerState.Paused -> {
                buttonStart.isEnabled = true
                buttonStart.setBackgroundResource(R.drawable.baseline_play_arrow_24)

                buttonPause.isEnabled = false
                buttonPause.setBackgroundResource(R.drawable.baseline_pause_off)

                buttonStop.isEnabled = true
                buttonStop.setBackgroundResource(R.drawable.baseline_stop)

                settings.isEnabled = false
                settings.setBackgroundResource(R.drawable.baseline_settings_off)
            }
        }
    }


    fun Fragment.vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }

    fun popTimePicker(view: View){
        val onTime = TimePickerDialog.OnTimeSetListener(){ timePicker: TimePicker,
                                              selectedMinute: Int,
                                              selectedSeconds: Int ->
            timerLenInt = selectedMinute * 60 + selectedSeconds
        }

        val timePicker = TimePickerDialog(
            requireContext(),
            onTime,
            minutes,
            seconds,
            true
        )

        timePicker.setTitle("Установите время")
        timePicker.show()
    }


}