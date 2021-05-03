package com.example.perpetual_calendar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.DayOfWeek

class WorkingDaysActivity : BaseActivity() {

    private fun isHoliday(date: LocalDate): Boolean{
        val month = date.monthValue
        val day = date.dayOfMonth
        val wielkanoc: LocalDate = wielkanoc(date.year).plusDays(1)
        val bozeCialo: LocalDate = wielkanoc.plusDays(59)
        when(month){
            1 -> {
                if(day == 1 || day == 6){
                    return true
                }
            }
            5 -> {
                if(day == 1 || day == 3)
                    return true
            }
            8 -> {
                if(day == 15)
                    return true
            }
            11 -> {
                if(day == 1 || day == 11)
                    return true
            }
            12 -> {
                if(day == 25 || day == 26)
                    return true
            }
        }
        if(wielkanoc.dayOfMonth == day && wielkanoc.monthValue == month)
            return true
        if(bozeCialo.dayOfMonth == day && bozeCialo.monthValue == month)
            return true
        return false
    }

    private fun countDays(startDate: LocalDate, endDate: LocalDate) {
        val textView: TextView = findViewById(R.id.days_numbers)
        val textView2: TextView = findViewById(R.id.days_names)
        val textView3: TextView = findViewById(R.id.message)
        var tempStart: LocalDate = startDate
        var holidays: Int = 0
        val all: Int = ChronoUnit.DAYS.between(startDate,endDate).toInt()
        if (all<0){
            textView.visibility = TextView.INVISIBLE
            textView2.visibility = TextView.INVISIBLE
            textView3.visibility = TextView.VISIBLE
        }
        else {
            textView.visibility = TextView.VISIBLE
            textView2.visibility = TextView.VISIBLE
            textView3.visibility = TextView.INVISIBLE
            while(ChronoUnit.DAYS.between(tempStart,endDate)>0){
                var weekDay: DayOfWeek = tempStart.dayOfWeek
                if (weekDay.toString()=="SUNDAY" || weekDay.toString()=="SATURDAY" || isHoliday(tempStart))
                    holidays++
                tempStart = tempStart.plusDays(1)
            }
            val working = all - holidays
            textView.text = "$all\n$working"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_working_days)

        val startPicker: DatePicker = findViewById(R.id.startPicker)
        val endPicker: DatePicker = findViewById(R.id.endPicker)
        val backButton: Button = findViewById(R.id.buttonBack)
        val restartButton: Button = findViewById(R.id.restart)
        var startDate: LocalDate = LocalDate.now()
        var endDate: LocalDate = LocalDate.now()
        countDays(startDate, endDate)

        startPicker.setOnDateChangedListener(){ _, year, month, day ->
            startDate = LocalDate.of(year,month+1,day)
            countDays(startDate, endDate)
        }

        endPicker.setOnDateChangedListener(){ _, year, month, day ->
            endDate = LocalDate.of(year,month+1,day)
            countDays(startDate, endDate)
        }

        restartButton.setOnClickListener{
            startPicker.updateDate(LocalDate.now().year, LocalDate.now().monthValue-1,LocalDate.now().dayOfMonth)
            endPicker.updateDate(LocalDate.now().year, LocalDate.now().monthValue-1,LocalDate.now().dayOfMonth)
            startDate= LocalDate.now()
            endDate = LocalDate.now()
        }

        backButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}