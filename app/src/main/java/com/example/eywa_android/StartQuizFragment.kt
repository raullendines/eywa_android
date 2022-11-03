package com.example.eywa_android

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController

private const val QUESTIONS = "QUESTIONS"
class StartQuizFragment : Fragment(), QuestionsActivity.getQuestionsList {


    private var myQuestions : MutableList<Question> = mutableListOf()

    override fun getQuestionsListFun(questions : MutableList<Question>) {
        myQuestions = questions
    }

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
        val bundle = bundleOf(QUESTIONS to myQuestions)
        val timer = object: CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {

                findNavController().navigate(R.id.action_startQuizFragment_to_questionsFragment, bundle)
            }
        }
        timer.start()
    }
}