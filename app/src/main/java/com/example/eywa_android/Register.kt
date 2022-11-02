package com.example.eywa_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
        val username = findViewById<EditText>(R.id.LblUsernameRegister)
        val password_1 = findViewById<EditText>(R.id.edit_password)
        val password_2 = findViewById<EditText>(R.id.repeat_password)
        var btnLogin = findViewById<TextView>(R.id.btnLoginRegister)
        var btnRegister = findViewById<Button>(R.id.BtnRegister)
        var users = FilesManager.getUsers(this)

        btnRegister.setOnClickListener() {
            var registerIntent = false
            if(username.text.isEmpty() || password_1.text.isEmpty() || password_2.text.isEmpty()){
                Toast.makeText(applicationContext, "Please fill all camps", Toast.LENGTH_SHORT).show()
            }else{
                var userExist = false
                for (user : User in users) {
                    if (user.username.equals(username.text.toString())){
                        Toast.makeText(applicationContext, "User already exist", Toast.LENGTH_SHORT).show()
                        userExist = true
                    }
                }
                if (!userExist){
                    if (password_1.text.toString().equals(password_2.text.toString())){
                        var salt : String = Bcrypt.gensalt()
                        var hashedPassword : String = Bcrypt.hashpw(password_1.text.toString(),salt)
                        var newUser : User = User(username.text.toString(),hashedPassword,"foto.png","male",18)
                        users.add(users.size-1, newUser)
                        FilesManager.saveUser(this,users)
                        Toast.makeText(applicationContext, "User registered", Toast.LENGTH_SHORT).show()
                        val intentLogin = Intent(this, Login::class.java)
                        startActivity(intentLogin)
                        finish()
                    }
                    else {
                        Toast.makeText(applicationContext, "Passwords are different", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

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