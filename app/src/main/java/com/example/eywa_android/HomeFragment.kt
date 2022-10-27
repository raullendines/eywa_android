package com.example.eywa_android

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import java.lang.RuntimeException

class HomeFragment : Fragment(), Home.mainPage {

    private var activityContenedora : Home.mainPage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is Home.mainPage){
            activityContenedora = context
        } else throw RuntimeException(context.toString() + " Debe implementar interficie")
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
                textIntro.text = "(CAT)"
            }
        }
    }
}