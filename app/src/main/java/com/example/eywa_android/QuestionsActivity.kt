package com.example.eywa_android

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import kotlin.math.log

class QuestionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        val answer1 = findViewById<Button>(R.id.answer1)
        val answer2 = findViewById<Button>(R.id.answer2)
        val answer3 = findViewById<Button>(R.id.answer3)
        val answer4 = findViewById<Button>(R.id.answer4)
        val selected: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.answer_button_selected, null)
        val unselected: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.answer_button, null)
        val animation = findViewById<LottieAnimationView>(R.id.animationWin)
        val timer = findViewById<TextView>(R.id.txtTimer)


        //Answer 1
        answer1.setOnClickListener(){
            if(answer1.tag =="unselected"){
                answer1.tag = "selected"
                answer1.setTextColor(Color.parseColor("#FFFFFF"))
                answer1.background = selected

                animation.setAnimation(R.raw.animation_incorrect)
                animation.playAnimation()
            }
            else{
                answer1.tag = "unselected"
                answer1.setTextColor(Color.parseColor("#000000"))
                answer1.background = unselected
            }
        }

        //Answer 2
        answer2.setOnClickListener(){
            if(answer2.tag =="unselected"){
                answer2.tag = "selected"
                answer2.setTextColor(Color.parseColor("#FFFFFF"))
                answer2.background = selected

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
                        }, 100)
                    }
                }.start()





            }
            else{
                answer2.tag = "unselected"
                answer2.setTextColor(Color.parseColor("#000000"))
                answer2.background = unselected
            }
        }

        
        //Answer 3
        answer3.setOnClickListener(){
            if(answer3.tag =="unselected"){
                answer3.tag = "selected"
                answer3.setTextColor(Color.parseColor("#FFFFFF"))
                answer3.background = selected
            }
            else{
                answer3.tag = "unselected"
                answer3.setTextColor(Color.parseColor("#000000"))
                answer3.background = unselected
            }
        }

        //Answer 4
        answer4.setOnClickListener(){
            if(answer4.tag =="unselected"){
                answer4.tag = "selected"
                answer4.setTextColor(Color.parseColor("#FFFFFF"))
                answer4.background = selected
            }
            else{
                answer4.tag = "unselected"
                answer4.setTextColor(Color.parseColor("#000000"))
                answer4.background = unselected
            }
        }


    }
}