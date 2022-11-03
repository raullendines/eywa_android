package com.example.eywa_android

import android.app.Fragment
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.ActionMode
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextInt
import kotlin.random.Random



class QuestionsActivity : AppCompatActivity() {

    object Questions {
        const val QUESTIONS = "QUESTIONS"
    }

    interface getQuestionsList{
        fun getQuestionsListFun(questions: MutableList<Question>)
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

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)

    }

    fun callFun(someObject : getQuestionsList, questions : MutableList<Question>){
        someObject.getQuestionsListFun(questions)
    }
}

