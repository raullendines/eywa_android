package com.example.eywa_android.Home

import android.graphics.BitmapFactory
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
import com.example.eywa_android.R
import com.example.eywa_android.Utility.FilesManager
import com.example.eywa_android.databinding.FragmentUserBinding


class UserFragment : Fragment(), HomeActivity.mainPage {

    private var _binding : FragmentUserBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel : HomeSharedViewModel by activityViewModels()

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
        /*
        postponeEnterTransition()
        requireView().doOnPreDraw { startPostponedEnterTransition() }


        val isTheUserProfile : Boolean = arguments?.get("Boolean") as Boolean

        if (isTheUserProfile){
            binding.buttonEditData.visibility = View.INVISIBLE
        }
        */

        //initiateTestUser()

        initUser()

        setAchievementGrid()

        getMatchHistory()

        if (sharedViewModel.userMatches.isNotEmpty()){

            setMatchHistoryGrid()

            binding.textNoGames.visibility = View.INVISIBLE
        }

        setPercetageGrid()

        binding.buttonEditData.setOnClickListener(){
            //Navigation to the other fragment
            findNavController().navigate(R.id.action_userFragment_to_editUserFragment)
        }

        binding.buttonGoBack.setOnClickListener(){
            findNavController().popBackStack()
        }
    }

    private fun getMatchHistory(){
        val allMatches = FilesManager.getMatches(requireContext())
        val usersMatches = mutableListOf<QuizMatch>()
        for (matches in allMatches){
            if (matches.userId == sharedViewModel.displayUser!!.id){
                usersMatches.add(matches)
            }
        }
        sharedViewModel.userMatches = usersMatches
    }



    private fun setAchievementGrid(){

        //sharedViewModel.achievementListRefresh(QuizAchievement.generateList())

        val achievementAdapter = AchievementAdapter(requireContext(), sharedViewModel.displayUser!!.quizAchievementList)
        binding.listAchievements.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.listAchievements.adapter = achievementAdapter


    }

    private fun setMatchHistoryGrid(){
        val myList = sharedViewModel.userMatches.reversed().toMutableList()
        val matchHistoryAdapter = MatchHistoryAdapter(requireContext(), myList)
        binding.listMatchHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.listMatchHistory.adapter = matchHistoryAdapter
    }

    private fun setPercetageGrid(){
        var percentageAdapter = getPercentages()

        val categoryPercentageAdapter = CategoryPercentageAdapter(requireContext(), percentageAdapter)

        binding.listCategories.layoutManager = GridLayoutManager(requireContext(), 3)

        binding.listCategories.adapter = categoryPercentageAdapter
    }

    private fun initUser(){
        //TODO Finish initialitzation
        binding.textUsername.text = sharedViewModel.displayUser!!.username
        val path = requireContext().filesDir.path.toString() + "/img/" + sharedViewModel.displayUser!!.image + ".jpeg"
        val bitmap = BitmapFactory.decodeFile(path)
        binding.imageUser.setImageBitmap(bitmap)

        binding.textRegisterDate.text = sharedViewModel.displayUser!!.dateOfRegister

    }

    private fun initiateTestUser(){
        //TODO Has to be deleted

//        val matchHistory = mutableListOf(
//            QuizMatch("science fiction", 15, 1, "25"),
//            QuizMatch("science fiction", 15, 1, "25"),
//            QuizMatch("science fiction", 15, 1, "25"),
//            QuizMatch("science fiction", 15, 1, "25"),
//            QuizMatch("science fiction", 15, 1, "25"),
//            QuizMatch("comedy", 15, 1, "25"),
//            QuizMatch("comedy", 15, 1, "25"),
//            QuizMatch("comedy", 15, 1, "25"),
//            QuizMatch("comedy", 15, 1, "25"),
//            QuizMatch("comedy", 15, 1, "25"),
//            QuizMatch("comedy", 15, 1, "25"),
//            QuizMatch("horror", 15, 1, "25"),
//            QuizMatch("horror", 15, 1, "25"),
//            QuizMatch("horror", 15, 1, "25"),
//            QuizMatch("horror", 15, 1, "25"),
//            QuizMatch("horror", 15, 1, "25"),
//            QuizMatch("animation", 15, 1, "25"),
//            QuizMatch("animation", 15, 1, "25"),
//            QuizMatch("animation", 15, 1, "25"),
//            QuizMatch("animation", 15, 1, "25"),
//            QuizMatch("animation", 15, 1, "25"),
//            QuizMatch("animation", 15, 1, "25"),
//            QuizMatch("drama", 15, 1, "25"),
//            QuizMatch("action", 15, 1, "25"),
//            QuizMatch("action", 15, 1, "25"),
//            QuizMatch("action", 15, 1, "25"),
//
//            )
//
//        val user = User("MariKong", "123", "kowalski", "none", 23, matchHistory, QuizAchievement.generateList())
//
//        sharedViewModel.setUserToDisplay(user)
    }

    private fun getPercentages() : MutableList<String>{
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

        if (sharedViewModel.userMatches.isEmpty()){
            for (index in 0 until percentages.size){
                returningList.add("0%")
            }
        } else{
            //Iterate over the user match history

            for (match in sharedViewModel.userMatches){
                when(match.category){
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

            for (index in 0 until percentages.size){
                var percentage = 0
                if (games[index] != 0){
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
    }
}