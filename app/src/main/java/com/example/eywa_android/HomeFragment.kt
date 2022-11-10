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
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
//import kotlinx.android.synthetic.main.fragment_home.*

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
    }

    override fun changeLang() {

        val btnPlay = requireView().findViewById<Button>(R.id.btnPlay)
        val txtIntro = requireView().findViewById<TextView>(R.id.txtIntro)
        btnPlay.setText(R.string.btnPlay)
        txtIntro.setText(R.string.intro)

    }

}