package com.example.perpetual_calendar
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate

public abstract class BaseActivity : AppCompatActivity(){

    fun wielkanoc(year: Int): LocalDate {
        val a: Int = year % 19
        val b: Int = Math.floorDiv(year,100)
        val c: Int = year % 100
        val d: Int = Math.floorDiv(b,4)
        val e: Int = b % 4
        val f: Int = Math.floorDiv(b+8,25)
        val g: Int = Math.floorDiv(b-f+1,3)
        val h: Int = (19*a+b-d-g+15)%30
        val i: Int = Math.floorDiv(c,4)
        val k: Int = c % 4
        val l: Int = (32+2*e+2*i-h-k)%7
        val m: Int = Math.floorDiv(a+11*h+22*l,451)
        val p: Int = (h+l-7*m+114)%31
        val day : Int = p+1
        val month: Int = Math.floorDiv(h+l-7*m+114,31)
        return LocalDate.of(year,month,day)
    }

    fun reformat(date: String): String{
        val tab: List<String> = date.split("-")
        return tab[2]+"."+tab[1]+"."+tab[0]
    }
}