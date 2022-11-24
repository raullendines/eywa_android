package com.example.eywa_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_score.*


class ScoreFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false)
    }

    override fun onStart() {
        super.onStart()

        val scoreUsers = ArrayList<UserRanking>()


        scoreUsers.add(UserRanking("Pau",5000,1, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"))
        scoreUsers.add(UserRanking("Pau",4000,1, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"))
        scoreUsers.add(UserRanking("Pau",3000,1, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"))

        val usersAdapter = ScoreAdapter(requireContext(),R.layout.layout_ranking,scoreUsers)
        listUsersScore.adapter = usersAdapter

    }
}