package com.example.eywa_android.Home

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.eywa_android.Quiz.QuestionsActivity
import com.example.eywa_android.R

class CategoryFragment : Fragment(), HomeActivity.mainPage {

    private var categoryToReturn: String = "Action"
    private var buttonSelected = 0

    private val sharedViewModel : HomeSharedViewModel by activityViewModels()

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

        val categoryButtons = arrayOf(
            requireView().findViewById<Button>(R.id.buttonAction),
            requireView().findViewById<Button>(R.id.buttonComedy),
            requireView().findViewById<Button>(R.id.buttonScienceFiction),
            requireView().findViewById<Button>(R.id.buttonHorror),
            requireView().findViewById<Button>(R.id.buttonAnimation),
            requireView().findViewById<Button>(R.id.buttonDrama)
        )


        sharedViewModel.category.observe(viewLifecycleOwner) { category ->
            when(category){
                "Action" -> buttonStyle(categoryButtons[0])
                "Comedy" -> buttonStyle(categoryButtons[1])
                "Science Fiction" -> buttonStyle(categoryButtons[2])
                "Horror" -> buttonStyle(categoryButtons[3])
                "Animation" -> buttonStyle(categoryButtons[4])
                "Drama" -> buttonStyle(categoryButtons[5])
            }
        }

        for (button in categoryButtons){
            button.setOnClickListener(){
                uncheckButton(categoryButtons[buttonSelected])
                buttonPressed(button)
                //buttonStyle(button)
            }
        }

        val btnPlay = requireView().findViewById<Button>(R.id.btnPlay)
        btnPlay.setOnClickListener(){

            val bundle = bundleOf(QuestionsActivity.Questions.CATEGORY to categoryToReturn)
            findNavController().navigate(R.id.action_categoryFragment_to_difficultyFragment, bundle)

        }



//        buttonSelected = 0
//        val categoryButtons = arrayOf(
//            requireView().findViewById<Button>(R.id.buttonAction),
//            requireView().findViewById<Button>(R.id.buttonComedy),
//            requireView().findViewById<Button>(R.id.buttonScienceFiction),
//            requireView().findViewById<Button>(R.id.buttonHorror),
//            requireView().findViewById<Button>(R.id.buttonAnimation),
//            requireView().findViewById<Button>(R.id.buttonDrama)
//        )
//
//        for (button in categoryButtons){
//            button.setOnClickListener(){
//                uncheckButton(categoryButtons[buttonSelected])
//                buttonStyle(button)
//            }
//        }
//
//        val btnPlay = requireView().findViewById<Button>(R.id.btnPlay)
//        btnPlay.setOnClickListener(){
//
//            val bundle = bundleOf(QuestionsActivity.Questions.CATEGORY to categoryToReturn)
//            findNavController().navigate(R.id.action_categoryFragment_to_difficultyFragment, bundle)
//
//        }

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

    private fun buttonPressed(button: Button){
        sharedViewModel.changeCategory(button.tag.toString())
    }

    private fun buttonStyle(button : Button){

        when (button.id){
            resources.getIdentifier("buttonAction", "id", requireActivity().packageName) -> {
                button.backgroundTintList = requireContext().getColorStateList(R.color.action)
                buttonSelected = 0
            }
            resources.getIdentifier("buttonComedy", "id", requireActivity().packageName) -> {
                button.backgroundTintList = requireContext().getColorStateList(R.color.comedy)
                buttonSelected = 1
            }
            resources.getIdentifier("buttonScienceFiction", "id", requireActivity().packageName) -> {
                button.backgroundTintList = requireContext().getColorStateList(R.color.sci_fi)
                buttonSelected = 2
            }
            resources.getIdentifier("buttonHorror", "id", requireActivity().packageName) -> {
                button.backgroundTintList = requireContext().getColorStateList(R.color.horror)
                buttonSelected = 3
            }
            resources.getIdentifier("buttonAnimation", "id", requireActivity().packageName) -> {
                button.backgroundTintList = requireContext().getColorStateList(R.color.animation)
                buttonSelected = 4
            }
            resources.getIdentifier("buttonDrama", "id", requireActivity().packageName) -> {
                button.backgroundTintList = requireContext().getColorStateList(R.color.drama)
                buttonSelected = 5
            }
        }
        button.setTextColor(Color.parseColor("#FFFFFF"))

    }

}