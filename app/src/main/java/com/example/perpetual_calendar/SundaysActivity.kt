package com.example.perpetual_calendar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.time.DayOfWeek
import java.time.LocalDate

class SundaysActivity : BaseActivity() {

    private fun sundays(year: Int): String{
        val textView: TextView = findViewById(R.id.sundayTitle)
        textView.text = "NIEDZIELE HANDLOWE W ROKU $year\n"
        var result = ""
        var temp = ""
        var date: LocalDate = LocalDate.of(year,1,31)
        var count: Int = 0
        while(count<7){
            var weekDay: DayOfWeek = date.dayOfWeek
            if(weekDay.toString() == "SUNDAY"){
                count++
                if (count!=6)
                    result = result + reformat(date.toString()) + "\n"
                when(count){
                    1 -> date = wielkanoc(year).minusDays(1)
                    2 -> date = LocalDate.of(year,4,30)
                    3 -> date = LocalDate.of(year,6,30)
                    4 -> date = LocalDate.of(year,8,31)
                    5 -> date = LocalDate.of(year,12,24)
                    6 -> {
                        temp = reformat(date.toString())
                        date = date.minusDays(1)
                    }
                }
            }
            else
                date = date.minusDays(1)
        }
        return result+temp
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sundays)
        val year: Int = intent.getIntExtra("year",2021)
        val textView: TextView = findViewById(R.id.sundays)
        val backButton: Button = findViewById(R.id.buttonBack2)

        if (year<2020)
            textView.text = "W "+year.toString()+" nie obowiązywały przepisy o niedzielach handlowych"
        else
            textView.text = sundays(year)

        backButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}