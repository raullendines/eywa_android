package com.example.eywa_android.Home

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eywa_android.Adapters.AchievementAdapter
import com.example.eywa_android.Adapters.CategoryPercentageAdapter
import com.example.eywa_android.Adapters.MatchHistoryAdapter
import com.example.eywa_android.ClassObject.QuizAchievement
import com.example.eywa_android.ClassObject.QuizMatch
import com.example.eywa_android.ClassObject.User
import com.example.eywa_android.Quiz.QuestionsActivity
import com.example.eywa_android.R
import com.example.eywa_android.Utility.FilesManager
import com.example.eywa_android.databinding.FragmentUserBinding
import kotlinx.android.synthetic.main.fragment_user.*
import java.util.*


class UserFragment : Fragment(), HomeActivity.mainPage {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: HomeSharedViewModel by activityViewModels()

    private lateinit var userToDisplay: User

    private var otherUser: User? = null
    private val otherUserMatches = mutableListOf<QuizMatch>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()


        //If we want to a user other than the logged one to show their data we send FALSE to this fragment
        var loggedUser = arguments?.getBoolean("OTHER_USER")
        //If we don't get any arguments it's because we want to show the logged User
        if (loggedUser == null) {
            userToDisplay = sharedViewModel.displayUser!!
        } else{
            userToDisplay = arguments?.getSerializable(QuestionsActivity.Questions.USER) as User
            otherUser = arguments?.getSerializable(QuestionsActivity.Questions.USER) as User

            binding.btnEditContainer.visibility = View.INVISIBLE
            binding.buttonEditData.visibility = View.INVISIBLE
        }



        initUser()

        setAchievementGrid()

        getMatchHistory()

        if (sharedViewModel.userMatches.isNotEmpty()) {

            setMatchHistoryGrid()

            binding.textNoGames.visibility = View.INVISIBLE

            setBestScore()
        }
        setPercetageGrid()

        binding.buttonEditData.setOnClickListener() {
            //Navigation to the other fragment
            findNavController().navigate(R.id.action_userFragment_to_editUserFragment)
        }

        binding.buttonGoBack.setOnClickListener() {
            findNavController().popBackStack()
        }
    }

    private fun setBestScore() {
        var bestPuntuation = 0

        for (match in sharedViewModel.userMatches) {
            if (bestPuntuation < match.points.toInt()) {
                bestPuntuation = match.points.toInt()
            }
        }

        val textToDisplay = bestPuntuation.toString() + " " + getString(R.string.points)
        binding.textBestScore.text = textToDisplay
    }

    private fun getMatchHistory() {
        val allMatches = FilesManager.getMatches(requireContext())
        val usersMatches = mutableListOf<QuizMatch>()
        for (matches in allMatches) {
            if (matches.userId == userToDisplay.id) {
                usersMatches.add(matches)
            }
        }
        sharedViewModel.userMatches = usersMatches
        getRanking(allMatches)
    }

    private fun getRanking(allMatches : MutableList<QuizMatch>){
        allMatches.sortByDescending { it.points.toInt() }
        var userIds = mutableListOf<Int>()
        var index = 0
        var found = false
        do {
            if (allMatches[index].userId == userToDisplay.id){
                found = true
                val ranking = userIds.size + 1
                textRankingNumber.text = ranking.toString()
            } else if (!userIds.contains(allMatches[index].userId)){
                userIds.add(allMatches[index].userId)
            }
            index += 1
        } while (index < allMatches.size && !found )
    }


    private fun setAchievementGrid() {

        //load achievements
        val listAchievements = FilesManager.getAchievements(requireContext())

        //create adapter

        val achievementAdapter = AchievementAdapter(
            context = requireContext(),
            achievementListUnchanged = listAchievements,
            userAchievements = userToDisplay.quizAchievementList,
            fullList = true
        )
        binding.listAchievements.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.listAchievements.adapter = achievementAdapter


    }

    private fun setMatchHistoryGrid() {
        val myList = sharedViewModel.userMatches.reversed().toMutableList()
        val matchHistoryAdapter = MatchHistoryAdapter(requireContext(), myList)
        binding.listMatchHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.listMatchHistory.adapter = matchHistoryAdapter


    }

    private fun setPercetageGrid() {
        var percentageAdapter = getPercentages()

        val categoryPercentageAdapter =
            CategoryPercentageAdapter(requireContext(), percentageAdapter)

        binding.listCategories.layoutManager = GridLayoutManager(requireContext(), 3)

        binding.listCategories.adapter = categoryPercentageAdapter

    }

    private fun initUser() {
        //TODO Finish initialitzation
        binding.textUsername.text = userToDisplay.username
        val path =
            requireContext().filesDir.path.toString() + "/img/" + userToDisplay.image + ".jpeg"
        val bitmap = BitmapFactory.decodeFile(path)
        binding.imageUser.setImageBitmap(bitmap)

        binding.textRegisterDate.text = userToDisplay.dateOfRegister

    }


    private fun getPercentages(): MutableList<String> {
        var returningList = mutableListOf<String>()

        var percentages = mutableListOf(
            0,
            0,
            0,
            0,
            0,
            0
        )

        var games = mutableListOf(
            0,
            0,
            0,
            0,
            0,
            0
        )

        if (sharedViewModel.userMatches.isEmpty()) {
            for (index in 0 until percentages.size) {
                returningList.add("0%")
            }
        } else {
            //Iterate over the user match history

            for (match in sharedViewModel.userMatches) {
                when (match.category) {
                    "action" -> {
                        games[0]++
                        percentages[0] += match.correctAnswers
                    }
                    "science fiction" -> {
                        games[1]++
                        percentages[1] += match.correctAnswers
                    }
                    "animation" -> {
                        games[2]++
                        percentages[2] += match.correctAnswers
                    }
                    "comedy" -> {
                        games[3]++
                        percentages[3] += match.correctAnswers
                    }
                    "horror" -> {
                        games[4]++
                        percentages[4] += match.correctAnswers
                    }
                    "drama" -> {
                        games[5]++
                        percentages[5] += match.correctAnswers
                    }
                }
            }

            for (index in 0 until percentages.size) {
                var percentage = 0
                if (games[index] != 0) {
                    percentage = percentages[index] * 100 / (games[index] * 10)
                }
                returningList.add("$percentage%")
            }
        }



        return returningList
    }

    override fun changeLang() {
        binding.textRegisterDateLabel.text = getString(R.string.date_register)
        binding.textBestScoreHeader.text = getString(R.string.best_score)
        binding.textAchievements.text = getString(R.string.achievements)
        binding.textMatchHistory.text = getString(R.string.match_history)
        binding.textNoGames.text = getString(R.string.no_games)
        binding.textPerformanceByCategory.text = getString(R.string.category_performance)

        binding.textCategory.text = getString(R.string.category)
        binding.textDifficulty.text = getString(R.string.difficulty)
        binding.textScore.text = getString(R.string.score)

        setAchievementGrid()


    }
}