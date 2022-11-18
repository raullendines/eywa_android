package com.example.eywa_android.Quiz

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
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.eywa_android.ClassObject.Characters
import com.example.eywa_android.ClassObject.QuizMatch
import com.example.eywa_android.Management.FilesManager
import com.example.eywa_android.R
import com.example.eywa_android.databinding.FragmentCharacterBinding
import com.example.eywa_android.databinding.FragmentQuestionsBinding
import java.util.*

class CharacterFragment : Fragment() {


    private var _binding : FragmentCharacterBinding? = null
    private val binding get() = _binding!!

    private var characterList : MutableList<Characters> = mutableListOf()
    private val sharedViewModel : QuizSharedViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)


        val diffPoints = sharedViewModel.correctAnswers * sharedViewModel.difficulty.toInt()
        val quizScore : Int = (diffPoints * 100) / sharedViewModel.timeUsed

        val match = QuizMatch(sharedViewModel.category, sharedViewModel.timeUsed,
        sharedViewModel.difficulty.toInt(), quizScore.toString())



        characterList = FilesManager.getCharacters(requireContext())
        val characterToShow = findCharacter(characterList)

        if(characterToShow != null){
            var score = "$sharedViewModel.correctAnswers/10"
            binding.txtViewScore.setText(score)
            val imagePath = requireContext().filesDir.path.toString() + "/img/" + characterToShow!!.image + ".jpeg"
            val bitmap = BitmapFactory.decodeFile(imagePath)
            binding.imageCharacter.setImageBitmap(bitmap)
            binding.txtNameCharacter.setText(characterToShow!!.name)
            binding.txtFilmCharacter.setText(characterToShow!!.film)
            //IDIOMA
            var locale : Locale? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                locale = resources.configuration.locales.get(0);
            } else{
                //noinspection deprecation
                locale = resources.configuration.locale
            }
            var localeLang = locale!!.language

            when(localeLang){
                "ca" ->  binding.txtDescriptionCharacter.setText(characterToShow!!.description_cat)
                "es" ->  binding.txtDescriptionCharacter.setText(characterToShow!!.description_esp)
                "en" ->  binding.txtDescriptionCharacter.setText(characterToShow!!.description_eng)
            }
        } else {
            Toast.makeText(requireContext(), "PROBLEMAS", Toast.LENGTH_LONG).show()
        }


        binding.btnPlay.setOnClickListener(){
            val myActivity = this.activity as QuestionsActivity
            myActivity.finishActivity()
        }

        return binding.root
    }

    private fun findCharacter(characterList : MutableList<Characters>) : Characters?{
        var characterToReturn : Characters? = null
        var founded = false
        var count = 0
        do{
            if (characterList[count].category == sharedViewModel.category
                //&& characterList[count].difficulty == sharedViewModel.difficulty.toString()
                && characterList[count].num_correct == sharedViewModel.correctAnswers.toString()){
                characterToReturn=characterList[count]
                founded = true
            }
            count++
        } while(count<characterList.size && !founded)

        return characterToReturn
    }

    override fun onStart() {
        super.onStart()
        characterList = FilesManager.getCharacters(requireContext())


        var characterToShow : Characters? = null
        var founded = false
        var count = 0
        do{
            if (characterList[count].category == sharedViewModel.category
                //&& characterList[count].difficulty == sharedViewModel.difficulty.toString()
                && characterList[count].num_correct == sharedViewModel.correctAnswers.toString()){
                characterToShow=characterList[count]
                founded = true


            }
            count++
        } while(count<characterList.size && !founded)


        if(characterToShow != null){
            val score = sharedViewModel.correctAnswers.toString()
            val scoreText = "$score/10"
            binding.txtViewScore.setText(scoreText)
            val imagePath = requireContext().filesDir.path.toString() + "/img/" + characterToShow!!.image + ".jpeg"
            val bitmap = BitmapFactory.decodeFile(imagePath)
            binding.imageCharacter.setImageBitmap(bitmap)
            binding.txtNameCharacter.setText(characterToShow!!.name)
            binding.txtFilmCharacter.setText(characterToShow!!.film)

            //We search the current lang of the app
            var locale : Locale? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                locale = resources.configuration.locales.get(0);
            } else{
                //noinspection deprecation
                locale = resources.configuration.locale
            }
            var localeLang = locale!!.language

            when(localeLang){
                "ca" ->  binding.txtDescriptionCharacter.setText(characterToShow!!.description_cat)
                "es" ->  binding.txtDescriptionCharacter.setText(characterToShow!!.description_esp)
                "en" ->  binding.txtDescriptionCharacter.setText(characterToShow!!.description_eng)
            }
        } else {
            Toast.makeText(requireContext(), "PROBLEMAS", Toast.LENGTH_LONG).show()
        }

        binding.btnPlay.setOnClickListener(){
            val myActivity = this.activity as QuestionsActivity
            myActivity.finishActivity()
        }

    }
}