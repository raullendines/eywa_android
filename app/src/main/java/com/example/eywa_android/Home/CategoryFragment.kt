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
import com.example.eywa_android.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment(), HomeActivity.mainPage {


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

        val categoryButtons = arrayOf(
            binding.buttonAction,
            binding.buttonComedy,
            binding.buttonScienceFiction,
            binding.buttonHorror,
            binding.buttonAnimation,
            binding.buttonDrama
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

        binding.btnPlay.setOnClickListener(){
            findNavController().navigate(R.id.action_categoryFragment_to_difficultyFragment)
        }

    }

    override fun changeLang() {

        val categoryButtons = arrayOf(
            binding.buttonAction,
            binding.buttonComedy,
            binding.buttonScienceFiction,
            binding.buttonHorror,
            binding.buttonAnimation,
            binding.buttonDrama
        )

        binding.txtCategories.setText(R.string.category_title)
        categoryButtons[0].setText(R.string.category_action)
        categoryButtons[1].setText(R.string.category_comedy)
        categoryButtons[2].setText(R.string.category_scifi)
        categoryButtons[3].setText(R.string.category_horror)
        categoryButtons[4].setText(R.string.category_animation)
        categoryButtons[5].setText(R.string.category_drama)

        binding.btnPlay.setText(R.string.btnPlay)

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