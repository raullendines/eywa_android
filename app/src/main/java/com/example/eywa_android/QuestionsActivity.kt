package com.example.eywa_android

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
import com.airbnb.lottie.LottieAnimationView
import org.w3c.dom.Text

class QuestionsActivity : AppCompatActivity() {

    private lateinit var contador_timer: CountDownTimer

    private fun getObject() = object {
        var contador = 10000
        val answer1 = findViewById<Button>(R.id.answer1)
        val answer2 = findViewById<Button>(R.id.answer2)
        val answer3 = findViewById<Button>(R.id.answer3)
        val answer4 = findViewById<Button>(R.id.answer4)
        val question = findViewById<TextView>(R.id.txtQuestion)
        val selected: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.answer_button_selected, null)
        val unselected: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.answer_button, null)
        val correct_button: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.answer_button_correct, null)
        val incorrect_button: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.answer_button_error, null)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        val questionsList = Constants.getQuestions()
        Log.i("Questions size:", "${questionsList.size}")

        val currentPosition = 1
        val question: Question? = questionsList[currentPosition - 1]
        getObject().question.text = question!!.question
        getObject().answer1.text = question!!.correct_answer
        Log.i("Incorrect:", "${question.incorrect_answers[1]}")
        getObject().answer2.text = question.incorrect_answers[0]
        getObject().answer3.text = question!!.incorrect_answers[1]
        //getObject().answer4.text = question!!.incorrect_answers[2]


        var contadorTiempo = getObject().contador/1000
        val timer = findViewById<TextView>(R.id.txtTimer)
        val animation = findViewById<LottieAnimationView>(R.id.animationWin)


        //Answer 1
        getObject().answer1.setOnClickListener() {
            if (getObject().answer1.tag == "unselected") {
                getObject().answer1.tag = "selected"
                getObject().answer1.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer1.background = getObject().selected

                if (getObject().answer1.text == "Answer 1"){
                    correct(animation)
                }
                else{
                    incorrect(animation)
                }
            } else {
                getObject().answer1.tag = "unselected"
                getObject().answer1.setTextColor(Color.parseColor("#000000"))
                getObject().answer1.background = getObject().unselected
            }
            disabled()
        }

        //Answer 2
        getObject().answer2.setOnClickListener() {
            if (getObject().answer2.tag == "unselected") {
                getObject().answer2.tag = "selected"
                getObject().answer2.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer2.background = getObject().selected

                if (getObject().answer2.text == "Answer 1"){
                    correct(animation)
                }
                else{
                    incorrect(animation)
                }
            } else {
                getObject().answer2.tag = "unselected"
                getObject().answer2.setTextColor(Color.parseColor("#000000"))
                getObject().answer2.background = getObject().unselected
            }
            disabled()
        }


        //Answer 3
        getObject().answer3.setOnClickListener() {
            if (getObject().answer3.tag == "unselected") {
                getObject().answer3.tag = "selected"
                getObject().answer3.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer3.background = getObject().selected

                if (getObject().answer3.text == "Answer 1"){
                    correct(animation)
                }
                else{
                    incorrect(animation)
                }
            } else {
                getObject().answer3.tag = "unselected"
                getObject().answer3.setTextColor(Color.parseColor("#000000"))
                getObject().answer3.background = getObject().unselected
            }
            disabled()
        }

        //Answer 4
        getObject().answer4.setOnClickListener() {
            if (getObject().answer4.tag == "unselected") {
                getObject().answer4.tag = "selected"
                getObject().answer4.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer4.background = getObject().selected

                if (getObject().answer4.text == "Answer 1"){
                    correct(animation)
                }
                else{
                    incorrect(animation)
                }
            } else {
                getObject().answer4.tag = "unselected"
                getObject().answer4.setTextColor(Color.parseColor("#000000"))
                getObject().answer4.background = getObject().unselected
            }
            disabled()
        }

        contador_timer = object : CountDownTimer( getObject().contador.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timer.text = contadorTiempo.toString();
                contadorTiempo--;
            }

            override fun onFinish() {

                Handler().postDelayed({
                    timer.text = "0"
                    incorrect(animation)
                }, 100)
            }
        }.start()
    }

    override fun onActionModeFinished(mode: ActionMode?) {
        super.onActionModeFinished(mode)
        contador_timer.cancel()
    }

    fun stopTimer(){
        contador_timer.cancel()
    }

    fun newQuestion(){

        getObject().contador = 10000
        contador_timer.start()

        getObject().question.text = "¿Quien es mejor?"
        getObject().answer1.text = "Respuesta 1"
        getObject().answer2.text = "Respuesta 2"
        getObject().answer3.text = "Respuesta 3"
        getObject().answer4.text = "Respuesta 4"

    }

    fun disabled(){
        getObject().answer1.isEnabled = false
        getObject().answer2.isEnabled = false
        getObject().answer3.isEnabled = false
        getObject().answer4.isEnabled = false
    }

    fun enabled(){
        getObject().answer1.isEnabled = true
        getObject().answer2.isEnabled = true
        getObject().answer3.isEnabled = true
        getObject().answer4.isEnabled = true
    }

    fun correct(animation: LottieAnimationView) {
        var correcto = true;
        animation.setAnimation(R.raw.animation_correct)
        animation.playAnimation()
        stopTimer()
        colorBtn(correcto)
        val animacion = AnimationUtils.loadAnimation(this, R.anim.fade_out)


        object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                animation.startAnimation(animacion)
                Handler().postDelayed({
                    animation.visibility = View.INVISIBLE
                }, 100)
            }
        }.start()
    }

    fun colorBtn(isCorrect: Boolean) {
        if (isCorrect) {
            if (getObject().answer1.tag.equals("selected")) {
                getObject().answer1.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer1.background = getObject().correct_button
            } else if (getObject().answer2.tag.equals("selected")) {
                getObject().answer2.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer2.background = getObject().correct_button
            } else if (getObject().answer3.tag.equals("selected")) {
                getObject().answer3.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer3.background = getObject().correct_button
            } else if (getObject().answer3.tag.equals("selected")) {
                getObject().answer3.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer3.background = getObject().correct_button
            }
        }
        else{
            if (getObject().answer1.tag.equals("selected")) {
                getObject().answer1.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer1.background = getObject().incorrect_button
            } else if (getObject().answer2.tag.equals("selected")) {
                getObject().answer2.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer2.background = getObject().incorrect_button
            } else if (getObject().answer3.tag.equals("selected")) {
                getObject().answer3.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer3.background = getObject().incorrect_button
            } else if (getObject().answer3.tag.equals("selected")) {
                getObject().answer3.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer3.background = getObject().incorrect_button
            }
        }
    }

    fun incorrect(animation: LottieAnimationView, ) {
        animation.setAnimation(R.raw.animation_incorrect)
        animation.playAnimation()
        stopTimer()

        var correcto = false;
        colorBtn(correcto)

        val animacion = AnimationUtils.loadAnimation(this, R.anim.fade_out)


        object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                animation.startAnimation(animacion)
                Handler().postDelayed({
                    animation.visibility = View.INVISIBLE
                }, 100)
            }
        }.start()
    }

}

