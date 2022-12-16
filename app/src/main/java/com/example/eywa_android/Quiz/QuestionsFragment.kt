package com.example.eywa_android.Quiz

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.provider.MediaStore.Audio.Media
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.eywa_android.ClassObject.Question
import com.example.eywa_android.R
import com.example.eywa_android.databinding.ActivityHomeBinding
import com.example.eywa_android.databinding.FragmentQuestionsBinding
import kotlin.collections.ArrayList


private const val SCORE = "SCORE"


class QuestionsFragment : Fragment() {

    private var _binding : FragmentQuestionsBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel : QuizSharedViewModel by activityViewModels()
    private var correctMusic: MediaPlayer? = null
    private var incorrectMusic: MediaPlayer? = null

    private lateinit var contador_timer: CountDownTimer
    private lateinit var optional_timer : CountDownTimer
    private var hasBeenPaused = false
    private var bothTimers = false
    var correcto = true
    var correct_answer = true
    var maxAnswers = 2
    private fun getObject() = object {
        val selected: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.answer_button_selected, null)
        val unselected: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.answer_button, null)
        val correct_button: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.answer_button_correct, null)
        val incorrect_button: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.answer_button_error, null)
        val current_circle: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.circle_current, null)
        val correct_circle: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.circle_ok, null)
        val incorrect_circle: Drawable? =
            ResourcesCompat.getDrawable(resources, R.drawable.circle_bad, null)
    }




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
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        //_bindingMenu = ActivityHomeBinding.inflate(inflater, container, false)

        correctMusic = MediaPlayer.create(context, R.raw.music_correct)
        incorrectMusic = MediaPlayer.create(context, R.raw.music_incorrect)

        return binding.root
    }



    override fun onStart() {
        super.onStart()

        //We get the question list from the sharedViewModel
        //The questions are already initialized and mixed

        //Change the header color and the title of the diferent categories
        changeHeaderBackground(sharedViewModel.category)

        //Loading the first question
        if (!hasBeenPaused){
            randomQuestion(sharedViewModel.questions)
        }


        //Buttons Click Listeners
        answersButtonsClickListeners()

        if (!hasBeenPaused){
            contador_timer = object : CountDownTimer( 15000, 1000) {

                override fun onTick(millisUntilFinished: Long) {
                    //adding the time to a variable to set the points of the game
                    sharedViewModel.addTime()
                    //controling the time in case the app stops
                    sharedViewModel.timeLeft = millisUntilFinished / 1000
                    sharedViewModel.timeLeft++
                    //setting the UI timer
                    binding.txtTimer.text = sharedViewModel.timeLeft.toString()
                }

                //If the user didn't answer before the 15sec countdown, the answer is set on incorrect
                override fun onFinish() {
                    disabled()
                    Handler().postDelayed({
                        binding.txtTimer.text = "0"
                        animationIncorrect(binding.animationWin)
                        disabled()
                        incorrect(binding.animationWin, sharedViewModel.questions, binding.defaultButton)
                        sharedViewModel.questionsRemoveLast()
                    }, 100)
                }
            }.start()


        } else{
            optional_timer = object : CountDownTimer( sharedViewModel.timeLeft * 1000, 1000) {

                override fun onTick(millisUntilFinished: Long) {
                    //adding the time to a variable to set the points of the game
                    sharedViewModel.addTime()
                    sharedViewModel.timeLeft = millisUntilFinished / 1000
                    sharedViewModel.timeLeft++
                    binding.txtTimer.text = sharedViewModel.timeLeft.toString()
                }

                //If the user didn't reponded before the 15sec countdown, the answer is set on incorrect
                override fun onFinish() {
                    disabled()
                    Handler().postDelayed({
                        binding.txtTimer.text = "0"
                        animationIncorrect(binding.animationWin)
                        disabled()
                        incorrect(binding.animationWin, sharedViewModel.questions, binding.defaultButton)
                        sharedViewModel.questionsRemoveLast()
                    }, 100)
                }
            }.start()

            bothTimers = true

        }

        hasBeenPaused = false

        //We set the timer of 15 seconds


    }



    private fun answersButtonsClickListeners(){

        binding.answer1.setOnClickListener(){
            stopBothTimers()
            if (binding.answer1.text == sharedViewModel.questions.last().correct_answer){
                correct(binding.animationWin, sharedViewModel.questions, binding.answer1)
            } else{
                incorrect(binding.animationWin, sharedViewModel.questions, binding.answer1)
            }
            disabled()
            sharedViewModel.questionsRemoveLast()
        }

        binding.answer2.setOnClickListener(){
            stopBothTimers()
            if (binding.answer2.text == sharedViewModel.questions.last().correct_answer){
                correct(binding.animationWin, sharedViewModel.questions, binding.answer2)
            } else{
                incorrect(binding.animationWin, sharedViewModel.questions, binding.answer2)
            }
            disabled()
            sharedViewModel.questionsRemoveLast()
        }

        binding.answer3.setOnClickListener(){
            stopBothTimers()
            if (binding.answer3.text == sharedViewModel.questions.last().correct_answer){
                correct(binding.animationWin, sharedViewModel.questions, binding.answer3)
            } else{
                incorrect(binding.animationWin, sharedViewModel.questions, binding.answer3)
            }
            disabled()
            sharedViewModel.questionsRemoveLast()
        }

        binding.answer4.setOnClickListener(){
            stopBothTimers()
            if (binding.answer4.text == sharedViewModel.questions.last().correct_answer){
                correct(binding.animationWin, sharedViewModel.questions, binding.answer4)
            } else{
                incorrect(binding.animationWin, sharedViewModel.questions, binding.answer4)
            }
            disabled()
            sharedViewModel.questionsRemoveLast()
        }
    }


