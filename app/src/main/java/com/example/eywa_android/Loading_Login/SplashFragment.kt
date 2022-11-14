package com.example.eywa_android.Loading_Login

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.eywa_android.R


class SplashFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_splash, container, false)

        //TIMER

    }


    override fun onStart() {
        super.onStart()
        var imageView : ImageView = requireView().findViewById(R.id.logoEywa)

        val animation_in : Animation = AnimationUtils.loadAnimation(this.context, R.anim.fade_in)
        val animation_out : Animation = AnimationUtils.loadAnimation(this.context, R.anim.fade_out)

        imageView.startAnimation(animation_in)

        val changeTimer = object : CountDownTimer(1000, 1000){
            override fun onTick(p0: Long) {

            }
            override fun onFinish() {
                findNavController().navigate(R.id.action_splashFragment_to_loadingFragment)
            }
        }

        val timer = object: CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {

                imageView.startAnimation(animation_out)
                changeTimer.start()
            }
        }
        timer.start()

    }
}