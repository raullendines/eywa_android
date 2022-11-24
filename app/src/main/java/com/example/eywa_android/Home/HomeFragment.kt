package com.example.eywa_android.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.eywa_android.R
import kotlinx.android.synthetic.main.fragment_home.*

//import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), HomeActivity.mainPage {



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
        layoutPlay.setOnClickListener(){
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }
        layoutLeaderboard.setOnClickListener(){
            findNavController().navigate(R.id.action_homeFragment_to_rankingFragment)
        }
    }

    override fun changeLang() {
        //TODO


    }

}