package com.example.eywa_android

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intentLogin = getIntent()

        var img_on = findViewById<ImageView>(R.id.btnShow)
        var img_off = findViewById<ImageView>(R.id.btnHide)
        var password = findViewById<EditText>(R.id.textPassword)
        var btnLogin = findViewById<Button>(R.id.btnLogin)
        var btnRegister = findViewById<TextView>(R.id.btnRegisterLogin)

        var users = FilesManager.getUsers(this)

        btnRegister.setOnClickListener(){
            val intentRegister = Intent(this, Register::class.java)
            startActivity(intentRegister)
            finish()
        }
        
        btnLogin.setOnClickListener(){
            var username = findViewById<EditText>(R.id.textUsername)
            var password = findViewById<EditText>(R.id.textPassword)

            //Omplir amb les dades per contrastar amb el JSON
            if (userExist(username.text.toString(),password.text.toString(),users)){
                println("Correcte")
            }
            else {
                println("Incorrecte")
            }

        }
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
    fun userExist(user : String, password : String, users : MutableList<User>): Boolean {

        var existUser : Boolean = false
        var index : Int = 0
        do {
            if (users[index].username == user && users[index].password == password)
            {
                existUser = true
            }
            index++
        }while(!existUser && index < users.size)
        return existUser
    }
}