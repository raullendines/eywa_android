package com.example.eywa_android.Quiz

import androidx.lifecycle.ViewModel
import com.example.eywa_android.ClassObject.Question

class QuizSharedViewModel : ViewModel() {

    lateinit var questions : MutableList<Question>
    lateinit var difficulty : String
    lateinit var category : String
    var correctAnswers : Int = 0
    var incorrectAnswers : Int = 0
    var countQuestion : Int = 0



    lateinit var currentPossibleAnswers : ArrayList<String>
    var currentQuestionCorrectAnswer : Int = 0



    fun initQuestions(newQuestions : MutableList<Question>){
        questions = newQuestions
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

}