//    override fun onPauseFragment() {
//        hasBeenPaused = true
//        if(sharedViewModel.timeLeft > 1){
//            stopBothTimers()
//        }
//    }

    override fun onStop() {
        super.onStop()
        hasBeenPaused = true
        if (sharedViewModel.timeLeft > 1){
            stopBothTimers()
        }
    }



    //    override fun onActionModeFinished(mode: ActionMode?) {
//        super.onActionModeFinished(mode)
//        contador_timer.cancel()
//    }

    private fun stopBothTimers(){
        contador_timer.cancel()
        if(bothTimers){
            optional_timer.cancel()
        }
    }

    private fun progress(){
        //sharedViewModel.questionDone()
        when (sharedViewModel.countQuestion + 1) {
            1 ->  binding.actual1.background = getObject().current_circle
            2 ->  binding.actual2.background = getObject().current_circle
            3 ->  binding.actual3.background = getObject().current_circle
            4 ->  binding.actual4.background = getObject().current_circle
            5 ->  binding.actual5.background = getObject().current_circle
            6 ->  binding.actual6.background = getObject().current_circle
            7 ->  binding.actual7.background = getObject().current_circle
            8 ->  binding.actual8.background = getObject().current_circle
            9 ->  binding.actual9.background = getObject().current_circle
            10 -> binding.actual10.background = getObject().current_circle
        }
    }

    fun progressCircles(){
        if (correct_answer){
            when (sharedViewModel.countQuestion) {
                1 ->  binding.actual1.background = getObject().correct_circle
                2 ->  binding.actual2.background = getObject().correct_circle
                3 ->  binding.actual3.background = getObject().correct_circle
                4 ->  binding.actual4.background = getObject().correct_circle
                5 ->  binding.actual5.background = getObject().correct_circle
                6 ->  binding.actual6.background = getObject().correct_circle
                7 ->  binding.actual7.background = getObject().correct_circle
                8 ->  binding.actual8.background = getObject().correct_circle
                9 ->  binding.actual9.background = getObject().correct_circle
                10 -> binding.actual10.background = getObject().correct_circle
            }
        }
        else{
            when (sharedViewModel.countQuestion) {
                1 ->  binding.actual1.background = getObject().incorrect_circle
                2 ->  binding.actual2.background = getObject().incorrect_circle
                3 ->  binding.actual3.background = getObject().incorrect_circle
                4 ->  binding.actual4.background = getObject().incorrect_circle
                5 ->  binding.actual5.background = getObject().incorrect_circle
                6 ->  binding.actual6.background = getObject().incorrect_circle
                7 ->  binding.actual7.background = getObject().incorrect_circle
                8 ->  binding.actual8.background = getObject().incorrect_circle
                9 ->  binding.actual9.background = getObject().incorrect_circle
                10 -> binding.actual10.background = getObject().incorrect_circle
            }
        }
    }

    fun randomQuestion(questions: MutableList<Question>){
        val randomQuestion = questions.last()

        val possibleAnswers = ArrayList<String>()

        progress()
        if (sharedViewModel.countQuestion != 0 ){
            progressCircles()
        }

//        if (possibleAnswers.size != 0){
//            possibleAnswers.clear()
//        }

        possibleAnswers.add(randomQuestion.correct_answer)
        possibleAnswers.add(randomQuestion.incorrect_answers[0])
        possibleAnswers.add(randomQuestion.incorrect_answers[1])
        possibleAnswers.add(randomQuestion.incorrect_answers[2])

        possibleAnswers.shuffle()

        sharedViewModel.initPossibleAnswers(possibleAnswers)

        for ((index, value) in possibleAnswers.withIndex()) {
            if (value.equals(questions.last().correct_answer)){
                sharedViewModel.changeCurrentQuestionCorrectAnswer(index)
            }
        }

        binding.txtQuestion.text = randomQuestion.question

        val answers = arrayOf(
            binding.answer1,
            binding.answer2,
            binding.answer3,
            binding.answer4
        )

        var index = 0
        //set text
        for(i in 0..3){
            answers[i].text = possibleAnswers[i]
        }
        //animations
        val anim_timer = object: CountDownTimer(500, 100) {
            override fun onTick(millisUntilFinished: Long) {
                if(index < 4) {
                    animate(answers[index])
                    index++
                }
            }
            override fun onFinish() {
            }
        }
        anim_timer.start()

    }


    private fun animate(v : View){

        val myAnim : Animation = AnimationUtils.loadAnimation(this.requireContext(),
            R.anim.scale_to_100
        )
        v.startAnimation(myAnim)

        val resizeAnim : Animation = AnimationUtils.loadAnimation(this.requireContext(),
            R.anim.rescale
        )

        resizeAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                v.scaleX = 1.0f
                v.scaleY = 1.0f
            }
        })


        val anim_timer = object: CountDownTimer(500, 100) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {

                v.startAnimation(resizeAnim)
            }
        }
        anim_timer.start()


    }

    //NEW QUESTION
    fun newQuestion(shuffle: MutableList<Question>){
        if (sharedViewModel.countQuestion == maxAnswers){
            endGame()
        }
        else{
            sharedViewModel.questionDone()
            object : CountDownTimer( 600, 1000) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    contador_timer.start()
                    enabled()
                    colorBtnRestart()
                    randomQuestion(shuffle)
                }
            }.start()
        }
    }

    fun disabled(){
        binding.answer1.isEnabled = false
        binding.answer2.isEnabled = false
        binding.answer3.isEnabled = false
        binding.answer4.isEnabled = false
    }

    fun enabled(){
        binding.answer1.isEnabled = true
        binding.answer2.isEnabled = true
        binding.answer3.isEnabled = true
        binding.answer4.isEnabled = true
    }

    fun colorBtnRestart(){
        binding.answer1.setTextColor(Color.parseColor("#000000"))
        binding.answer1.background = getObject().unselected

        binding.answer2.setTextColor(Color.parseColor("#000000"))
        binding.answer2.background = getObject().unselected

        binding.answer3.setTextColor(Color.parseColor("#000000"))
        binding.answer3.background = getObject().unselected

        binding.answer4.setTextColor(Color.parseColor("#000000"))
        binding.answer4.background = getObject().unselected
    }

    fun colorBtn(isCorrect: Boolean, myButton: Button, shuffle: MutableList<Question>) {
        if(isCorrect){
            myButton.setTextColor(Color.parseColor("#FFFFFF"))
            myButton.background = getObject().correct_button
        } else{
            myButton.setTextColor(Color.parseColor("#FFFFFF"))
            myButton.background = getObject().incorrect_button


            if (binding.answer1.text.equals(shuffle.last().correct_answer)){
                binding.answer1.setTextColor(Color.parseColor("#FFFFFF"))
                binding.answer1.background = getObject().correct_button
            }
            else if (binding.answer2.text.equals(shuffle.last().correct_answer)){
                binding.answer2.setTextColor(Color.parseColor("#FFFFFF"))
                binding.answer2.background = getObject().correct_button
            }
            else if (binding.answer3.text.equals(shuffle.last().correct_answer)){
                binding.answer3.setTextColor(Color.parseColor("#FFFFFF"))
                binding.answer3.background = getObject().correct_button
            }
            else{
                binding.answer4.setTextColor(Color.parseColor("#FFFFFF"))
                binding.answer4.background = getObject().correct_button
            }
        }
    }

    fun animationIncorrect(animation: LottieAnimationView){
        animation.setAnimation(R.raw.animation_incorrect)
        animation.visibility = View.VISIBLE
        animation.alpha = 1.0f
        animation.playAnimation()


        val animacion = AnimationUtils.loadAnimation(this.activity, R.anim.fade_out)


        object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                animation.startAnimation(animacion)
                Handler().postDelayed({
                    animation.visibility = View.INVISIBLE
                }, 100)
            }
        }.start()
    }

    fun animationCorrect(animation: LottieAnimationView){
        animation.setAnimation(R.raw.animation_correct)
        animation.visibility = View.VISIBLE
        animation.alpha = 1.0f
        animation.playAnimation()

        val animacion = AnimationUtils.loadAnimation(this.activity, R.anim.fade_out)


        object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                animation.startAnimation(animacion)
                Handler().postDelayed({
                    animation.visibility = View.INVISIBLE
                }, 100)
            }
        }.start()
    }

    fun correct(animation: LottieAnimationView, shuffle: MutableList<Question>, myButton: Button) {
        if(!sharedViewModel.muted) {
            correctMusic?.start()
        }
        correcto = true
        correct_answer = true
        animationCorrect(animation)
        colorBtn(correcto, myButton, shuffle)
        sharedViewModel.questionCorrect()
        //correctAnswers+= 1


        object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                scaleAnswers()
                Handler().postDelayed({
                    newQuestion(shuffle)
                }, 100)
            }
        }.start()
    }

    // INCORRECT ANSWER
    fun incorrect(animation: LottieAnimationView, shuffle: MutableList<Question>, myButton: Button) {
        if(!sharedViewModel.muted) {
            incorrectMusic?.start()
        }
        animationIncorrect(animation)
        correcto = false
        correct_answer = false
        colorBtn(correcto, myButton, shuffle)
        sharedViewModel.questionIncorrect()
        //incorrectAnswers+= 1



        object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                scaleAnswers()
                Handler().postDelayed({
                    newQuestion(shuffle)
                }, 100)
            }
        }.start()
    }

    private fun endGame(){

        progressCircles()
        sharedViewModel.mediaPlayerQuiz.release()
        incorrectMusic?.release()
        correctMusic?.release()

        val animation = requireView().findViewById<LottieAnimationView>(R.id.animationWin)
        stopBothTimers()
        //stopTimer()
        animation.setAnimation(R.raw.animation_finish)
        animation.visibility = View.VISIBLE
        animation.playAnimation()

//        val bundle = bundleOf( QuestionsActivity.Questions.SCORE to correctAnswers.toString(),
//            QuestionsActivity.Questions.CATEGORY to category,
//            QuestionsActivity.Questions.DIFFICULTY to difficulty)

        findNavController().navigate(R.id.action_questionsFragment_to_characterFragment)

    }

    private fun scaleAnswers(){
        val answers = arrayOf(
            binding.answer1,
            binding.answer2,
            binding.answer3,
            binding.answer4
        )

        var index : Int = 0

        val myAnim : Animation = AnimationUtils.loadAnimation(this.requireContext(),
            R.anim.scale_to_0
        )
        for(answer in answers){
            answer.startAnimation(myAnim)
        }

//        val anim_timer = object: CountDownTimer(500, 100) {
//            override fun onTick(millisUntilFinished: Long) {
//                if(index < 4) {
//                    answers[index].startAnimation(myAnim)
//                    index++
//                }
//            }
//            override fun onFinish() {
//
//            }
//        }

    }

    private fun changeHeaderBackground(category : String){
        when (category) {
            "action" -> {
                binding.backgroundLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.action
                    )
                )
                binding.txtTitle.setText(R.string.category_action)
            }
            "comedy" -> {
                binding.backgroundLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.comedy
                    )
                )
                binding.txtTitle.setText(R.string.category_comedy)
            }
            "science fiction" -> {
                binding.backgroundLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.sci_fi
                    )
                )
                binding.txtTitle.setText(R.string.category_scifi)
            }
            "horror" -> {
                binding.backgroundLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.horror
                    )
                )
                binding.txtTitle.setText(R.string.category_horror)
            }
            "animation" -> {
                binding.backgroundLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.animation
                    )
                )
                binding.txtTitle.setText(R.string.category_animation)
            }
            "drama" -> {
                binding.backgroundLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.drama
                    )
                )
                binding.txtTitle.setText(R.string.category_drama)
            }
        }
    }

}

