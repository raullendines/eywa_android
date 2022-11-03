package com.example.eywa_android

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.fragment.findNavController
import java.nio.file.Files
import java.util.ArrayList
import java.util.function.Predicate

private const val CATEGORY = "categorySelected"
private const val DIFFICULTY = "difficulty"

class DifficultyFragment : Fragment(), Home.mainPage {
    // TODO: Rename and change types of parameters
    private var category: String? = "Action"
    private var difficultySelected: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_difficulty, container, false)
    }

    override fun onStart() {
        super.onStart()

        val buttonCategory = requireView().findViewById<Button>(R.id.categoryButton)

        category = arguments?.getString(CATEGORY)

        val difficultyButtons = arrayOf(
            requireView().findViewById<Button>(R.id.btnEasy),
            requireView().findViewById<Button>(R.id.btnMedium),
            requireView().findViewById<Button>(R.id.btnHard),
            requireView().findViewById<Button>(R.id.btnLegend)
        )

        difficultyButtons[0].setOnClickListener(){
            if (difficultySelected != 0){
                difficultyButtons[difficultySelected].setBackgroundResource(R.drawable.rounded_corners_white)
                difficultySelected = 0
                difficultyButtons[0].setBackgroundResource(R.drawable.rounded_corners_yellow)
            }
        }

        difficultyButtons[1].setOnClickListener(){
            if (difficultySelected != 1){
                difficultyButtons[difficultySelected].setBackgroundResource(R.drawable.rounded_corners_white)
                difficultySelected = 1
                difficultyButtons[1].setBackgroundResource(R.drawable.rounded_corners_red)
            }
        }

        difficultyButtons[2].setOnClickListener(){
            if (difficultySelected != 2){
                difficultyButtons[difficultySelected].setBackgroundResource(R.drawable.rounded_corners_white)
                difficultySelected = 2
                difficultyButtons[2].setBackgroundResource(R.drawable.rounded_corners_yellow)
            }
        }

        difficultyButtons[3].setOnClickListener(){
            if (difficultySelected != 3){
                difficultyButtons[difficultySelected].setBackgroundResource(R.drawable.rounded_corners_white)
                difficultySelected = 3
                difficultyButtons[3].setBackgroundResource(R.drawable.rounded_corners_red)
            }
        }


        val myActivity : Home = activity as Home
        changeLang(myActivity.lang)

        val btnPlay = requireView().findViewById<Button>(R.id.btnPlay)
        btnPlay.setOnClickListener(){

            val intentQuestion = Intent(this.activity, QuestionsActivity::class.java)

            var Questions : MutableList<Question> = mutableListOf<Question>()
            when(myActivity.lang){
                "cat" -> Questions = FilesManager.getQuestionsCA(requireContext())
                "esp" -> Questions = FilesManager.getQuestionsES(requireContext())
                "eng" -> Questions = FilesManager.getQuestionsEN(requireContext())
            }
            var difficulty : String = (difficultySelected + 1).toString()

            var questionsReturn : MutableList<Question> = mutableListOf<Question>()
            for(q in Questions){
                if(q.category == category!!.lowercase() && q.difficulty != difficulty){
                    questionsReturn.add(q)
                }
            }

            if(questionsReturn.size > 10){
                questionsReturn.shuffle()
                questionsReturn.subList(10, Questions.size).clear()
            }

            intentQuestion.putParcelableArrayListExtra(QuestionsActivity.Questions.QUESTIONS, questionsReturn as ArrayList<Question>)

            startActivity(intentQuestion)


        }
    }


    override fun changeLang(lang: String) {
        val difficultyButtons = arrayOf(
            requireView().findViewById<Button>(R.id.btnEasy),
            requireView().findViewById<Button>(R.id.btnMedium),
            requireView().findViewById<Button>(R.id.btnHard),
            requireView().findViewById<Button>(R.id.btnLegend)
        )

        val txtViewDifficulty = requireView().findViewById<TextView>(R.id.txtViewDifficulty)
        val playButton = requireView().findViewById<Button>(R.id.btnPlay)

        when(lang){
            "cat" -> {
                txtViewDifficulty.setText("DIFICULTAT")
                difficultyButtons[0].setText("FÀCIL")
                difficultyButtons[1].setText("MITJANA")
                difficultyButtons[2].setText("DIFICIL")
                difficultyButtons[3].setText("LLEGENDA")
                playButton.setText("JUGAR")
            }
            "esp" -> {
                txtViewDifficulty.setText("DIFICULTAD")
                difficultyButtons[0].setText("FÁCIL")
                difficultyButtons[1].setText("MEDIANA")
                difficultyButtons[2].setText("DIFÍCIL")
                difficultyButtons[3].setText("LEYENDA")
                playButton.setText("JUGAR")
            }
            "eng" -> {
                txtViewDifficulty.setText("DIFFICULTY")
                difficultyButtons[0].setText("EASY")
                difficultyButtons[1].setText("MEDIUM")
                difficultyButtons[2].setText("HARD")
                difficultyButtons[3].setText("LEGEND")
                playButton.setText("PLAY")
            }
        }
        changeCategoryLang(lang)
    }

    private fun changeCategoryLang(lang : String){
        val buttonCategory = requireView().findViewById<Button>(R.id.categoryButton)
        when(lang){
            "cat" -> {
                when(category){
                    "Action" -> buttonCategory.text = "ACCIÓ"
                    "Comedy" -> buttonCategory.text = "COMEDIA"
                    "Science Fiction" -> buttonCategory.text = "CIENCIA FICCIÓ"
                    "Horror" -> buttonCategory.text = "TERROR"
                    "Animation" -> buttonCategory.text = "ANIMACIÓ"
                    "Drama" -> buttonCategory.text = "DRAMA"
                }
            }
            "esp" -> {
                when(category){
                    "Action" -> buttonCategory.text = "ACCIÓN"
                    "Comedy" -> buttonCategory.text = "COMEDIA"
                    "Science Fiction" -> buttonCategory.text = "CIENCIA FICCIÓN"
                    "Horror" -> buttonCategory.text = "TERROR"
                    "Animation" -> buttonCategory.text = "ANIMACIÓN"
                    "Drama" -> buttonCategory.text = "DRAMA"
                }
            } else -> {
                buttonCategory.text = category!!.uppercase()
            }
        }

    }
}