package com.example.eywa_android

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_ranking.view.*

class UserRankingAdapter (private val context: Context,
                          private val ranking: MutableList<UserRanking>) :
    RecyclerView.Adapter<UserRankingAdapter.UserRankingViewHolder>(),
    View.OnClickListener

{
    private val layout = R.layout.layout_ranking
    private var clickListener: View.OnClickListener? = null
    class UserRankingViewHolder(val view: View):
        RecyclerView.ViewHolder(view)
    {
        var txtRank: TextView
        var imgUsername: ImageView
        var txtUsername: TextView
        var imgCategory: ImageView
        var txtCategory: TextView
        var txtDifficulty : TextView
        var txtScore: TextView

        init
        {
            txtRank = view.findViewById(R.id.txtRank)
            imgUsername = view.findViewById(R.id.imgUsername)
            txtUsername = view.findViewById(R.id.txtUsername)
            imgCategory = view.findViewById(R.id.imgCategory)
            txtCategory= view.findViewById(R.id.txtCategory)
            txtDifficulty= view.findViewById(R.id.txtDifficulty)
            txtScore = view.findViewById(R.id.txtScore)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRankingViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return UserRankingViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserRankingViewHolder, position: Int)
    {
        val planet = ranking[position]
        bindVideogame(holder,planet)
    }

    fun bindVideogame (holder: UserRankingViewHolder, user: UserRanking)
    {
        holder.txtRank?.text = user.rank.toString()
        holder.imgUsername?.setImageResource(user.userImage)
        holder.txtUsername?.text = user.username.uppercase()
        holder.imgCategory?.setImageResource(user.categoryImage)
        holder.txtCategory?.text = user.category
        holder.txtDifficulty?.text = user.difficulty
        holder.txtScore?.text = user.score.toString()

    }

    override fun getItemCount(): Int {return ranking.size}


    fun setOnClickListener(listener: View.OnClickListener)
    {
        clickListener = listener
    }

    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }
}