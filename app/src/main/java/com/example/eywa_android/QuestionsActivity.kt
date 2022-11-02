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
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextInt
import kotlin.random.Random

class QuestionsActivity : AppCompatActivity() {

    private lateinit var contador_timer: CountDownTimer
    var currentPosition = 0
    var countQuestions = 0
    var correctAnswers = 0
    var incorrectAnswers = 0
    var correcto = true
    var correct_answer = true
    var possibleAnswers = ArrayList<String>()
    var correct = 0
    var maxAnswers = 9

    private fun getObject() = object {
        val answer1 = findViewById<Button>(R.id.answer1)
        val answer2 = findViewById<Button>(R.id.answer2)
        val answer3 = findViewById<Button>(R.id.answer3)
        val answer4 = findViewById<Button>(R.id.answer4)
        val default = findViewById<Button>(R.id.defaultButton)

        val txtQuestion = findViewById<TextView>(R.id.txtQuestion)
        val actual1 = findViewById<View>(R.id.actual1)
        val actual2 = findViewById<View>(R.id.actual2)
        val actual3 = findViewById<View>(R.id.actual3)
        val actual4 = findViewById<View>(R.id.actual4)
        val actual5 = findViewById<View>(R.id.actual5)
        val actual6 = findViewById<View>(R.id.actual6)
        val actual7 = findViewById<View>(R.id.actual7)
        val actual8 = findViewById<View>(R.id.actual8)
        val actual9 = findViewById<View>(R.id.actual9)
        val actual10 = findViewById<View>(R.id.actual10)

        val selected: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.answer_button_selected, null)
        val unselected: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.answer_button, null)
        val correct_button: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.answer_button_correct, null)
        val incorrect_button: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.answer_button_error, null)
        val current_circle: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.circle_current, null)
        val correct_circle: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.circle_ok, null)
        val incorrect_circle: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.circle_bad, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        var questionsES : MutableList<Question> = FilesManager.getQuestionsES(this)
        var shuffle : MutableList<Question> = questionsES.shuffled().toMutableList()
        randomQuestion(shuffle)
        val timer = findViewById<TextView>(R.id.txtTimer)
        val animation = findViewById<LottieAnimationView>(R.id.animationWin)

        //Answer 1
        getObject().answer1.setOnClickListener() {

                if (getObject().answer1.text.equals(shuffle.last().correct_answer)){
                    correct(animation, shuffle, getObject().answer1)
                }
                else{
                    incorrect(animation, shuffle, getObject().answer1)
                }
            disabled()
            shuffle.removeAt(shuffle.size-1)
        }

        //Answer 2
        getObject().answer2.setOnClickListener() {

            if (getObject().answer2.text.equals(shuffle.last().correct_answer)){
                correct(animation, shuffle, getObject().answer2)
            }
            else{
                incorrect(animation, shuffle, getObject().answer2)
            }
            disabled()
            shuffle.removeAt(shuffle.size-1)
        }

        //Answer 3
        getObject().answer3.setOnClickListener() {

            if (getObject().answer3.text.equals(shuffle.last().correct_answer)){
                correct(animation, shuffle, getObject().answer3)
            }
            else{
                incorrect(animation, shuffle, getObject().answer3)
            }
            disabled()
            shuffle.removeAt(shuffle.size-1)
        }

        //Answer 4
        getObject().answer4.setOnClickListener() {

            if (getObject().answer4.text.equals(shuffle.last().correct_answer)){
                correct(animation, shuffle, getObject().answer4)
            }
            else{
                incorrect(animation, shuffle, getObject().answer4)
            }
            disabled()
            shuffle.removeAt(shuffle.size-1)
        }

        contador_timer = object : CountDownTimer( 10000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var contador = millisUntilFinished / 1000
                timer.text = contador.toString()
            }

            override fun onFinish() {
                Handler().postDelayed({
                    timer.text = "0"
                    animationIncorrect(animation)
                    disabled()
                    incorrect(animation, shuffle, getObject().default)
                    shuffle.removeAt(shuffle.size-1)
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

    fun progress(){
        when (countQuestions + 1) {
            1 ->  getObject().actual1.background = getObject().current_circle
            2 ->  getObject().actual2.background = getObject().current_circle
            3 ->  getObject().actual3.background = getObject().current_circle
            4 ->  getObject().actual4.background = getObject().current_circle
            5 ->  getObject().actual5.background = getObject().current_circle
            6 ->  getObject().actual6.background = getObject().current_circle
            7 ->  getObject().actual7.background = getObject().current_circle
            8 ->  getObject().actual8.background = getObject().current_circle
            9 ->  getObject().actual9.background = getObject().current_circle
            10 -> getObject().actual10.background = getObject().current_circle
        }
    }

    fun progressCircles(){
        if (correct_answer){
            when (countQuestions) {
                1 ->  getObject().actual1.background = getObject().correct_circle
                2 ->  getObject().actual2.background = getObject().correct_circle
                3 ->  getObject().actual3.background = getObject().correct_circle
                4 ->  getObject().actual4.background = getObject().correct_circle
                5 ->  getObject().actual5.background = getObject().correct_circle
                6 ->  getObject().actual6.background = getObject().correct_circle
                7 ->  getObject().actual7.background = getObject().correct_circle
                8 ->  getObject().actual8.background = getObject().correct_circle
                9 ->  getObject().actual9.background = getObject().correct_circle
                10 -> getObject().actual10.background = getObject().correct_circle
            }
        }
        else{
            when (countQuestions) {
                1 ->  getObject().actual1.background = getObject().incorrect_circle
                2 ->  getObject().actual2.background = getObject().incorrect_circle
                3 ->  getObject().actual3.background = getObject().incorrect_circle
                4 ->  getObject().actual4.background = getObject().incorrect_circle
                5 ->  getObject().actual5.background = getObject().incorrect_circle
                6 ->  getObject().actual6.background = getObject().incorrect_circle
                7 ->  getObject().actual7.background = getObject().incorrect_circle
                8 ->  getObject().actual8.background = getObject().incorrect_circle
                9 ->  getObject().actual9.background = getObject().incorrect_circle
                10 -> getObject().actual10.background = getObject().incorrect_circle
            }
        }
    }

    fun randomQuestion(shuffle: MutableList<Question>){
        val randomQuestion = shuffle.last()
        currentPosition = randomQuestion.id.toInt()

        progress()
        if (countQuestions == 0 ){

        }else{
            progressCircles()
        }

        if (possibleAnswers.size == 0){

        }
        else{
            possibleAnswers.clear()
        }

        possibleAnswers.add(randomQuestion.correct_answer)
        possibleAnswers.add(randomQuestion.incorrect_answers[0])
        possibleAnswers.add(randomQuestion.incorrect_answers[1])
        possibleAnswers.add(randomQuestion.incorrect_answers[2])

        possibleAnswers.shuffle()

        for ((index, value) in possibleAnswers.withIndex()) {
            if (value.equals(shuffle.last().correct_answer)){
                correct = index
            }
        }

        getObject().txtQuestion.text = randomQuestion!!.question
        getObject().answer1.text = possibleAnswers[0]
        getObject().answer2.text = possibleAnswers[1]
        getObject().answer3.text = possibleAnswers[2]
        getObject().answer4.text = possibleAnswers[3]
    }

    //NEW QUESTION
    fun newQuestion(shuffle: MutableList<Question>){
        if (countQuestions == maxAnswers){
            endGame()
        }
        else{
            countQuestions += 1
            object : CountDownTimer( 2000, 1000) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    Handler().postDelayed({
                        contador_timer.start()
                        enabled()
                        colorBtnRestart()
                        randomQuestion(shuffle)
                    }, 100)
                }
            }.start()
        }
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

    fun colorBtnRestart(){
        getObject().answer1.setTextColor(Color.parseColor("#000000"))
        getObject().answer1.background = getObject().unselected

        getObject().answer2.setTextColor(Color.parseColor("#000000"))
        getObject().answer2.background = getObject().unselected

        getObject().answer3.setTextColor(Color.parseColor("#000000"))
        getObject().answer3.background = getObject().unselected

        getObject().answer4.setTextColor(Color.parseColor("#000000"))
        getObject().answer4.background = getObject().unselected
    }

    fun colorBtn(isCorrect: Boolean, myButton: Button, shuffle: MutableList<Question>) {
        if(isCorrect){
            myButton.setTextColor(Color.parseColor("#FFFFFF"))
            myButton.background = getObject().correct_button
        } else{
            myButton.setTextColor(Color.parseColor("#FFFFFF"))
            myButton.background = getObject().incorrect_button


            if (getObject().answer1.text.equals(shuffle.last().correct_answer)){
                getObject().answer1.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer1.background = getObject().correct_button
            }
            else if (getObject().answer2.text.equals(shuffle.last().correct_answer)){
                getObject().answer2.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer2.background = getObject().correct_button
            }
            else if (getObject().answer3.text.equals(shuffle.last().correct_answer)){
                getObject().answer3.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer3.background = getObject().correct_button
            }
            else{
                getObject().answer4.setTextColor(Color.parseColor("#FFFFFF"))
                getObject().answer4.background = getObject().correct_button
            }
        }
    }

    fun animationIncorrect(animation: LottieAnimationView){
        animation.setAnimation(R.raw.animation_incorrect)
        animation.visibility = View.VISIBLE
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

    fun animationCorrect(animation: LottieAnimationView){
        animation.setAnimation(R.raw.animation_correct)
        animation.visibility = View.VISIBLE
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

    fun correct(animation: LottieAnimationView, shuffle: MutableList<Question>, myButton: Button) {
        correcto = true
        correct_answer = true
        animationCorrect(animation)
        stopTimer()
        colorBtn(correcto, myButton, shuffle)
        correctAnswers+= 1

        object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                Handler().postDelayed({
                    newQuestion(shuffle)
                }, 100)
            }
        }.start()
    }

    // INCORRECT ANSWER
    fun incorrect(animation: LottieAnimationView, shuffle: MutableList<Question>, myButton: Button) {
        stopTimer()
        animationIncorrect(animation)
        correcto = false
        correct_answer = false
        colorBtn(correcto, myButton, shuffle)
        incorrectAnswers+= 1

        object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                Handler().postDelayed({
                    newQuestion(shuffle)
                }, 100)
            }
        }.start()
    }

    fun endGame(){
        progressCircles()
        val animation = findViewById<LottieAnimationView>(R.id.animationWin)
        stopTimer()
        animation.setAnimation(R.raw.animation_finish)
        animation.visibility = View.VISIBLE
        animation.playAnimation()
    }
}

