package com.example.eywa_android

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.doOnPreDraw
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eywa_android.Adapters.AchievementAdapter
import com.example.eywa_android.Adapters.CategoryPercentageAdapter
import com.example.eywa_android.Adapters.MatchHistoryAdapter
import com.example.eywa_android.ClassObject.QuizAchievement
import com.example.eywa_android.ClassObject.QuizMatch
import com.example.eywa_android.ClassObject.User
import com.example.eywa_android.Home.HomeSharedViewModel
import com.example.eywa_android.databinding.FragmentUserBinding
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.android.synthetic.main.achievement_item.view.*


class UserFragment : Fragment() {

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

        //setAchievementGrid()

        if (!sharedViewModel.displayUser!!.quizMatchHistory.isNullOrEmpty()){

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



    @SuppressLint("ResourceAsColor")
    private fun setAchievementGrid(){
        val lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

        val testAchievement = mutableListOf(
            QuizAchievement(1, "Play a game of each category", lorem,true),
            QuizAchievement(2, "Score more than 25000pts on action category", lorem,true),
            QuizAchievement(3, "Score more than 25000pts on scifi category", lorem,true),
            QuizAchievement(4, "Score more than 25000pts on animation category", lorem,true),
            QuizAchievement(5, "Score more than 25000pts on comedy category",lorem,true),
            QuizAchievement(6, "Score more than 25000pts on horror category", lorem,true),
            QuizAchievement(7, "Score more than 25000pts on drama category", lorem,false),
            QuizAchievement(8, "Be monkey", lorem,false),
            QuizAchievement(9, "Be God", lorem,false),
            QuizAchievement(10, "Only for admin", lorem,false),
        )

        val achievementAdapter = AchievementAdapter(requireContext(), testAchievement)
        binding.listAchievements.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.listAchievements.adapter = achievementAdapter

        /*
        achievementAdapter.setOnClickListener(){
            //testAchievement[binding.listAchievements.getChildAdapterPosition(it)]

            exitTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(com.google.android.material.R.integer.material_motion_duration_long_1).toLong()
            }
            reenterTransition = MaterialElevationScale(true).apply {
                duration = resources.getInteger(com.google.android.material.R.integer.material_motion_duration_long_1).toLong()
            }
            val detailTransitionName = "achievement_card"


            val listView : View = binding.listAchievements.findViewHolderForAdapterPosition(binding.listAchievements.getChildAdapterPosition(it))!!.itemView

            val extras = FragmentNavigatorExtras(it.startCard to "achievement_card")
            findNavController().navigate(
                R.id.action_userFragment_to_achievementFragment,
                null,
                null,
                extras
            )




        }

         */



    }

    private fun setMatchHistoryGrid(){
        val matchHistoryAdapter = MatchHistoryAdapter(requireContext(), sharedViewModel.displayUser!!.quizMatchHistory!!)
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

        binding.textRegisterDate.text = "16/11/2022"


    }

    private fun initiateTestUser(){
        //TODO Has to be deleted



        val matchHistory = mutableListOf(
            QuizMatch("science fiction", 15, 1, "25"),
            QuizMatch("science fiction", 15, 1, "25"),
            QuizMatch("science fiction", 15, 1, "25"),
            QuizMatch("science fiction", 15, 1, "25"),
            QuizMatch("science fiction", 15, 1, "25"),
            QuizMatch("comedy", 15, 1, "25"),
            QuizMatch("comedy", 15, 1, "25"),
            QuizMatch("comedy", 15, 1, "25"),
            QuizMatch("comedy", 15, 1, "25"),
            QuizMatch("comedy", 15, 1, "25"),
            QuizMatch("comedy", 15, 1, "25"),
            QuizMatch("horror", 15, 1, "25"),
            QuizMatch("horror", 15, 1, "25"),
            QuizMatch("horror", 15, 1, "25"),
            QuizMatch("horror", 15, 1, "25"),
            QuizMatch("horror", 15, 1, "25"),
            QuizMatch("animation", 15, 1, "25"),
            QuizMatch("animation", 15, 1, "25"),
            QuizMatch("animation", 15, 1, "25"),
            QuizMatch("animation", 15, 1, "25"),
            QuizMatch("animation", 15, 1, "25"),
            QuizMatch("animation", 15, 1, "25"),
            QuizMatch("drama", 15, 1, "25"),
            QuizMatch("action", 15, 1, "25"),
            QuizMatch("action", 15, 1, "25"),
            QuizMatch("action", 15, 1, "25"),

            )

        val user = User("MariKong", "123", "kowalski", "none", 23, matchHistory)

        sharedViewModel.setUserToDisplay(user)
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

        if (sharedViewModel.displayUser!!.quizMatchHistory.isNullOrEmpty()){
            for (index in 0 until percentages.size){
                returningList.add("0%")
            }
        } else{
            //Iterate over the user match history

            for (match in sharedViewModel.displayUser!!.quizMatchHistory!!){
                when(match.category){
                    "action" -> percentages[0]++
                    "science fiction" -> percentages[1]++
                    "animation" -> percentages[2]++
                    "comedy" -> percentages[3]++
                    "horror" -> percentages[4]++
                    "drama" -> percentages[5]++
                }
            }

            for (index in 0 until percentages.size){
                percentages[index] = percentages[index] * 100 / sharedViewModel.displayUser!!.quizMatchHistory!!.size
                returningList.add(percentages[index].toString() + "%")
            }
        }


        return returningList
    }
}