package com.example.eywa_android.Home

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.eywa_android.*
import com.example.eywa_android.ClassObject.Question
import com.example.eywa_android.ClassObject.User
import com.example.eywa_android.Quiz.QuestionsActivity
import com.example.eywa_android.Utility.FilesManager
import com.example.eywa_android.databinding.FragmentDifficultyBinding
import java.util.*
import kotlin.collections.ArrayList

private const val CATEGORY = "categorySelected"
private const val DIFFICULTY = "difficulty"

class DifficultyFragment : Fragment(), HomeActivity.mainPage {

    private var difficultySelected: Int = 0

    private val sharedViewModel: HomeSharedViewModel by activityViewModels()


    private var _binding: FragmentDifficultyBinding? = null
    private val binding get() = _binding!!

//    class ReturnUser : ActivityResultContract<User, Uri?>() {
//        override fun createIntent(context: Context, questions: ArrayList<Question>, user: User) =
//
//            Intent().apply {
//                putParcelableArrayListExtra(QuestionsActivity.Questions.QUESTIONS, questions)
//                putExtra(QuestionsActivity.Questions.USER, user)
//            }
//
//        override fun parseResult(resultCode: Int, result: Intent?) : Uri? {
//            if (resultCode != Activity.RESULT_OK) {
//                return null
//            }
//            return result?.getParcelableExtra(QuestionsActivity.Questions.USER)
//        }
//
//
//    }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val newUser =
                    intent!!.getSerializableExtra(QuestionsActivity.Questions.USER) as User
                sharedViewModel.setUserToDisplay(newUser)
                // Handle the Intent
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDifficultyBinding.inflate(inflater, container, false)

        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart() {
        super.onStart()

        val difficultyButtons = arrayOf(
            binding.btnEasy,
            binding.btnMedium,
            binding.btnHard,
            binding.btnLegend
        )


        sharedViewModel.difficulty.observe(viewLifecycleOwner) { difficulty ->
            difficultyButtons[difficultySelected].backgroundTintList =
                requireContext().getColorStateList(
                    R.color.white
                )
            when (difficulty) {
                0 -> {
                    difficultySelected = 0
                    difficultyButtons[0].backgroundTintList =
                        requireContext().getColorStateList(R.color.green)
                }
                1 -> {
                    difficultySelected = 1
                    difficultyButtons[1].backgroundTintList =
                        requireContext().getColorStateList(R.color.orange)
                }
                2 -> {
                    difficultySelected = 2
                    difficultyButtons[2].backgroundTintList =
                        requireContext().getColorStateList(R.color.red)
                }
                3 -> {
                    difficultySelected = 3
                    difficultyButtons[3].backgroundTintList =
                        requireContext().getColorStateList(R.color.amarillo_eywa)
                }

            }
        }


        //category = arguments?.getString(QuestionsActivity.Questions.CATEGORY)
        setCategoryColor(sharedViewModel.category.value.toString())

        for (index in 0..3) {
            difficultyButtons[index].setOnClickListener() {
                sharedViewModel.changeDifficulty(index)
            }
        }

        binding.btnPlay.setOnClickListener() {
            sharedViewModel.mediaPlayer.release()

            val intentQuestion = Intent(this.activity, QuestionsActivity::class.java)

            var Questions: MutableList<Question> = mutableListOf()

            var locale: Locale? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = resources.configuration.locales.get(0);
            } else {
                //noinspection deprecation
                locale = resources.configuration.locale
            }
            val localeLang = locale!!.language



            when (localeLang) {
                "ca" -> Questions = FilesManager.getQuestionsCA(requireContext())
                "es" -> Questions = FilesManager.getQuestionsES(requireContext())
                "en" -> Questions = FilesManager.getQuestionsEN(requireContext())
            }

            //We have to add 1 to the difficulty because Easy = 1 and for the array its easier to start for 0
            val d: Int = sharedViewModel.difficulty.value as Int
            val difficulty: String = (d + 1).toString()

            val category = sharedViewModel.category.value.toString().lowercase()

            var questionsReturn: MutableList<Question> = mutableListOf<Question>()
            for (q in Questions) {
                if (q.category == category && q.difficulty == difficulty) {
                    questionsReturn.add(q)
                }
            }

            if (questionsReturn.size > 10) {
                questionsReturn.shuffle()
                questionsReturn = questionsReturn.drop(questionsReturn.size - 10).toMutableList()
                //questionsReturn.subList(10, Questions.size).clear()
            }

            if (questionsReturn.size != 10) {

                val noQ = Question(
                    "100000", "No hay suficientes preguntas",
                    category, difficulty,
                    "This is the correct one",
                    arrayOf("Not this one", "Not this one", "Not this one")
                )

                for (i in questionsReturn.size..10) {
                    questionsReturn.add(noQ)
                }
            }

            val myActivity = this.activity as HomeActivity


            intentQuestion.putParcelableArrayListExtra(
                QuestionsActivity.Questions.QUESTIONS,
                questionsReturn as ArrayList<Question>
            )
            intentQuestion.putExtra(QuestionsActivity.Questions.USER, sharedViewModel.displayUser!!)
            intentQuestion.putExtra(QuestionsActivity.Questions.MUTED, sharedViewModel.muted)
            startForResult.launch(intentQuestion)

        }
    }


    override fun changeLang() {
        val difficultyButtons = arrayOf(
            binding.btnEasy,
            binding.btnMedium,
            binding.btnHard,
            binding.btnLegend
        )

        binding.txtViewDifficulty.setText(R.string.difficulty)
        difficultyButtons[0].setText(R.string.easy)
        difficultyButtons[1].setText(R.string.medium)
        difficultyButtons[2].setText(R.string.hard)
        difficultyButtons[3].setText(R.string.legend)


        binding.btnPlay.setText(R.string.btnPlay)

    }

    private fun setCategoryColor(category: String) {
        when (category) {
            "Action" -> {
                binding.buttonCategory.backgroundTintList =
                    requireContext().getColorStateList(R.color.action)
                binding.textCategory.setText(R.string.category_action)
                binding.imageCategory.setImageResource(R.drawable.gun)
            }
            "Comedy" -> {
                binding.buttonCategory.backgroundTintList =
                    requireContext().getColorStateList(R.color.comedy)
                binding.textCategory.setText(R.string.category_comedy)
                binding.imageCategory.setImageResource(R.drawable.comedy)
            }
            "Science Fiction" -> {
                binding.buttonCategory.backgroundTintList =
                    requireContext().getColorStateList(R.color.sci_fi)
                binding.textCategory.setText(R.string.category_scifi)
                binding.imageCategory.setImageResource(R.drawable.ufo)
            }
            "Horror" -> {
                binding.buttonCategory.backgroundTintList =
                    requireContext().getColorStateList(R.color.horror)
                binding.textCategory.setText(R.string.category_horror)
                binding.imageCategory.setImageResource(R.drawable.horror)
            }
            "Animation" -> {
                binding.buttonCategory.backgroundTintList =
                    requireContext().getColorStateList(R.color.animation)
                binding.textCategory.setText(R.string.category_animation)
                binding.imageCategory.setImageResource(R.drawable.disneyland)
            }
            "Drama" -> {
                binding.buttonCategory.backgroundTintList =
                    requireContext().getColorStateList(R.color.drama)
                binding.textCategory.setText(R.string.category_drama)
                binding.imageCategory.setImageResource(R.drawable.drama)
            }
        }
        binding.textCategory.setTextColor(Color.parseColor("#FFFFFF"))

        binding.buttonCategory.setOnClickListener() {
            findNavController().popBackStack()
        }
    }


}