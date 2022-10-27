package com.example.eywa_android

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController


private const val CATEGORY = "categorySelected"

class CategoryFragment : Fragment(), Home.mainPage {

    private var categoryToReturn: String? = null
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

        val myActivity : Home = activity as Home
        changeLang(myActivity.lang)

        val btnPlay = requireView().findViewById<Button>(R.id.btnPlay)
        btnPlay.setOnClickListener(){

            val bundle = bundleOf(CATEGORY to categoryToReturn)
            findNavController().navigate(R.id.action_categoryFragment_to_difficultyFragment, bundle)

        }

    }

    override fun changeLang(lang: String) {
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

        when(lang){
            "cat" -> {
                txtViewCategory.setText("CATEGORIES")
                categoryButtons[0].setText("ACCIÓ")
                categoryButtons[1].setText("COMEDIA")
                categoryButtons[2].setText("CIENCIA FICCIÓ")
                categoryButtons[3].setText("TERROR")
                categoryButtons[4].setText("ANIMACIÓ")
                categoryButtons[5].setText("DRAMA")

                playButton.setText("JUGAR")
            }
            "esp" -> {
                txtViewCategory.setText("CATEGORIAS")
                categoryButtons[0].setText("ACCIÓN")
                categoryButtons[1].setText("COMEDIA")
                categoryButtons[2].setText("CIENCIA FICCIÓN")
                categoryButtons[3].setText("TERROR")
                categoryButtons[4].setText("ANIMACIÓN")
                categoryButtons[5].setText("DRAMA")
                playButton.setText("JUGAR")
            }
            "eng" -> {
                txtViewCategory.setText("CATEGORIES")
                categoryButtons[0].setText("ACTION")
                categoryButtons[1].setText("COMEDY")
                categoryButtons[2].setText("SCIENCE FICTION")
                categoryButtons[3].setText("HORROR")
                categoryButtons[4].setText("ANIMATION")
                categoryButtons[5].setText("DRAMA")
                playButton.setText("PLAY")
            }
        }
    }

    private fun uncheckButton(button : Button){
        button.setBackgroundResource(R.drawable.rounded_corners_white)
        button.setTextColor(Color.parseColor("#3d0066"))
    }


    private fun buttonStyle(button : Button){

        when (button.id){
            resources.getIdentifier("buttonAction", "id", requireActivity().packageName) -> {
                button.setBackgroundResource(R.drawable.rounded_corners_red)
                buttonSelected = 0
                categoryToReturn = "Action"
            }
            resources.getIdentifier("buttonComedy", "id", requireActivity().packageName) -> {
                button.setBackgroundResource(R.drawable.rounded_corners_yellow)
                buttonSelected = 1
                categoryToReturn = "Comedy"
            }
            resources.getIdentifier("buttonScienceFiction", "id", requireActivity().packageName) -> {
                button.setBackgroundResource(R.drawable.rounded_corners_red)
                buttonSelected = 2
                categoryToReturn = "Science Fiction"
            }
            resources.getIdentifier("buttonHorror", "id", requireActivity().packageName) -> {
                button.setBackgroundResource(R.drawable.rounded_corners_yellow)
                buttonSelected = 3
                categoryToReturn = "Horror"
            }
            resources.getIdentifier("buttonAnimation", "id", requireActivity().packageName) -> {
                button.setBackgroundResource(R.drawable.rounded_corners_red)
                buttonSelected = 4
                categoryToReturn = "Animation"
            }
            resources.getIdentifier("buttonDrama", "id", requireActivity().packageName) -> {
                button.setBackgroundResource(R.drawable.rounded_corners_yellow)
                buttonSelected = 5
                categoryToReturn = "Drama"
            }
        }
        button.setTextColor(Color.parseColor("#FFFFFF"))

    }
}