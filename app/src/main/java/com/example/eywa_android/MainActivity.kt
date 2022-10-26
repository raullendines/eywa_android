package com.example.eywa_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView


class MainActivity : AppCompatActivity() {



        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)



            var users : MutableList<User> = FilesManager.getUsers(this)
            var questionsCA : MutableList<Question> = FilesManager.getQuestionsCA(this)
            var questionsEN : MutableList<Question> = FilesManager.getQuestionsEN(this)
            var questionsES : MutableList<Question> = FilesManager.getQuestionsES(this)
            var characters : MutableList<Characters> = FilesManager.getCharacters(this)







        }








}