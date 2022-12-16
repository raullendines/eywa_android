package com.example.eywa_android.Home

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.eywa_android.Quiz.QuestionsActivity
import com.example.eywa_android.R
import com.example.eywa_android.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment(), HomeActivity.mainPage {

    private var categoryToReturn: String = "Action"
    private var buttonSelected = 0

    private var _binding : FragmentCategoryBinding? = null
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

        _binding = FragmentCategoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        buttonSelected = 0
        val categoryButtons = arrayOf(
            binding.buttonAction,
            binding.buttonComedy,
            binding.buttonScienceFiction,
            binding.buttonHorror,
            binding.buttonAnimation,
            binding.buttonDrama
        )

        val categoryText = arrayOf(
            binding.textAction,
            binding.textComedy,
            binding.textScienceFiction,
            binding.textHorror,
            binding.textAnimation,
            binding.textDrama
        )

        binding.buttonGoBack.setOnClickListener() {
            findNavController().popBackStack()
        }


        sharedViewModel.category.observe(viewLifecycleOwner) { category ->
            when(category){
                "Action" -> buttonStyle(categoryButtons[0], categoryText[0])
                "Comedy" -> buttonStyle(categoryButtons[1], categoryText[1])
                "Science Fiction" -> buttonStyle(categoryButtons[2], categoryText[2])
                "Horror" -> buttonStyle(categoryButtons[3], categoryText[3])
                "Animation" -> buttonStyle(categoryButtons[4], categoryText[4])
                "Drama" -> buttonStyle(categoryButtons[5], categoryText[5])
            }
        }

        for (button in categoryButtons){
            button.setOnClickListener(){
                uncheckButton(categoryButtons[buttonSelected], categoryText[buttonSelected])
                buttonPressed(button)
                //buttonStyle(button)

            }
        }

        val btnPlay = requireView().findViewById<Button>(R.id.btnPlay)
        btnPlay.setOnClickListener(){
            findNavController().navigate(R.id.action_categoryFragment_to_difficultyFragment)
        }

    }

    override fun changeLang() {


        val categoryText = arrayOf(
            binding.textAction,
            binding.textComedy,
            binding.textScienceFiction,
            binding.textHorror,
            binding.textAnimation,
            binding.textDrama
        )

        binding.txtCategories.setText(R.string.category_title)
        categoryText[0].setText(R.string.category_action)
        categoryText[1].setText(R.string.category_comedy)
        categoryText[2].setText(R.string.category_scifi)
        categoryText[3].setText(R.string.category_horror)
        categoryText[4].setText(R.string.category_animation)
        categoryText[5].setText(R.string.category_drama)


        binding.btnPlay.setText(R.string.btnPlay)

    }

    private fun uncheckButton(button : LinearLayout, text : TextView){
        button.backgroundTintList = requireContext().getColorStateList(R.color.white)
        text.setTextColor(requireContext().getColor(R.color.purple_eywa))
    }


    private fun buttonPressed(button: LinearLayout){
        sharedViewModel.changeCategory(button.tag.toString())
    }


    private fun buttonStyle(button : LinearLayout, text : TextView){


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
        text.setTextColor(Color.parseColor("#FFFFFF"))

    }

}