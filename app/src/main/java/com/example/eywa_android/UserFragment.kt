package com.example.eywa_android

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.eywa_android.Adapters.CategoryPercentageAdapter
import com.example.eywa_android.ClassObject.QuizMatch
import com.example.eywa_android.ClassObject.User
import com.example.eywa_android.Home.HomeSharedViewModel
import com.example.eywa_android.databinding.FragmentUserBinding


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
        val isTheUserProfile : Boolean = arguments?.get("Boolean") as Boolean

        if (isTheUserProfile){
            binding.buttonEditData.visibility = View.INVISIBLE
        }
        */

        initiateTestUser()

        initUser()

        var percentageAdapter = getPercentages()

        val categoryPercentageAdapter = CategoryPercentageAdapter(requireContext(), percentageAdapter)

        binding.listCategories.layoutManager = GridLayoutManager(requireContext(), 3)

        binding.listCategories.adapter = categoryPercentageAdapter

        binding.buttonEditData.setOnClickListener(){
            //Navigation to the other fragment
            findNavController().navigate(R.id.action_userFragment_to_editUserFragment)
        }
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
        //Iterate over the user match history

        for (match in sharedViewModel.displayUser!!.quizMatchHistory){
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
            percentages[index] = percentages[index] * 100 / sharedViewModel.displayUser!!.quizMatchHistory.size
            returningList.add(percentages[index].toString() + "%")
        }

        return returningList
    }
}