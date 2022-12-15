package com.example.eywa_android.Quiz

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.eywa_android.ClassObject.Question
import com.example.eywa_android.ClassObject.User
import com.example.eywa_android.R



class QuestionsActivity : AppCompatActivity() {

    private val sharedViewModel : QuizSharedViewModel by viewModels()

    object Questions {
        const val QUESTIONS = "QUESTIONS"
        const val USER = "USER"
        const val MUTED = "MUTED"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        val intent = intent
        val extras : Bundle? = intent.extras

        val questions : MutableList<Question> = extras?.getParcelableArrayList<Question>(Questions.QUESTIONS) as MutableList<Question>
        val user : User = extras.getSerializable(Questions.USER) as User
        val muted : Boolean = extras.getBoolean(Questions.MUTED)

        sharedViewModel.muted = muted
        sharedViewModel.user = user
        sharedViewModel.initQuestions(questions)

    }


    fun finishActivity(){
        finish()
    }


}

