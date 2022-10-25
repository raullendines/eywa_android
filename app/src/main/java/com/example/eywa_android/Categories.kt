package com.example.eywa_android

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Categories : AppCompatActivity() {

    var buttonSelected : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)



        val buttons = arrayOf(
                findViewById<Button>(R.id.buttonAction),
                findViewById<Button>(R.id.buttonComedy),
                findViewById<Button>(R.id.buttonScienceFiction),
                findViewById<Button>(R.id.buttonHorror),
                findViewById<Button>(R.id.buttonAnimation),
                findViewById<Button>(R.id.buttonDrama),
        )

        for (button in buttons){
            button.setOnClickListener(){
                uncheckButton(buttons[buttonSelected])
                buttonStyle(button)
            }
        }

    }


    private fun uncheckButton(button : Button){
            button.setBackgroundResource(R.drawable.rounded_corners_white)
            button.setTextColor(Color.parseColor("#3d0066"))
    }


    private fun buttonStyle(button : Button){
        when (button.text){
            "ACTION" -> {
                button.setBackgroundResource(R.drawable.rounded_corners_red)
                buttonSelected = 0
            }
            "COMEDY" -> {
                button.setBackgroundResource(R.drawable.rounded_corners_yellow)
                buttonSelected = 1
            }
            "SCIENCE FICTION" -> {
                button.setBackgroundResource(R.drawable.rounded_corners_red)
                buttonSelected = 2
            }
            "HORROR" -> {
                button.setBackgroundResource(R.drawable.rounded_corners_yellow)
                buttonSelected = 3
            }
            "ANIMATION" -> {
                button.setBackgroundResource(R.drawable.rounded_corners_red)
                buttonSelected = 4
            }
            "DRAMA" -> {
                button.setBackgroundResource(R.drawable.rounded_corners_yellow)
                buttonSelected = 5
            }
        }
        button.setTextColor(Color.parseColor("#FFFFFF"))

    }
}