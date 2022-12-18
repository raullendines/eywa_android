package com.example.eywa_android.Home

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eywa_android.Adapters.CategoryAdapter
import com.example.eywa_android.Adapters.UserRankingAdapter
import com.example.eywa_android.ClassObject.Category
import com.example.eywa_android.ClassObject.QuizMatch
import com.example.eywa_android.ClassObject.User
import com.example.eywa_android.ClassObject.UserRanking
import com.example.eywa_android.Quiz.QuestionsActivity
import com.example.eywa_android.R
import com.example.eywa_android.Utility.FilesManager
import com.example.eywa_android.Utility.RecyclerItemClickListenr
import kotlinx.android.synthetic.main.fragment_ranking.*
import kotlinx.android.synthetic.main.fragment_ranking.view.*


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

        val top3 = mutableListOf<UserRanking>()
        val top4_to_end = mutableListOf<UserRanking>()

        var ranking = mutableListOf<UserRanking>()
        var users = FilesManager.getUsers(requireContext())


        var rankingFromScore = arguments?.getBoolean("FROM_SCORE")
        if (rankingFromScore == null){
            imgRank2.setOnClickListener(){
                var userRanking = top3[1]
                lateinit var userProfile : User
                for (user in users){
                    if (user.username == userRanking.username){
                        userProfile= user
                    }
                }
                val bundle = bundleOf(
                    "OTHER_USER" to false,
                    QuestionsActivity.Questions.USER to userProfile
                )
                findNavController().navigate(R.id.action_rankingFragment_to_userFragment, bundle)
            }
            imgRank1.setOnClickListener(){
                var userRanking = top3[0]
                lateinit var userProfile : User
                for (user in users){
                    if (user.username == userRanking.username){
                        userProfile= user
                    }
                }
                val bundle = bundleOf(
                    "OTHER_USER" to false,
                    QuestionsActivity.Questions.USER to userProfile
                )
                findNavController().navigate(R.id.action_rankingFragment_to_userFragment, bundle)
            }
            imgRank3.setOnClickListener(){
                var userRanking = top3[2]
                lateinit var userProfile : User
                for (user in users){
                    if (user.username == userRanking.username){
                        userProfile= user
                    }
                }
                val bundle = bundleOf(
                    "OTHER_USER" to false,
                    QuestionsActivity.Questions.USER to userProfile
                )
                findNavController().navigate(R.id.action_rankingFragment_to_userFragment, bundle)
            }
        }else {
            imgRank1.isClickable = false
            imgRank1.isClickable = false
            imgRank1.isClickable = false
        }

        buttonGoBack.setOnClickListener() {
            findNavController().popBackStack()
        }

        val allMatches = FilesManager.getMatches(requireContext())
        allMatches.sortByDescending { it.points.toInt() }


        var index = 0
        for(match : QuizMatch in  allMatches){
            var difficulty : String = ""
            when(match.difficulty){
                1 -> difficulty = "EASY"
                2 -> difficulty = "MEDIUM"
                3 -> difficulty = "HARD"
                4 -> difficulty = "LEGEND"
            }

            var imgCategory : Int = 1
            when (match.category.uppercase()){
                "ACTION" -> imgCategory=R.drawable.gun
                "SCIENCE FICTION" -> imgCategory=R.drawable.ufo
                "ANIMATION" -> imgCategory=R.drawable.disneyland
                "COMEDY" -> imgCategory=R.drawable.comedy
                "HORROR" -> imgCategory=R.drawable.horror
                "DRAMA" -> imgCategory=R.drawable.drama
            }


            var user = users.find { it.id == match.userId}
            var userRanking = UserRanking(
            user!!.username,
            match.points.toInt(),
            index + 1,
            user.image,
            imgCategory,
            match.category.uppercase(),
            difficulty,
            )
            index++
            ranking.add(userRanking)
        }







        val usersRankingAdapter = UserRankingAdapter(requireContext(), top4_to_end)
        leaderboard_4_to_end.hasFixedSize()
        leaderboard_4_to_end.layoutManager = LinearLayoutManager(requireContext())
        leaderboard_4_to_end.adapter = usersRankingAdapter





        leaderboard_4_to_end.addOnItemTouchListener(RecyclerItemClickListenr(requireContext(),leaderboard_4_to_end, object : RecyclerItemClickListenr.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
//                Toast.makeText(requireContext(), top4_to_end[position].rank.toString(), Toast.LENGTH_LONG).show()

                var userRanking = top4_to_end[position]
                lateinit var userProfile : User
                for (user in users){
                    if (user.username == userRanking.username){
                         userProfile= user
                    }
                }
                val bundle = bundleOf(
                    "OTHER_USER" to false,
                    QuestionsActivity.Questions.USER to userProfile
                )
                findNavController().navigate(R.id.action_rankingFragment_to_userFragment, bundle)
            }
            override fun onItemLongClick(view: View?, position: Int) {
                Toast.makeText(requireContext(), "LISTENER LONG", Toast.LENGTH_LONG).show()
            }
        }))







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
                val path = requireContext().filesDir.path.toString() + "/img/" + user.userImage + ".jpeg"
                val bitmap = BitmapFactory.decodeFile(path)
                imgRank1.setImageBitmap(bitmap)
                txtUsernameRank1.text = user.username
                txtScoreRank1.text = user.score.toString()
                imgCategoryRank1.setImageResource(user.categoryImage)
            }else if(user.rank == 2){
                val path = requireContext().filesDir.path.toString() + "/img/" + user.userImage + ".jpeg"
                val bitmap = BitmapFactory.decodeFile(path)
                imgRank2.setImageBitmap(bitmap)
                txtUsernameRank2.text = user.username.uppercase()
                txtScoreRank2.text = user.score.toString()
                imgCategoryRank2.setImageResource(user.categoryImage)
                txtDifficultyRank2.text = user.difficulty

            }else {
                val path = requireContext().filesDir.path.toString() + "/img/" + user.userImage + ".jpeg"
                val bitmap = BitmapFactory.decodeFile(path)
                imgRank3.setImageBitmap(bitmap)
                txtUsernameRank3.text = user.username
                imgCategoryRank3.setImageResource(user.categoryImage)
                txtScoreRank3.text = user.score.toString()
            }
        }

        val categories = mutableListOf<Category>(
            Category(R.drawable.cine,"ALL"),
            Category(R.drawable.ufo,"SCIENCE FICTION"),
            Category(R.drawable.gun,"ACTION"),
            Category(R.drawable.comedy,"COMEDY"),
            Category(R.drawable.drama,"DRAMA"),
            Category(R.drawable.disneyland,"ANIMATION"),
            Category(R.drawable.horror,"HORROR")
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
                        top4ToEndFiltered = top4_to_end
                        for(position in ranking.indices){
                            ranking[position].rank = position + 1
                        }

                        for (user : UserRanking in top3){
                            if (user.rank == 1){
                                val path = requireContext().filesDir.path.toString() + "/img/" + user.userImage + ".jpeg"
                                val bitmap = BitmapFactory.decodeFile(path)
                                imgRank1.setImageBitmap(bitmap)
                                txtUsernameRank1.text = user.username
                                txtScoreRank1.text = user.score.toString()
                            }else if(user.rank == 2){
                                val path = requireContext().filesDir.path.toString() + "/img/" + user.userImage + ".jpeg"
                                val bitmap = BitmapFactory.decodeFile(path)
                                imgRank2.setImageBitmap(bitmap)
                                txtUsernameRank2.text = user.username
                                txtScoreRank2.text = user.score.toString()
                            }else {
                                val path = requireContext().filesDir.path.toString() + "/img/" + user.userImage + ".jpeg"
                                val bitmap = BitmapFactory.decodeFile(path)
                                imgRank3.setImageBitmap(bitmap)
                                txtUsernameRank3.text = user.username
                                txtScoreRank3.text = user.score.toString()
                            }
                        }

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

    private fun filterCategory(categoryName: String, filteredList : MutableList<UserRanking>, top3_filtered : MutableList<UserRanking>, top4ToEndFiltered : MutableList<UserRanking>, category : Category, ranking: MutableList<UserRanking>) {
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
            when(position){
                0 -> {
                    val path = requireContext().filesDir.path.toString() + "/img/" + top3_filtered[position].userImage + ".jpeg"
                    val bitmap = BitmapFactory.decodeFile(path)
                    imgRank1.setImageBitmap(bitmap)
                    txtUsernameRank1.text = top3_filtered[position].username
                    txtScoreRank1.text = top3_filtered[position].score.toString()
                    imgCategoryRank1.setImageResource(top3_filtered[position].categoryImage)
                    txtDifficultyRank1.text = top3_filtered[position].difficulty
                }
                1 -> {
                    val path = requireContext().filesDir.path.toString() + "/img/" + top3_filtered[position].userImage + ".jpeg"
                    val bitmap = BitmapFactory.decodeFile(path)
                    imgRank2.setImageBitmap(bitmap)
                    txtUsernameRank2.text = top3_filtered[position].username
                    txtScoreRank2.text = top3_filtered[position].score.toString()
                    imgCategoryRank2.setImageResource(top3_filtered[position].categoryImage)
                    txtDifficultyRank2.text = top3_filtered[position].difficulty
                }
                2 -> {
                    val path = requireContext().filesDir.path.toString() + "/img/" + top3_filtered[position].userImage + ".jpeg"
                    val bitmap = BitmapFactory.decodeFile(path)
                    imgRank3.setImageBitmap(bitmap)
                    txtUsernameRank3.text = top3_filtered[position].username
                    txtScoreRank3.text = top3_filtered[position].score.toString()
                    imgCategoryRank3.setImageResource(top3_filtered[position].categoryImage)
                    txtDifficultyRank3.text = top3_filtered[position].difficulty
                }
            }
        }

        val usersRankingAdapter = UserRankingAdapter(requireContext(), top4ToEndFiltered)
        leaderboard_4_to_end.hasFixedSize()
        leaderboard_4_to_end.layoutManager = LinearLayoutManager(requireContext())
        leaderboard_4_to_end.adapter = usersRankingAdapter
    }

}