package com.example.perpetual_calendar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import java.time.DayOfWeek
import java.time.LocalDate

class MainActivity : BaseActivity() {

    private fun adwent(year: Int): String{
        var date: LocalDate = LocalDate.of(year,12,25)
        val day: Int = date.dayOfWeek.value
        date = date.minusDays((day+21).toLong())
        return date.toString()
    }

    private fun setDates(year: Int){
        val textView: TextView = findViewById(R.id.dates)
        var wielkanocDate: LocalDate = wielkanoc(year)
        var wielkanoc: String = reformat(wielkanocDate.toString())
        var popielec: String = reformat(wielkanocDate.minusDays(46).toString())
        var bozeCialo: String = reformat(wielkanocDate.plusDays(60).toString())
        var adwent: String = reformat(adwent(year))
        textView.text = "$popielec\n$wielkanoc\n$bozeCialo\n$adwent"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val numberPicker: NumberPicker = findViewById(R.id.numberPicker)
        val sundaysButton: Button = findViewById(R.id.button1)
        val workingDaysButton: Button = findViewById(R.id.button2)
        var year: Int  = 2021;
        numberPicker.minValue = 1900
        numberPicker.maxValue = 2200
        numberPicker.value = year
        numberPicker.wrapSelectorWheel = false
        setDates(2021)
        numberPicker.setOnValueChangedListener(){ _, _, newVal ->
            year = newVal
            setDates(year)
        }
        sundaysButton.setOnClickListener{
            val intent = Intent(this, SundaysActivity::class.java)
            intent.putExtra("year",year)
            startActivity(intent)
        }
        workingDaysButton.setOnClickListener{
            val intent = Intent(this, WorkingDaysActivity::class.java)
            startActivity(intent)
        }
    }
}