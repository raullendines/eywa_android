package com.example.eywa_android

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController

class CategoryFragment : Fragment(), Home.mainPage{

    private var categoryToReturn: String = "Action"
    private var buttonSelected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onStart() {
        super.onStart()
        buttonSelected = 0
        val categoryButtons = arrayOf(
            requireView().findViewById<Button>(R.id.buttonAction),
            requireView().findViewById<Button>(R.id.buttonComedy),
            requireView().findViewById<Button>(R.id.buttonScienceFiction),
            requireView().findViewById<Button>(R.id.buttonHorror),
            requireView().findViewById<Button>(R.id.buttonAnimation),
            requireView().findViewById<Button>(R.id.buttonDrama)
        )

        for (button in categoryButtons){
            button.setOnClickListener(){
                uncheckButton(categoryButtons[buttonSelected])
                buttonStyle(button)
            }
        }

        val btnPlay = requireView().findViewById<Button>(R.id.btnPlay)
        btnPlay.setOnClickListener(){

            val bundle = bundleOf(QuestionsActivity.Questions.CATEGORY to categoryToReturn)
            findNavController().navigate(R.id.action_categoryFragment_to_difficultyFragment, bundle)

        }

    }

    override fun changeLang() {

        val categoryButtons = arrayOf(
            requireView().findViewById<Button>(R.id.buttonAction),
            requireView().findViewById<Button>(R.id.buttonComedy),
            requireView().findViewById<Button>(R.id.buttonScienceFiction),
            requireView().findViewById<Button>(R.id.buttonHorror),
            requireView().findViewById<Button>(R.id.buttonAnimation),
            requireView().findViewById<Button>(R.id.buttonDrama)
        )

        val txtViewCategory = requireView().findViewById<TextView>(R.id.txtCategories)
        val playButton = requireView().findViewById<Button>(R.id.btnPlay)

        txtViewCategory.setText(R.string.category_title)
        categoryButtons[0].setText(R.string.category_action)
        categoryButtons[1].setText(R.string.category_comedy)
        categoryButtons[2].setText(R.string.category_scifi)
        categoryButtons[3].setText(R.string.category_horror)
        categoryButtons[4].setText(R.string.category_animation)
        categoryButtons[5].setText(R.string.category_drama)

        playButton.setText(R.string.btnPlay)

    }

    private fun uncheckButton(button : Button){
        button.backgroundTintList = requireContext().getColorStateList(R.color.white)
        button.setTextColor(requireContext().getColor(R.color.purple_eywa))
    }


    private fun buttonStyle(button : Button){


        when (button.id){
            resources.getIdentifier("buttonAction", "id", requireActivity().packageName) -> {
                button.backgroundTintList = requireContext().getColorStateList(R.color.action)
                buttonSelected = 0
                categoryToReturn = "Action"

            }
            resources.getIdentifier("buttonComedy", "id", requireActivity().packageName) -> {
                button.backgroundTintList = requireContext().getColorStateList(R.color.comedy)
                buttonSelected = 1
                categoryToReturn = "Comedy"
            }
            resources.getIdentifier("buttonScienceFiction", "id", requireActivity().packageName) -> {
                button.backgroundTintList = requireContext().getColorStateList(R.color.sci_fi)
                buttonSelected = 2
                categoryToReturn = "Science Fiction"
            }
            resources.getIdentifier("buttonHorror", "id", requireActivity().packageName) -> {
                button.backgroundTintList = requireContext().getColorStateList(R.color.horror)
                buttonSelected = 3
                categoryToReturn = "Horror"
            }
            resources.getIdentifier("buttonAnimation", "id", requireActivity().packageName) -> {
                button.backgroundTintList = requireContext().getColorStateList(R.color.animation)
                buttonSelected = 4
                categoryToReturn = "Animation"
            }
            resources.getIdentifier("buttonDrama", "id", requireActivity().packageName) -> {
                button.backgroundTintList = requireContext().getColorStateList(R.color.drama)
                buttonSelected = 5
                categoryToReturn = "Drama"
            }
        }
        button.setTextColor(Color.parseColor("#FFFFFF"))

    }

}