package com.example.eywa_android

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.os.ConfigurationCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.fragment.findNavController
import java.nio.file.Files
import java.util.*
import java.util.function.Predicate

private const val CATEGORY = "categorySelected"
private const val DIFFICULTY = "difficulty"

class DifficultyFragment : Fragment(), Home.mainPage{

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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart() {
        super.onStart()

        val buttonCategory = requireView().findViewById<Button>(R.id.categoryButton)


        category = arguments?.getString(QuestionsActivity.Questions.CATEGORY)
        setCategoryColor(buttonCategory)
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

        val btnPlay = requireView().findViewById<Button>(R.id.btnPlay)
        btnPlay.setOnClickListener(){

            val intentQuestion = Intent(this.activity, QuestionsActivity::class.java)

            var Questions : MutableList<Question> = mutableListOf<Question>()

            var locale : Locale? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                locale = resources.configuration.locales.get(0);
            } else{
                //noinspection deprecation
                locale = resources.configuration.locale
            }
            var localeLang = locale!!.language



            when(localeLang){
                "ca" -> Questions = FilesManager.getQuestionsCA(requireContext())
                "es" -> Questions = FilesManager.getQuestionsES(requireContext())
                "en" -> Questions = FilesManager.getQuestionsEN(requireContext())
            }
            var difficulty : String = (difficultySelected + 1).toString()

            var questionsReturn : MutableList<Question> = mutableListOf<Question>()
            for(q in Questions){
                if(q.category == category!!.lowercase() && q.difficulty == difficulty){
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


    override fun changeLang() {
        val difficultyButtons = arrayOf(
            requireView().findViewById<Button>(R.id.btnEasy),
            requireView().findViewById<Button>(R.id.btnMedium),
            requireView().findViewById<Button>(R.id.btnHard),
            requireView().findViewById<Button>(R.id.btnLegend)
        )

        val txtViewDifficulty = requireView().findViewById<TextView>(R.id.txtViewDifficulty)
        val playButton = requireView().findViewById<Button>(R.id.btnPlay)


        txtViewDifficulty.setText(R.string.difficulty)
        difficultyButtons[0].setText(R.string.easy)
        difficultyButtons[1].setText(R.string.medium)
        difficultyButtons[2].setText(R.string.hard)
        difficultyButtons[3].setText(R.string.legend)


        playButton.setText(R.string.btnPlay)

    }

    private fun setCategoryColor(buttonCategory: Button){
        when (category){
            "Action" -> {
                buttonCategory.backgroundTintList =
                    requireContext().getColorStateList(R.color.action)
                buttonCategory.setText(R.string.category_action)
            }
            "Comedy" -> {
                buttonCategory.backgroundTintList =
                    requireContext().getColorStateList(R.color.comedy)
                buttonCategory.setText(R.string.category_comedy)
            }
            "Science Fiction" -> {
                buttonCategory.backgroundTintList =
                    requireContext().getColorStateList(R.color.sci_fi)
                buttonCategory.setText(R.string.category_scifi)
            }
            "Horror" -> {
                buttonCategory.backgroundTintList =
                    requireContext().getColorStateList(R.color.horror)
                buttonCategory.setText(R.string.category_horror)
            }
            "Animation" -> {
                buttonCategory.backgroundTintList =
                    requireContext().getColorStateList(R.color.animation)
                buttonCategory.setText(R.string.category_animation)
            }
            "Drama" -> {
                buttonCategory.backgroundTintList =
                    requireContext().getColorStateList(R.color.drama)
                buttonCategory.setText(R.string.category_drama)
            }
        }
        buttonCategory.setTextColor(Color.parseColor("#FFFFFF"))
    }




}