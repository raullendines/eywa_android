package com.example.eywa_android

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ScoreAdapter (context: Context, val layout: Int, val users: MutableList<UserRanking>) :
    ArrayAdapter<UserRanking>(context, layout, users)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View
        if (convertView!= null)
        {
            view = convertView
        }
        else
        {
            view = LayoutInflater.from(context).inflate(layout,parent,false)
        }

        bindUser(view, users[position])
        return view
    }


    fun bindUser(view: View, user: UserRanking)
    {
        var txtRank = view.findViewById<TextView>(R.id.txtRank)
        txtRank?.text = user.rank.toString()
        var imgUsername = view.findViewById<ImageView>(R.id.imgUsername)
        imgUsername?.setImageResource(user.userImage)
        var txtUsername = view.findViewById<TextView>(R.id.txtUsername)
        txtUsername?.text = user.username.uppercase()
        var imgCategory = view.findViewById<ImageView>(R.id.imgCategory)
        imgCategory?.setImageResource(user.categoryImage)
        var txtCategory= view.findViewById<TextView>(R.id.txtCategory)
        txtCategory?.text = user.category
        var txtDifficulty= view.findViewById<TextView>(R.id.txtDifficulty)
        txtDifficulty?.text = user.difficulty
        var txtScore = view.findViewById<TextView>(R.id.txtScore)
        txtScore?.text = user.score.toString()
    }


}