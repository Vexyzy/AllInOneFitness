package com.example.all_in_one_fitness

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.all_in_one_fitness.timer.TimerFragment

class Dialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val array = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
                "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
                "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",
                "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55",
                "56", "57", "58", "59", "60")
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Выберите время")
                .setItems(array
                ) { dialog, which ->
                    TimerFragment.timerLenInt =  array[which].toInt()
                    TimerFragment.isNewTimeSet = true
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
