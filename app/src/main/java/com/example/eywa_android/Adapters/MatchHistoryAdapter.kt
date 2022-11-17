package com.example.eywa_android.Adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eywa_android.ClassObject.QuizMatch
import com.example.eywa_android.R

class MatchHistoryAdapter(private val context: Context,
                          private val matchList: MutableList<QuizMatch>) :
    RecyclerView.Adapter<MatchHistoryAdapter.ImageViewHolder>(){

    private val layout = R.layout.match_item

    private val categoryImages = arrayListOf<Int>(
        R.drawable.gun,
        R.drawable.ufo,
        R.drawable.disneyland,
        R.drawable.comedy,
        R.drawable.horror,
        R.drawable.drama

    )


    class ImageViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        var imageCategory : ImageView
        var textCategory : TextView
        var textDifficulty : TextView
        var textPuntuation : TextView

        init {
            imageCategory = view.findViewById(R.id.imageCategory)
            textCategory = view.findViewById(R.id.textCategory)
            textDifficulty = view.findViewById(R.id.textDifficulty)
            textPuntuation = view.findViewById(R.id.textPuntuation)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        var image : Int? = null
        when(matchList[position].category){
            "action" -> image = categoryImages[0]
            "science fiction" -> image = categoryImages[1]
            "animation" -> image = categoryImages[2]
            "comedy" -> image = categoryImages[3]
            "horror" -> image = categoryImages[4]
            "drama" -> image = categoryImages[5]
        }

        val textCat = matchList[position].category
        val textDif = matchList[position].difficulty.toString()
        val textPunt = matchList[position].points
        bindMatch(holder, image!!, textCat, textDif, textPunt)

    }

    fun bindMatch(holder: ImageViewHolder, image: Int, textCat : String, textDif : String, textPunt : String){
        holder.imageCategory.setImageResource(image)
        holder.textCategory.text = textCat.uppercase()
        holder.textDifficulty.text = textDif
        holder.textPuntuation.text = textPunt
    }

    override fun getItemCount() = matchList.size

}
