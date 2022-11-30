package com.example.eywa_android.Quiz

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
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
            points = quizScore.toString(),
            correctAnswers = sharedViewModel.correctAnswers,
            incorrectAnswers = sharedViewModel.incorrectAnswers)

        //get match list
        val quizMatches = FilesManager.getMatches(requireContext())
        //add new match
        quizMatches.add(match)
        //save new match list
        FilesManager.saveMatches(requireContext(), quizMatches)

        sharedViewModel.user.quizAchievementList = checkAchievement(sharedViewModel.user.quizAchievementList, match)

        sharedViewModel.sortAchievementList()

        val listAchievements = FilesManager.getAchievements(requireContext())

        sharedViewModel.achievementMatch.clear()
        if(sharedViewModel.achievementList.size > 0){
            for (index in sharedViewModel.achievementList.indices){
                for (achievement in listAchievements){
                    if(achievement.id  == sharedViewModel.achievementList[index]){
                        println(achievement.title)
                        sharedViewModel.achievementMatch.add(achievement)
                    }
                }

            }
        }
        else {
            txtAchievementUnlocked.text = "NO HAS DESBLOQUEADO NINGÚN LOGRO"
        }

        val animDurationConst : Long = 2L

        val fullDuration : Long = animDurationConst * sharedViewModel.achievementMatch.size * 1000
        val animDuration : Long = animDurationConst * 1000

        var indexAnim = 0
        val animationTimer = object : CountDownTimer(
            fullDuration,
            animDuration){
            override fun onTick(p0: Long) {
                achievementAnimation.cancelAnimation()
                achievementAnimation.playAnimation()
                txtAchievementDescription.text = sharedViewModel.achievementMatch[indexAnim].title
                indexAnim += 1
            }

            override fun onFinish() {

            }
        }

        if(sharedViewModel.achievementMatch.size > 0){
            animationTimer.start()
        }



//        if(sharedViewModel.achievementMatch.size > 0){
//            for(achievement in sharedViewModel.achievementMatch){
//                achievementAnimation.playAnimation()
//                txtAchievementDescription.text = achievement.title
//            }
//
//
//        }



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

    fun checkAchievement(list : MutableList<Int>, match : QuizMatch) : MutableList<Int>{
        var listToReturn = list
        listToReturn = checkPlayAchievements(listToReturn, match)
        listToReturn = checkPointsAchievements(listToReturn, match)


        return listToReturn
    }

    private fun checkPointsAchievements(list : MutableList<Int>, match : QuizMatch) : MutableList<Int> {
        var listToReturn = list
        when(match.category){
            "action" -> {
                listToReturn = checkPointAchievement(100, match, list, 7)
                listToReturn = checkPointAchievement(250, match, list, 13)
            }
            "science fiction" -> {
                listToReturn = checkPointAchievement(100, match, list, 8)
                listToReturn = checkPointAchievement(250, match, list, 14)
            }
            "animation" -> {
                listToReturn = checkPointAchievement(100, match, list, 9)
                listToReturn = checkPointAchievement(250, match, list, 15)
            }
            "comedy" -> {
                listToReturn = checkPointAchievement(100, match, list, 10)
                listToReturn = checkPointAchievement(250, match, list, 16)
            }
            "horror" -> {
                listToReturn = checkPointAchievement(100, match, list, 11)
                listToReturn = checkPointAchievement(250, match, list, 17)
            }
            "drama" -> {
                listToReturn = checkPointAchievement(100, match, list, 12)
                listToReturn = checkPointAchievement(250, match, list, 18)
            }
        }
        checkPointAchievement(300, match, list, 19)
        checkPointAchievement(600, match, list, 20)
        return listToReturn
    }

    private fun checkPlayAchievements(list : MutableList<Int>, match : QuizMatch) : MutableList<Int> {
        var listToReturn = list
        when(match.category){
            "action" -> listToReturn = checkPlayedCategories(list, 1)
            "science fiction" -> listToReturn = checkPlayedCategories(list, 2)
            "animation" -> listToReturn = checkPlayedCategories(list, 3)
            "comedy" -> listToReturn = checkPlayedCategories(list, 4)
            "horror" -> listToReturn = checkPlayedCategories(list, 5)
            "drama" -> listToReturn = checkPlayedCategories(list, 6)
        }
        listToReturn = checkPlayedCategories(list, 0)

        return listToReturn
    }

    private fun checkPlayedCategories(list: MutableList<Int>, achievementId: Int): MutableList<Int>{
        if (!list.contains(achievementId)){
            sharedViewModel.achievementList.add(achievementId)
            list.add(achievementId)
            Toast.makeText(requireContext(), "Achievement $achievementId unlocked", Toast.LENGTH_SHORT).show()
            sharedViewModel.achievementUnlocked()
        }
        return list
    }


    private fun checkPointAchievement(points : Int, match: QuizMatch, list: MutableList<Int>, achievementId : Int) : MutableList<Int> {

        if (!list.contains(achievementId)){
            if (match.points.toInt() > points){
                sharedViewModel.achievementList.add(achievementId)
                list.add(achievementId)
                Toast.makeText(requireContext(), "Achievement $achievementId unlocked", Toast.LENGTH_SHORT).show()
                sharedViewModel.achievementUnlocked()
            }
        }
        return list
    }
}