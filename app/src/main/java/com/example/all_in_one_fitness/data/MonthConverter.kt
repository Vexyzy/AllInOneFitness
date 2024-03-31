package com.example.all_in_one_fitness.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Month

class MonthConverter {

    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        fun toMonth(str: String): Month {
            when(str){
                "JANUARY" -> return Month.JANUARY
                "FEBRUARY" -> return Month.FEBRUARY
                "MARCH" -> return Month.MARCH
                "APRIL" -> return Month.APRIL
                "MAY" -> return Month.MAY
                "JUNE" -> return Month.JUNE
                "JULY" -> return Month.JULY
                "AUGUST" -> return Month.AUGUST
                "SEPTEMBER" -> return Month.SEPTEMBER
                "OCTOBER" -> return Month.OCTOBER
                "NOVEMBER" -> return Month.NOVEMBER
                "DECEMBER" -> return Month.DECEMBER
            }
            return Month.MARCH
        }
    }
}