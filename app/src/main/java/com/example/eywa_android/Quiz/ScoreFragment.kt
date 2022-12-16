package com.example.eywa_android.Quiz

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eywa_android.Adapters.AchievementAdapter
import com.example.eywa_android.Adapters.UserRankingAdapter
import com.example.eywa_android.ClassObject.QuizAchievement
import com.example.eywa_android.ClassObject.QuizMatch
import com.example.eywa_android.ClassObject.User
import com.example.eywa_android.ClassObject.UserRanking
import com.example.eywa_android.Home.HomeActivity
import com.example.eywa_android.R
import com.example.eywa_android.Utility.FilesManager
import com.example.eywa_android.databinding.FragmentCharacterBinding
import com.example.eywa_android.databinding.FragmentScoreBinding
import kotlinx.android.synthetic.main.fragment_ranking.*
import kotlinx.android.synthetic.main.fragment_score.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.layout_ranking.*


class ScoreFragment : Fragment() {

    private var _binding : FragmentScoreBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel : QuizSharedViewModel by activityViewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScoreBinding.inflate(inflater, container, false)



        return binding.root
    }


    override fun onStart() {
        super.onStart()

        btnPlayAgain.setOnClickListener(){
            viewModelStore.clear()
            val myActivity = this.activity as QuestionsActivity
            if (sharedViewModel.hasAchievementUnlocked){
                val returnNewUserIntent = Intent(this.activity, HomeActivity::class.java)
                returnNewUserIntent.putExtra(QuestionsActivity.Questions.USER, sharedViewModel.user)
                myActivity.setResult(RESULT_OK, returnNewUserIntent)
            } else{
                myActivity.setResult(RESULT_CANCELED)
            }
            myActivity.finishActivity()
        }


        btnLeaderboard.setOnClickListener (){
            findNavController().navigate(R.id.action_scoreFragment_to_rankingFragment2)
        }

        initializeNumbersAndTexts()


        if (sharedViewModel.hasAchievementUnlocked){
            initializeAchievementList()
        }
        else {
            println("No has desbloqueado nada")
        }



        val allMatches = FilesManager.getMatches(requireContext())
        allMatches.sortByDescending { it.points.toInt() }

        var ranking = mutableListOf<UserRanking>()
        var users = FilesManager.getUsers(requireContext())
        var difficulty : String = ""
        var imgCategory : Int = 1
        var index = 0
        for(match : QuizMatch in  allMatches){

            when(match.difficulty){
                1 -> difficulty = "EASY"
                2 -> difficulty = "MEDIUM"
                3 -> difficulty = "HARD"
                4 -> difficulty = "LEGEND"
            }

            when (match.category.uppercase()){
                "ACTION" -> imgCategory=R.drawable.gun
                "SCIENCE FICTION" -> imgCategory=R.drawable.ufo
                "ANIMATION" -> imgCategory=R.drawable.disneyland
                "COMEDY" -> imgCategory=R.drawable.comedy
                "HORROR" -> imgCategory=R.drawable.horror
                "DRAMA" -> imgCategory=R.drawable.drama
            }


            var user = users.find { it.id == match.userId}
            var userRanking = UserRanking(
                user!!.username,
                match.points.toInt(),
                index + 1,
                user.image,
                imgCategory,
                match.category.uppercase(),
                difficulty,
            )
            index++
            ranking.add(userRanking)
        }

        var usersScore : MutableList<UserRanking> = mutableListOf()
        usersScore.clear()
        lateinit var actualUser:UserRanking
        for (user : UserRanking in ranking){
            if (user.score == sharedViewModel.points) {
                actualUser = user
            }
        }


        if (actualUser.rank == ranking.size) {
            usersScore.add(ranking[actualUser.rank-3])
            usersScore.add(ranking[actualUser.rank-2])
            usersScore.add(ranking[actualUser.rank-1])
        }
        else if(actualUser.rank == 1){
            usersScore.add(ranking[actualUser.rank-1])
            usersScore.add(ranking[actualUser.rank])
            usersScore.add(ranking[actualUser.rank+1])
        }
        else {
            usersScore.add(ranking[actualUser.rank-2])
            usersScore.add(ranking[actualUser.rank-1])
            usersScore.add(ranking[actualUser.rank])
        }

        val usersRankingAdapter = UserRankingAdapter(requireContext(), usersScore)
        listUsersScore.hasFixedSize()
        listUsersScore.layoutManager = LinearLayoutManager(requireContext())
        listUsersScore.adapter = usersRankingAdapter
    }

    fun initializeAchievementList() {
        val achievementAdapter = AchievementAdapter(requireContext(), sharedViewModel.achievementMatch, sharedViewModel.user.quizAchievementList,false)
        binding.listAchievements.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        binding.listAchievements.adapter = achievementAdapter
    }

    fun initializeNumbersAndTexts() {

        txtCongratulationsUsername.text = "CONGRATULATIONS " + sharedViewModel.user.username + "!"

        when(sharedViewModel.difficulty){
            "1" -> {
                txtDifficultyScore.text =  "EASY"
                txtDifficultyScore.setTextColor(resources.getColor(R.color.green))
            }
            "2" -> {
                txtDifficultyScore.text =  "MEDIUM"
                txtDifficultyScore.setTextColor(resources.getColor(R.color.orange))
            }
            "3" -> {
                txtDifficultyScore.text = "HARD"
                txtDifficultyScore.setTextColor(resources.getColor(R.color.red))
            }
            "4" -> {
                txtDifficultyScore.text = "LEGEND"
                txtDifficultyScore.setTextColor(resources.getColor(R.color.amarillo_eywa))
            }
        }

        when (sharedViewModel.category.uppercase()){
            "ACTION" -> imgCategoryScore.setImageResource(R.drawable.gun)
            "SCIENCE FICTION" -> imgCategoryScore.setImageResource(R.drawable.ufo)
            "ANIMATION" -> imgCategoryScore.setImageResource(R.drawable.disneyland)
            "COMEDY" -> imgCategoryScore.setImageResource(R.drawable.comedy)
            "HORROR" -> imgCategoryScore.setImageResource(R.drawable.horror)
            "DRAMA" -> imgCategoryScore.setImageResource(R.drawable.drama)
        }
        txtNumCorrects.text = sharedViewModel.correctAnswers.toString()
        txtNumIncorrects.text = sharedViewModel.incorrectAnswers.toString()
        txtPlayTime.text = sharedViewModel.timeUsed.toString()
        txtScoreScore.text = sharedViewModel.points.toString()
    }
}

