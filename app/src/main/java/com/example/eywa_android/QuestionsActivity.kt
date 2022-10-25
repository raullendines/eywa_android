package com.example.eywa_android

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
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
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        val answer1 = findViewById<Button>(R.id.answer1)
        val answer2 = findViewById<Button>(R.id.answer2)
        val answer3 = findViewById<Button>(R.id.answer3)
        val answer4 = findViewById<Button>(R.id.answer4)
        val question = findViewById<TextView>(R.id.txtQuestion)

        var contadorTiempo = getObject().contador/1000
        val timer = findViewById<TextView>(R.id.txtTimer)

        val selected: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.answer_button_selected, null)
        val unselected: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.answer_button, null)
        val animation = findViewById<LottieAnimationView>(R.id.animationWin)

        println("Texto"+answer1.text)
        //Answer 1
        answer1.setOnClickListener() {
            if (answer1.tag == "unselected") {
                answer1.tag = "selected"
                answer1.setTextColor(Color.parseColor("#FFFFFF"))
                answer1.background = selected

                if (answer1.text == "Answer 1"){
                    correct(animation, answer1, answer2, answer3, answer4, question)
                }
                else{
                    incorrect(animation, answer1, answer2, answer3, answer4, question)
                }
            } else {
                answer1.tag = "unselected"
                answer1.setTextColor(Color.parseColor("#000000"))
                answer1.background = unselected
            }
            disabled(answer1, answer2, answer3, answer4)
        }

        //Answer 2
        answer2.setOnClickListener() {
            if (answer2.tag == "unselected") {
                answer2.tag = "selected"
                answer2.setTextColor(Color.parseColor("#FFFFFF"))
                answer2.background = selected

                if (answer2.text == "Answer 1"){
                    correct(animation, answer1, answer2, answer3, answer4, question)
                }
                else{
                    incorrect(animation, answer1, answer2, answer3, answer4, question)
                }
            } else {
                answer2.tag = "unselected"
                answer2.setTextColor(Color.parseColor("#000000"))
                answer2.background = unselected
            }
            disabled(answer1, answer2, answer3, answer4)
        }


        //Answer 3
        answer3.setOnClickListener() {
            if (answer3.tag == "unselected") {
                answer3.tag = "selected"
                answer3.setTextColor(Color.parseColor("#FFFFFF"))
                answer3.background = selected

                if (answer3.text == "Answer 1"){
                    correct(animation, answer1, answer2, answer3, answer4, question)
                }
                else{
                    incorrect(animation, answer1, answer2, answer3, answer4, question)
                }
            } else {
                answer3.tag = "unselected"
                answer3.setTextColor(Color.parseColor("#000000"))
                answer3.background = unselected
            }
            disabled(answer1, answer2, answer3, answer4)
        }

        //Answer 4
        answer4.setOnClickListener() {
            if (answer4.tag == "unselected") {
                answer4.tag = "selected"
                answer4.setTextColor(Color.parseColor("#FFFFFF"))
                answer4.background = selected

                if (answer4.text == "Answer 1"){
                    correct(animation, answer1, answer2, answer3, answer4, question)
                }
                else{
                    incorrect(animation, answer1, answer2, answer3, answer4, question)
                }
            } else {
                answer4.tag = "unselected"
                answer4.setTextColor(Color.parseColor("#000000"))
                answer4.background = unselected
            }
            disabled(answer1, answer2, answer3, answer4)
        }

        contador_timer = object : CountDownTimer( getObject().contador.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timer.text = contadorTiempo.toString();
                contadorTiempo--;
            }

            override fun onFinish() {

                Handler().postDelayed({
                    incorrect(animation, answer1, answer2, answer3, answer4, question)
                }, 100)
            }
        }.start()
    }

    override fun onActionModeFinished(mode: ActionMode?) {
        super.onActionModeFinished(mode)
        contador_timer.cancel()
    }

    fun stopTimer(answer1: Button, answer2: Button, answer3: Button, answer4: Button, question: TextView){
        contador_timer.cancel()
    }

    fun newQuestion(answer1: Button, answer2: Button, answer3: Button, answer4: Button, question: TextView){

        contador_timer.start()
        getObject().contador = 10000
        question.text = "¿Quien es mejor?"
        answer1.text = "Respuesta 1"
        answer2.text = "Respuesta 2"
        answer3.text = "Respuesta 3"
        answer4.text = "Respuesta 4"

    }

    fun disabled(answer1: Button, answer2: Button, answer3: Button, answer4: Button){
        answer1.isEnabled = false
        answer2.isEnabled = false
        answer3.isEnabled = false
        answer4.isEnabled = false
    }

    fun enabled(answer1: Button, answer2: Button, answer3: Button, answer4: Button){
        answer1.isEnabled = true
        answer2.isEnabled = true
        answer3.isEnabled = true
        answer4.isEnabled = true
    }

    fun correct(animation: LottieAnimationView, answer1: Button, answer2: Button, answer3: Button, answer4: Button, question: TextView) {
        animation.setAnimation(R.raw.animation_correct)
        animation.playAnimation()

        val animacion = AnimationUtils.loadAnimation(this, R.anim.fade_out)


        object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                animation.startAnimation(animacion)
                Handler().postDelayed({
                    animation.visibility = View.INVISIBLE
                    stopTimer(answer1, answer2, answer3, answer4, question)
                }, 100)
            }
        }.start()
    }

    fun incorrect(animation: LottieAnimationView, answer1: Button, answer2: Button, answer3: Button, answer4: Button, question: TextView) {
        animation.setAnimation(R.raw.animation_incorrect)
        animation.playAnimation()

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

