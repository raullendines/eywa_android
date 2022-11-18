package com.example.eywa_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_ranking.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RankingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RankingFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_ranking, container, false)
    }

    override fun onStart() {
        super.onStart()

        val ranking = mutableListOf<UserRanking>(
            UserRanking("Pau",15000,1, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"),
            UserRanking("Pau",14000,2, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"),
            UserRanking("Pau",13000,3, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"),
            UserRanking("Pau",12000,4, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"),
            UserRanking("Pau",11000,5, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"),
            UserRanking("Marcelinho",10000,6, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"),
            UserRanking("Pau",9000,7, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"),
            UserRanking("Pau",8000,8, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"),
            UserRanking("Pau",7000,9, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"),
            UserRanking("Pau",6000,10, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"),
            UserRanking("Pau",5000,11, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"),
            UserRanking("Pau",4000,12, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"),
            UserRanking("Pau",3000,13, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"),
            UserRanking("Pau",2000,14, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"),
            UserRanking("Pau",1000,15, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY")
        )

        val top3 = mutableListOf<UserRanking>()
        val top4_to_end = mutableListOf<UserRanking>()


        val usersRankingAdapter = UserRankingAdapter(requireContext(), top4_to_end)
        leaderboard_4_to_end.hasFixedSize()
        leaderboard_4_to_end.layoutManager = LinearLayoutManager(requireContext())
        leaderboard_4_to_end.adapter = usersRankingAdapter

        for (user : UserRanking in ranking){
            if (user.rank == 1 || user.rank == 2 || user.rank == 3) {
                top3.add(user)
            }
            else{
                top4_to_end.add(user)
            }
        }

        for (user : UserRanking in top3){
            if (user.rank == 1){
                imgRank1.setImageResource(user.userImage)
                txtUsernameRank1.text = user.username
                txtScoreRank1.text = user.score.toString()
            }else if(user.rank == 2){
                imgRank2.setImageResource(user.userImage)
                txtUsernameRank2.text = user.username
                txtScoreRank2.text = user.score.toString()
            }else {
                imgRank3.setImageResource(user.userImage)
                txtUsernameRank3.text = user.username
                txtScoreRank3.text = user.score.toString()
            }
        }
    }

}