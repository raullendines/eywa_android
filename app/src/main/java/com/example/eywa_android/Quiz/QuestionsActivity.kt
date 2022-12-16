package com.example.eywa_android.Quiz

import android.media.MediaPlayer
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

    override fun onResume() {
        super.onResume()

        categoryMusic()
    }


    fun finishActivity(){
        sharedViewModel.mediaPlayerQuiz.release()
        finish()
    }

    fun categoryMusic(){
        println(sharedViewModel.category)
        when (sharedViewModel.category) {
            "action" -> {
                sharedViewModel.mediaPlayerQuiz = MediaPlayer.create(this, R.raw.black_panther)
            }
            "drama" -> {
                sharedViewModel.mediaPlayerQuiz = MediaPlayer.create(this, R.raw.el_padrino)
            }
            "horror" -> {
                sharedViewModel.mediaPlayerQuiz = MediaPlayer.create(this, R.raw.el_exorcista)
            }
            "science fiction" -> {
                sharedViewModel.mediaPlayerQuiz = MediaPlayer.create(this, R.raw.star_wars)
            }
            "comedy" -> {
                sharedViewModel.mediaPlayerQuiz = MediaPlayer.create(this, R.raw.comedia)
            }
            "animation" -> {
                sharedViewModel.mediaPlayerQuiz = MediaPlayer.create(this, R.raw.rey_leon)
            }

        }

        if(!sharedViewModel.muted){
            sharedViewModel.mediaPlayerQuiz.isLooping = true
            sharedViewModel.mediaPlayerQuiz.start()
        }
    }
}

