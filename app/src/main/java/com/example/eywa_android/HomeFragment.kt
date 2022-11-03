package com.example.eywa_android

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), Home.mainPage {



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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        val btnPlay = requireView().findViewById<Button>(R.id.btnPlay)
        btnPlay.setOnClickListener(){
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }
        val myActivity : Home = activity as Home
        changeLang(myActivity.lang)

        val btnAnim = requireView().findViewById<Button>(R.id.btnAnim)

        btnAnim.setOnClickListener(){
            val myAnim : Animation = AnimationUtils.loadAnimation(this.requireContext(), R.anim.scale_to_100)
            btnPlay.startAnimation(myAnim)

            val resizeAnim : Animation = AnimationUtils.loadAnimation(this.requireContext(), R.anim.rescale)
            val timer = object: CountDownTimer(500, 100) {
                override fun onTick(millisUntilFinished: Long) {

                }

                override fun onFinish() {

                    btnPlay.startAnimation(resizeAnim)
                }
            }
            timer.start()


        }

    }

    override fun changeLang(lang: String) {

        val btnPlay = requireView().findViewById<Button>(R.id.btnPlay)
        val textIntro = requireView().findViewById<TextView>(R.id.txtIntro)

        when(lang){
            "cat" -> {
                btnPlay.setText("JUGAR")
                textIntro.text = "(CAT)"
            }
            "esp" -> {
                btnPlay.setText("JUGAR")
                textIntro.text = "(ESP)"
            }
            "eng" -> {
                btnPlay.setText("PLAY")
                textIntro.text = "(A LOT OF ENGLISH THINGS)"
            }
        }
    }

    private fun scaleAnimation(v : View, startScale : Float, endScale : Float){
        val anim = ScaleAnimation(1f, 2f,
            startScale, endScale,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 1f)
        anim.fillAfter = true
        anim.duration = 1000
        v.startAnimation(anim)

    }


}