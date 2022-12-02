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

        val allMatches = FilesManager.getMatches(requireContext())
        allMatches.sortByDescending { it.points.toInt() }

        var ranking = mutableListOf<UserRanking>()
        var users = FilesManager.getUsers(requireContext())
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




        val top3 = mutableListOf<UserRanking>()
        val top4_to_end = mutableListOf<UserRanking>()


        val usersRankingAdapter = UserRankingAdapter(requireContext(), top4_to_end)
        leaderboard_4_to_end.hasFixedSize()
        leaderboard_4_to_end.layoutManager = LinearLayoutManager(requireContext())
        leaderboard_4_to_end.adapter = usersRankingAdapter

        val user = User(
            id = 22,
            username = "TESTING",
            password = "2",
            image = "avatar",
            quizAchievementList = mutableListOf(),
            dateOfRegister = "11/11/1111")

        imgRank2.setOnClickListener(){
            val bundle = bundleOf(
                "OTHER_USER" to false,
                QuestionsActivity.Questions.USER to user
            )
            findNavController().navigate(R.id.action_rankingFragment_to_userFragment, bundle)
            Toast.makeText(requireContext(), "LISTENER", Toast.LENGTH_LONG).show()
        }
//        usersRankingAdapter.setOnClickListener(){
//
//            val userRanking :UserRanking = top4_to_end[leaderboard_4_to_end.getChildAdapterPosition(it)]
//
//            val bundle = bundleOf(
//                "OTHER_USER" to false,
//                QuestionsActivity.Questions.USER to user
//            )
//            findNavController().navigate(R.id.action_rankingFragment_to_userFragment, bundle)
//            Toast.makeText(requireContext(), "LISTENER", Toast.LENGTH_LONG).show()
//
//        }

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

        imgRank1.setImageResource(R.drawable.logo_eywa)
        txtUsernameRank1.text = ""
        txtScoreRank1.text = ""

        imgRank2.setImageResource(R.drawable.logo_eywa)
        txtUsernameRank2.text = ""
        txtScoreRank2.text = ""

        imgRank3.setImageResource(R.drawable.logo_eywa)
        txtUsernameRank3.text = ""
        txtScoreRank3.text = ""


        for (position in top3_filtered.indices){
            when(position){
                0 -> {
                    val path = requireContext().filesDir.path.toString() + "/img/" + top3_filtered[position].userImage + ".jpeg"
                    val bitmap = BitmapFactory.decodeFile(path)
                    imgRank1.setImageBitmap(bitmap)
                    txtUsernameRank1.text = top3_filtered[position].username
                    txtScoreRank1.text = top3_filtered[position].score.toString()
                }
                1 -> {
                    val path = requireContext().filesDir.path.toString() + "/img/" + top3_filtered[position].userImage + ".jpeg"
                    val bitmap = BitmapFactory.decodeFile(path)
                    imgRank2.setImageBitmap(bitmap)
                    txtUsernameRank2.text = top3_filtered[position].username
                    txtScoreRank2.text = top3_filtered[position].score.toString()
                }
                2 -> {
                    val path = requireContext().filesDir.path.toString() + "/img/" + top3_filtered[position].userImage + ".jpeg"
                    val bitmap = BitmapFactory.decodeFile(path)
                    imgRank3.setImageBitmap(bitmap)
                    txtUsernameRank3.text = top3_filtered[position].username
                    txtScoreRank3.text = top3_filtered[position].score.toString()
                }
            }
        }

        val usersRankingAdapter = UserRankingAdapter(requireContext(), top4ToEndFiltered)
        leaderboard_4_to_end.hasFixedSize()
        leaderboard_4_to_end.layoutManager = LinearLayoutManager(requireContext())
        leaderboard_4_to_end.adapter = usersRankingAdapter
    }

}