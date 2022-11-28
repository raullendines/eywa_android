package com.example.eywa_android.Quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eywa_android.Adapters.AchievementAdapter
import com.example.eywa_android.Adapters.UserRankingAdapter
import com.example.eywa_android.ClassObject.QuizAchievement
import com.example.eywa_android.ClassObject.QuizMatch
import com.example.eywa_android.ClassObject.User
import com.example.eywa_android.ClassObject.UserRanking
import com.example.eywa_android.R
import com.example.eywa_android.Utility.FilesManager
import com.example.eywa_android.databinding.FragmentCharacterBinding
import com.example.eywa_android.databinding.FragmentScoreBinding
import kotlinx.android.synthetic.main.fragment_ranking.*
import kotlinx.android.synthetic.main.fragment_score.*
import kotlinx.android.synthetic.main.fragment_user.*


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
        var index = 0
        for(match : QuizMatch in  allMatches){
            var difficulty : String = ""
            when(match.difficulty){
                1 -> difficulty = "EASY"
                2 -> difficulty = "MEDIUM"
                3 -> difficulty = "HARD"
                4 -> difficulty = "LEGEND"
            }

            var imgCategory : Int = 1
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

        var usersScore : MutableList<UserRanking> = mutableListOf(UserRanking("pau",1,1,"",R.drawable.logo_eywa,"",""))
        usersScore.clear()
        lateinit var actualUser:UserRanking
        for (user : UserRanking in ranking){
            if (user.score == sharedViewModel.points) {
                actualUser = user
            }
        }


        if (actualUser.rank == ranking.size) {
            usersScore.add(ranking[actualUser.rank-2])
            usersScore.add(ranking[actualUser.rank-1])
            usersScore.add(ranking[actualUser.rank])
        }
        else if(actualUser.rank == 1){
            usersScore.add(ranking[actualUser.rank])
            usersScore.add(ranking[actualUser.rank+1])
            usersScore.add(ranking[actualUser.rank+2])
        }
        else {
            usersScore.add(ranking[actualUser.rank-1])
            usersScore.add(ranking[actualUser.rank])
            usersScore.add(ranking[actualUser.rank+1])
        }

        val usersRankingAdapter = UserRankingAdapter(requireContext(), usersScore)
        listUsersScore.hasFixedSize()
        listUsersScore.layoutManager = LinearLayoutManager(requireContext())
        listUsersScore.adapter = usersRankingAdapter





    }

    fun initializeAchievementList() {
        val achievementAdapter = AchievementAdapter(requireContext(), sharedViewModel.achievementList)
        binding.listAchievements.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.listAchievements.adapter = achievementAdapter
    }

    fun initializeNumbersAndTexts() {
        txtNumCorrects.text = sharedViewModel.correctAnswers.toString()
        txtNumIncorrects.text = sharedViewModel.incorrectAnswers.toString()
        txtPlayTime.text = sharedViewModel.timeUsed.toString()
        txtDifficultyScore.text = sharedViewModel.difficulty
        txtScoreScore.text = sharedViewModel.points.toString()
    }
}