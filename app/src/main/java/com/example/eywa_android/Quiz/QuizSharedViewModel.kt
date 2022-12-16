package com.example.eywa_android.Quiz

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import com.example.eywa_android.ClassObject.Question
import com.example.eywa_android.ClassObject.QuizAchievement
import com.example.eywa_android.ClassObject.User

class QuizSharedViewModel : ViewModel() {

    lateinit var questions : MutableList<Question>
    lateinit var difficulty : String
    lateinit var category : String
    var points : Int = 0
    var correctAnswers : Int = 0
    var incorrectAnswers : Int = 0
    var countQuestion : Int = 0
    var timeUsed = 0
    var muted = false;
    var mediaPlayerQuiz = MediaPlayer()
    val easyPoints : Int = 30
    val mediumPoints: Int = 40
    val hardPoints :Int = 50
    val legendPoints:Int = 60



    lateinit var currentPossibleAnswers : ArrayList<String>
    var currentQuestionCorrectAnswer : Int = 0
    var achievementList : MutableList<Int> = mutableListOf()
    var achievementMatch : MutableList<QuizAchievement> = mutableListOf()

    var timeLeft : Long = 15

    lateinit var user : User

    var hasAchievementUnlocked = false


    fun achievementUnlocked(){
        hasAchievementUnlocked = true
    }

    fun initQuestions(newQuestions : MutableList<Question>){
        questions = newQuestions
        questions.shuffle()
        category = questions[0].category
        difficulty = questions[0].difficulty
    }

    fun questionsRemoveLast(){
        questions.removeAt(questions.size-1)
    }

    fun initPossibleAnswers(possibleAnswers : ArrayList<String>){
        currentPossibleAnswers = possibleAnswers
    }

    fun questionCorrect(){
        correctAnswers++
    }

    fun questionIncorrect(){
        incorrectAnswers++
    }

    fun questionDone(){
        countQuestion++
    }

    fun changeCurrentQuestionCorrectAnswer(i : Int){
        currentQuestionCorrectAnswer = i
    }

    fun addTime(){
        timeUsed++
    }

    fun sortAchievementList(){
        user.quizAchievementList.sort()
    }

}