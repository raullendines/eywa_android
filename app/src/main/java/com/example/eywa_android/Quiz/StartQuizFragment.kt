package com.example.eywa_android.Quiz

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.eywa_android.ClassObject.Question
import com.example.eywa_android.Quiz.QuestionsActivity
import com.example.eywa_android.R

private const val QUESTIONS = "QUESTIONS"
class StartQuizFragment : Fragment(){


    private var myQuestions : MutableList<Question> = mutableListOf()

    private val sharedViewModel : QuizSharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_quiz, container, false)
    }

    override fun onStart() {
        super.onStart()

        val timer = object: CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {

                findNavController().navigate(R.id.action_startQuizFragment_to_questionsFragment)
            }
        }
        timer.start()
    }
}