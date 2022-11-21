package com.example.eywa_android.ClassObject

import com.example.eywa_android.R
import java.io.Serializable

class QuizAchievement (id : Int, title : String, owned : Boolean) : Serializable {

    val id : Int
    val title : String
    var owned : Boolean
    var image : Int

    init {
        this.id= id
        this.title = title
        this.owned = owned
        image = if (owned){
            R.drawable.trophy
        } else {
            R.drawable.trophy_black_white
        }
    }

    companion object {
        fun generateList() : MutableList<QuizAchievement>{
            val testAchievement = mutableListOf(
                QuizAchievement(0, "Play a game", false),
                QuizAchievement(1, "Scifi: Play a game", false),
                QuizAchievement(2, "Action: Score more than 100pts", false),
                QuizAchievement(3, "Scifi: Score more than 100pts", false),
                QuizAchievement(4, "Animation: Score more than 100pts", false),
                QuizAchievement(5, "Comedy: Score more than 100pts",false),
                QuizAchievement(6, "Horror: Score more than 100pts", false),
                QuizAchievement(7, "Drama: Score more than 100pts", false),
                QuizAchievement(8, "Action: Score more than 100pts", false),
                QuizAchievement(9, "Scifi: Score more than 250pts", false),
                QuizAchievement(10, "Animation: Score more than 250pts", false),
                QuizAchievement(11, "Comedy: Score more than 250",false),
                QuizAchievement(12, "Horror: Score more than 250pts", false),
                QuizAchievement(13, "Drama: Score more than 250pts", false),
                QuizAchievement(14, "Be monkey", false),
                QuizAchievement(15, "Be God", false),
                QuizAchievement(16, "Only for admin", false),
            )

            return testAchievement
        }


    }
}