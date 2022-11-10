package com.example.eywa_android

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.findFragment
import java.nio.file.Files
import java.util.*

private const val SCORE = "SCORE"
class CharacterFragment : Fragment() {

    private var correctAnswers: String? = null
    private var characterList : MutableList<Characters> = mutableListOf()
    private var category : String? = null
    private var difficulty : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character, container, false)
    }

    override fun onStart() {
        super.onStart()
        characterList = FilesManager.getCharacters(requireContext())
        correctAnswers = arguments?.getString(QuestionsActivity.Questions.SCORE)
        category = arguments?.getString(QuestionsActivity.Questions.CATEGORY)
        difficulty = arguments?.getString(QuestionsActivity.Questions.DIFFICULTY)

        var characterToShow : Characters? = null
        var founded = false
        var count = 0
        do{
            if (characterList[count].category == category
                //&& characterList[count].difficulty == difficulty
                && characterList[count].num_correct == correctAnswers){
                founded = true
            }
            count++
        } while(count<characterList.size && !founded)

//        for (character in characterList){
//            if(character.category == category && character.difficulty == "E" && difficulty == "1"
//                && character.num_correct == correctAnswers){
//                characterToShow = character
//            }
//        }

        val txtViewScore = requireView().findViewById<TextView>(R.id.txtViewScore)
        val imageCharacter = requireView().findViewById<ImageView>(R.id.imageCharacter)
        val txtNameCharacter = requireView().findViewById<TextView>(R.id.txtNameCharacter)
        val txtFilmCharacter = requireView().findViewById<TextView>(R.id.txtFilmCharacter)
        val txtDescriptionCharacter = requireView().findViewById<TextView>(R.id.txtDescriptionCharacter)

        var score = "$correctAnswers/10"
        txtViewScore.setText(score)
        val imagePath = requireContext().filesDir.path.toString() + "/img/" + characterToShow!!.image + ".jpeg"
        val bitmap = BitmapFactory.decodeFile(imagePath)
        imageCharacter.setImageBitmap(bitmap)
        txtNameCharacter.setText(characterToShow!!.name)
        txtFilmCharacter.setText(characterToShow!!.film)
        //FALTA IDIOMA
        var locale : Locale? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            locale = resources.configuration.locales.get(0);
        } else{
            //noinspection deprecation
            locale = resources.configuration.locale
        }
        var localeLang = locale!!.language

        when(localeLang){
            "ca" ->  txtDescriptionCharacter.setText(characterToShow!!.description_cat)
            "es" ->  txtDescriptionCharacter.setText(characterToShow!!.description_esp)
            "en" ->  txtDescriptionCharacter.setText(characterToShow!!.description_eng)
        }



        val btnPlay = requireView().findViewById<Button>(R.id.btnPlay)
        btnPlay.setOnClickListener(){
            val myActivity = this.activity as QuestionsActivity
            myActivity.finishActivity()
        }

    }
}