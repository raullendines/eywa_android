package com.example.eywa_android.Quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eywa_android.ClassObject.Question
import com.example.eywa_android.R


class QuestionsActivity : AppCompatActivity() {

    object Questions {
        const val QUESTIONS = "QUESTIONS"
        const val CATEGORY = "CATEGORY"
        const val DIFFICULTY = "DIFFICULTY"
        const val SCORE = "SCORE"
    }

    interface getQuestionsList{
        fun getQuestionsListFun(questions: MutableList<Question>)
    }

    interface pauseFragment{
        fun onPauseFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        val intent = getIntent()
        val extras : Bundle? = intent.extras

        var questions : MutableList<Question> = extras?.getParcelableArrayList<Question>(Questions.QUESTIONS) as MutableList<Question>
        var myFragmentManager = this.supportFragmentManager.findFragmentByTag("myfragmentquiz")
        var myFragment = myFragmentManager!!.childFragmentManager.fragments[0]
        callFun(myFragment as getQuestionsList, questions)
    }


    fun callFun(someObject : getQuestionsList, questions : MutableList<Question>){
        someObject.getQuestionsListFun(questions)
    }

    fun finishActivity(){
        finish()
    }

    override fun onStop() {
        super.onStop()
        var myFragmentManager = this.supportFragmentManager.findFragmentByTag("myfragmentquiz")
        var myFragment = myFragmentManager!!.childFragmentManager.fragments[0]
        callQuizFragment(myFragment as pauseFragment)
    }

    fun callQuizFragment(fragment : pauseFragment){
        fragment.onPauseFragment()
    }
}

