package com.example.eywa_android

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_loading.*


class LoadingFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_loading, container, false)


    }

    override fun onStart() {
        super.onStart()
        txtPressAnywhereToContinue.isVisible = false
        val animation_in : Animation = AnimationUtils.loadAnimation(this.context,R.anim.fade_in)
        val animation_out : Animation = AnimationUtils.loadAnimation(this.context,R.anim.fade_out)
        var pressed : Boolean = false
        val timer = object: CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                txtPressAnywhereToContinue.isVisible = true
                txtPressAnywhereToContinue.startAnimation(animation_in)
                while(!pressed)
                {


                val timer = object: CountDownTimer(3000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                    }

                    override fun onFinish() {
                        txtPressAnywhereToContinue.startAnimation(animation_out)
                    }
                }
                timer.start()
                }
                TxtLoading.isVisible = false
                loadingAnimation.isVisible = false
            }
        }
        timer.start()


        loadingLayout.setOnClickListener(){
            pressed = false
            findNavController().navigate(R.id.action_loadingFragment_to_loginFragment)

        }
    }
}
