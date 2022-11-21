package com.example.eywa_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_ranking.*
import kotlinx.android.synthetic.main.fragment_ranking.view.*

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
            UserRanking("Pau",14000,2, R.drawable.logo_eywa,R.drawable.circulo_oro,"ACTION","EASY"),
            UserRanking("Pau",13000,3, R.drawable.logo_eywa,R.drawable.circulo_oro,"COMEDY","EASY"),
            UserRanking("Pau",12000,4, R.drawable.logo_eywa,R.drawable.circulo_oro,"ACTION","EASY"),
            UserRanking("Pau",11000,5, R.drawable.logo_eywa,R.drawable.circulo_oro,"COMEDY","EASY"),
            UserRanking("Marcelinho",10000,6, R.drawable.logo_eywa,R.drawable.circulo_oro,"SCIENCE FICTION","EASY"),
            UserRanking("Pau",9000,7, R.drawable.logo_eywa,R.drawable.circulo_oro,"COMEDY","EASY"),
            UserRanking("Pau",8000,8, R.drawable.logo_eywa,R.drawable.circulo_oro,"ANIMATION","EASY"),
            UserRanking("Pau",7000,9, R.drawable.logo_eywa,R.drawable.circulo_oro,"ANIMATION","EASY"),
            UserRanking("Pau",6000,10, R.drawable.logo_eywa,R.drawable.circulo_oro,"HORROR","EASY"),
            UserRanking("Pau",5000,11, R.drawable.logo_eywa,R.drawable.circulo_oro,"DRAMA","EASY"),
            UserRanking("Pau",4000,12, R.drawable.logo_eywa,R.drawable.circulo_oro,"DRAMA","EASY"),
            UserRanking("Pau",3000,13, R.drawable.logo_eywa,R.drawable.circulo_oro,"DRAMA","EASY"),
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

        val categories = mutableListOf<Category>(
            Category(R.drawable.logo_eywa,"ALL"),
            Category(R.drawable.logo_eywa,"SCIENCE FICTION"),
            Category(R.drawable.logo_eywa,"ACTION"),
            Category(R.drawable.logo_eywa,"COMEDY"),
            Category(R.drawable.logo_eywa,"DRAMA"),
            Category(R.drawable.logo_eywa,"ANIMATION"),
            Category(R.drawable.logo_eywa,"HORROR")
        )


        val categoryAdapter = CategoryAdapter(requireContext(),categories)
        spinnerCategories.adapter = categoryAdapter

        spinnerCategories.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val filteredList = mutableListOf<UserRanking>()
                var top3_filtered = mutableListOf<UserRanking>()
                var top4ToEndFiltered = mutableListOf<UserRanking>()
                filteredList.clear()
                top3_filtered.clear()
                top4ToEndFiltered.clear()
                var category = categories.get(position)
                when(category.categoryName){
                    "ALL" -> {
                        top3_filtered = top3
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
                        top4ToEndFiltered = top4_to_end
                        val usersRankingAdapter = UserRankingAdapter(requireContext(), top4ToEndFiltered)
                        leaderboard_4_to_end.hasFixedSize()
                        leaderboard_4_to_end.layoutManager = LinearLayoutManager(requireContext())
                        leaderboard_4_to_end.adapter = usersRankingAdapter
                    }
                    "SCIENCE FICTION" -> {
                        filterCategory(category.categoryName,filteredList,top3_filtered,top4ToEndFiltered,category,ranking)
                    }
                    "ACTION" -> {
                        filterCategory(category.categoryName,filteredList,top3_filtered,top4ToEndFiltered,category,ranking)
                    }
                    "COMEDY" -> {
                        filterCategory(category.categoryName,filteredList,top3_filtered,top4ToEndFiltered,category,ranking)
                    }
                    "DRAMA" ->{
                        filterCategory(category.categoryName,filteredList,top3_filtered,top4ToEndFiltered,category,ranking)
                    }
                    "ANIMATION" ->{
                        filterCategory(category.categoryName,filteredList,top3_filtered,top4ToEndFiltered,category,ranking)
                    }
                    "HORROR" -> {
                        filterCategory(category.categoryName,filteredList,top3_filtered,top4ToEndFiltered,category,ranking)
                    }

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

    private fun filterCategory(categoryName: String,filteredList : MutableList<UserRanking>, top3_filtered : MutableList<UserRanking>, top4ToEndFiltered : MutableList<UserRanking>,category : Category,ranking: MutableList<UserRanking>) {
        for (user : UserRanking in ranking){
            if (user.category.equals(category.categoryName)){
                filteredList.add(user)
            }
        }
        for (position in filteredList.indices){
            if (position == 0 || position == 1 || position == 2) {
                top3_filtered.add(filteredList[position])
                filteredList[position].rank = position + 1
            }
            else{
                top4ToEndFiltered.add(filteredList[position])
                filteredList[position].rank = position + 1
            }
        }

        for (position in top3_filtered.indices){
            if (position == 0){
                imgRank1.setImageResource(top3_filtered[position].userImage)
                txtUsernameRank1.text = top3_filtered[position].username
                txtScoreRank1.text = top3_filtered[position].score.toString()
            }else if(position == 1){
                imgRank2.setImageResource(top3_filtered[position].userImage)
                txtUsernameRank2.text = top3_filtered[position].username
                txtScoreRank2.text = top3_filtered[position].score.toString()
            }else {
                imgRank3.setImageResource(top3_filtered[position].userImage)
                txtUsernameRank3.text = top3_filtered[position].username
                txtScoreRank3.text = top3_filtered[position].score.toString()
            }
        }

        val usersRankingAdapter = UserRankingAdapter(requireContext(), top4ToEndFiltered)
        leaderboard_4_to_end.hasFixedSize()
        leaderboard_4_to_end.layoutManager = LinearLayoutManager(requireContext())
        leaderboard_4_to_end.adapter = usersRankingAdapter
    }

}