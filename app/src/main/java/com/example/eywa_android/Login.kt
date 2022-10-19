package com.example.eywa_android

import android.R.attr
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val img_on = findViewById<ImageView>(R.id.show_pass_btn)
        val img_off = findViewById<ImageView>(R.id.hide_pass_btn)
        val password = findViewById<EditText>(R.id.edit_password)

        //Turn on password
        img_on.setOnClickListener {
            if (img_on.isVisible){
                img_on.visibility =View.INVISIBLE
                img_off.visibility =View.VISIBLE
                password.setInputType(InputType.TYPE_CLASS_TEXT);
                println("on")
            }
        }

        //Turn off password
        img_off.setOnClickListener {
            if (img_off.isVisible){
                img_on.visibility =View.VISIBLE
                img_off.visibility =View.INVISIBLE
                password.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                println("off")
            }
        }


    }
}