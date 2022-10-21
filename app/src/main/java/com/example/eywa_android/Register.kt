package com.example.eywa_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val intentRegister = getIntent()

        val img_on_1 = findViewById<ImageView>(R.id.show_pass_btn)
        val img_off_1 = findViewById<ImageView>(R.id.hide_pass_btn)
        val img_on_2 = findViewById<ImageView>(R.id.show_pass_btn2)
        val img_off_2 = findViewById<ImageView>(R.id.hide_pass_btn2)
        val password_1 = findViewById<EditText>(R.id.edit_password)
        val password_2 = findViewById<EditText>(R.id.repeat_password)
        var btnLogin = findViewById<TextView>(R.id.btnLoginRegister)

        btnLogin.setOnClickListener(){
            val intentLogin = Intent(this, Login::class.java)
            startActivity(intentLogin)
            finish()
        }
        //Turn on password 1 edittext
        img_on_1.setOnClickListener {
            if (img_on_1.isVisible){
                img_on_1.visibility = View.INVISIBLE
                img_off_1.visibility = View.VISIBLE
                img_on_2.visibility = View.INVISIBLE
                img_off_2.visibility = View.VISIBLE
                password_1.setInputType(InputType.TYPE_CLASS_TEXT);
                password_2.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        }

        //Turn off password 1 edittext
        img_off_1.setOnClickListener {
            if (img_off_1.isVisible){
                img_on_1.visibility = View.VISIBLE
                img_off_1.visibility = View.INVISIBLE
                img_on_2.visibility = View.VISIBLE
                img_off_2.visibility = View.INVISIBLE
                password_1.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                password_2.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
        }

        //Turn on password 2 edittext
        img_on_2.setOnClickListener {
            if (img_on_2.isVisible){
                img_on_1.visibility = View.INVISIBLE
                img_off_1.visibility = View.VISIBLE
                img_on_2.visibility = View.INVISIBLE
                img_off_2.visibility = View.VISIBLE
                password_1.setInputType(InputType.TYPE_CLASS_TEXT);
                password_2.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        }

        //Turn off password 2 edittext
        img_off_2.setOnClickListener {
            if (img_off_2.isVisible){
                img_on_1.visibility = View.VISIBLE
                img_off_1.visibility = View.INVISIBLE
                img_on_2.visibility = View.VISIBLE
                img_off_2.visibility = View.INVISIBLE
                password_1.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                password_2.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)            }
        }
    }
}