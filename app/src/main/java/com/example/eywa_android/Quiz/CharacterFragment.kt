package com.example.eywa_android.Quiz

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.eywa_android.ClassObject.Characters
import com.example.eywa_android.ClassObject.QuizAchievement
import com.example.eywa_android.ClassObject.QuizMatch
import com.example.eywa_android.Home.HomeActivity
import com.example.eywa_android.R
import com.example.eywa_android.Utility.FilesManager

import com.example.eywa_android.databinding.FragmentCharacterBinding
import kotlinx.android.synthetic.main.fragment_character.*
import java.util.*

class CharacterFragment : Fragment() {


    private var _binding : FragmentCharacterBinding? = null
    private val binding get() = _binding!!

    private var characterList : MutableList<Characters> = mutableListOf()
    private val sharedViewModel : QuizSharedViewModel by activityViewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)




//        binding.btnPlay.setOnClickListener(){
//            val myActivity = this.activity as QuestionsActivity
//            myActivity.finishActivity()
//        }

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
        sharedViewModel.achievementList.clear()
        val difficultyMultiplier : Int = when(sharedViewModel.difficulty){
            "1" -> 10
            "2" -> 15
            "3" -> 20
            "4" -> 25
            else -> 10
        }

        if (sharedViewModel.timeUsed == 0){
            sharedViewModel.addTime()
        }

        val diffPoints = (sharedViewModel.correctAnswers * difficultyMultiplier) / 10
        val quizScore : Int = (diffPoints * 1000) / sharedViewModel.timeUsed

        sharedViewModel.points = quizScore
        val match = QuizMatch(
            userId = sharedViewModel.user.id,
            category = sharedViewModel.category,
            time = sharedViewModel.timeUsed,
            difficulty = sharedViewModel.difficulty.toInt(),
            points = quizScore.toString())

        //get match list
        val quizMatches = FilesManager.getMatches(requireContext())
        //add new match
        quizMatches.add(match)
        //save new match list
        FilesManager.saveMatches(requireContext(), quizMatches)

        sharedViewModel.user.quizAchievementList = checkAchievement(sharedViewModel.user.quizAchievementList, match)

        if(sharedViewModel.achievementList.size > 0){
            for (index in sharedViewModel.achievementList.indices){
                achievementAnimation.clearAnimation()
                achievementAnimation.setAnimation(R.raw.animation_achievement)
                txtAchievementDescription.text = sharedViewModel.achievementList[index].title
            }
        }
        else {
            txtAchievementUnlocked.text = "NO HAS DESBLOQUEADO NINGÚN LOGRO"
        }
        val testUser = sharedViewModel.user


        val newUser = sharedViewModel.user

        val userList = FilesManager.getUsers(requireContext())
        var index : Int = -1
        for (user in userList){
            if (user.username == sharedViewModel.user.username){
                index = userList.indexOf(user)
            }
        }
        userList[index] = sharedViewModel.user
        FilesManager.saveUser(requireContext(), userList)



        characterList = FilesManager.getCharacters(requireContext())
        val characterToShow = findCharacter(characterList)

        if(characterToShow != null){
            var score = quizScore.toString()
            val imagePath = requireContext().filesDir.path.toString() + "/img/" + characterToShow.image + ".jpeg"
            val bitmap = BitmapFactory.decodeFile(imagePath)
            binding.imageCharacter.setImageBitmap(bitmap)
            binding.txtNameCharacter.setText(characterToShow.name)
            binding.txtFilmCharacter.setText(characterToShow.film)
            //IDIOMA
            var locale : Locale? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                locale = resources.configuration.locales.get(0)
            } else{
                //noinspection deprecation
                locale = resources.configuration.locale
            }
            var localeLang = locale!!.language

            when(localeLang){
                "ca" ->  binding.txtDescriptionCharacter.setText(characterToShow.description_cat)
                "es" ->  binding.txtDescriptionCharacter.setText(characterToShow.description_esp)
                "en" ->  binding.txtDescriptionCharacter.setText(characterToShow.description_eng)
            }
        } else {
            Toast.makeText(requireContext(), "PROBLEMAS", Toast.LENGTH_LONG).show()
        }

        binding.btnPlay.setOnClickListener(){
//            viewModelStore.clear()
//            val myActivity = this.activity as QuestionsActivity
//            if (sharedViewModel.hasAchievementUnlocked){
//                val returnNewUserIntent = Intent(this.activity, HomeActivity::class.java)
//                returnNewUserIntent.putExtra(QuestionsActivity.Questions.USER, sharedViewModel.user)
//                myActivity.setResult(RESULT_OK, returnNewUserIntent)w
//            } else{
//                myActivity.setResult(RESULT_CANCELED)
//            }
//            myActivity.finishActivity()

            findNavController().navigate(R.id.action_characterFragment_to_scoreFragment)
        }

    }

    fun checkAchievement(list : MutableList<QuizAchievement>, match : QuizMatch) : MutableList<QuizAchievement>{
        var listToReturn = list
        when(match.category){
            "action" -> {
                listToReturn = checkPointAchievement(100, match, list, 2)
                listToReturn = checkPointAchievement(250, match, list, 8)
            }
            "science fiction" -> {
                listToReturn = checkPointAchievement(100, match, list, 3)
                listToReturn = checkPointAchievement(250, match, list, 9)
            }
            "animation" -> {
                listToReturn = checkPointAchievement(100, match, list, 4)
                listToReturn = checkPointAchievement(250, match, list, 10)
            }
            "comedy" -> {
                listToReturn = checkPointAchievement(100, match, list, 5)
                listToReturn = checkPointAchievement(250, match, list, 11)
            }
            "horror" -> {
                listToReturn = checkPointAchievement(100, match, list, 6)
                listToReturn = checkPointAchievement(250, match, list, 12)
            }
            "drama" -> {
                listToReturn = checkPointAchievement(100, match, list, 7)
                listToReturn = checkPointAchievement(250, match, list, 13)
            }
        }

        return listToReturn
    }

    private fun checkPointAchievement(points : Int, match: QuizMatch, list: MutableList<QuizAchievement>, achievementId : Int) : MutableList<QuizAchievement> {

        if (!list[achievementId].owned){
            if (match.points.toInt() > points){
                sharedViewModel.achievementList.add(list[achievementId])
                list[achievementId].owned = true
                Toast.makeText(requireContext(), "Achievement $achievementId unlocked", Toast.LENGTH_SHORT).show()
                sharedViewModel.achievementUnlocked()
            }
        }
        return list
    }
}