package com.example.eywa_android.Home

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.eywa_android.*
import com.example.eywa_android.ClassObject.Question
import com.example.eywa_android.Management.FilesManager
import com.example.eywa_android.Quiz.QuestionsActivity
import java.util.*

private const val CATEGORY = "categorySelected"
private const val DIFFICULTY = "difficulty"

class DifficultyFragment : Fragment(), HomeActivity.mainPage {

    private var difficultySelected: Int = 0

    private val sharedViewModel : HomeSharedViewModel by activityViewModels()

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

        val difficultyButtons = arrayOf(
            requireView().findViewById<Button>(R.id.btnEasy),
            requireView().findViewById<Button>(R.id.btnMedium),
            requireView().findViewById<Button>(R.id.btnHard),
            requireView().findViewById<Button>(R.id.btnLegend)
        )


        sharedViewModel.difficulty.observe(viewLifecycleOwner) { difficulty ->
            difficultyButtons[difficultySelected].backgroundTintList = requireContext().getColorStateList(
                R.color.white
            )
            when (difficulty) {
                0 -> {
                    difficultySelected = 0
                    difficultyButtons[0].backgroundTintList = requireContext().getColorStateList(R.color.green_easy)
                }
                1 -> {
                    difficultySelected = 1
                    difficultyButtons[1].backgroundTintList = requireContext().getColorStateList(R.color.orange_medium)
                }
                2 -> {
                    difficultySelected = 2
                    difficultyButtons[2].backgroundTintList = requireContext().getColorStateList(R.color.red)
                }
                3 -> {
                    difficultySelected = 3
                    difficultyButtons[3].backgroundTintList = requireContext().getColorStateList(R.color.yellow_legend)
                }

            }
        }

        val buttonCategory = requireView().findViewById<Button>(R.id.categoryButton)
        //category = arguments?.getString(QuestionsActivity.Questions.CATEGORY)
        setCategoryColor(buttonCategory, sharedViewModel.category.value.toString())




        for(index in 0..3){
            difficultyButtons[index].setOnClickListener(){
                sharedViewModel.changeDifficulty(index)
            }
        }

        val btnPlay = requireView().findViewById<Button>(R.id.btnPlay)
        btnPlay.setOnClickListener(){

            if(sharedViewModel.category.value.toString() != "Drama"){
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

                //We have to add 1 to the difficulty because Easy = 1 and for the array its easier to start for 0
                val d : Int = sharedViewModel.difficulty.value as Int
                val difficulty : String = (d + 1).toString()

                val category = sharedViewModel.category.value.toString().lowercase()

                var questionsReturn : MutableList<Question> = mutableListOf<Question>()
                for(q in Questions){
                    if(q.category == category && q.difficulty == difficulty){
                        questionsReturn.add(q)
                    }
                }

                if(questionsReturn.size > 10){
                    questionsReturn.shuffle()
                    questionsReturn = questionsReturn.drop(questionsReturn.size - 10).toMutableList()
                    //questionsReturn.subList(10, Questions.size).clear()
                }

                if (questionsReturn.size != 10){

                    val noQ = Question("100000", "No hay suficientes preguntas",
                        category!!, difficulty!!,
                        "This is the correct one",
                        arrayOf("Not this one", "Not this one", "Not this one")
                    )

                    for (i in questionsReturn.size..10){
                        questionsReturn.add(noQ)
                    }
                }

                intentQuestion.putParcelableArrayListExtra(QuestionsActivity.Questions.QUESTIONS, questionsReturn as ArrayList<Question>)

                startActivity(intentQuestion)
            } else{
                Toast.makeText(requireContext(), "Drama questions not supported", Toast.LENGTH_LONG).show()
            }



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

    private fun setCategoryColor(buttonCategory: Button, category: String){
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

        buttonCategory.setOnClickListener(){
            findNavController().popBackStack()
        }
    }




}