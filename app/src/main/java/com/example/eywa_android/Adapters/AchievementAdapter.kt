package com.example.eywa_android.Adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.eywa_android.ClassObject.QuizAchievement
import com.example.eywa_android.R

class AchievementAdapter(private val context: Context,
                         private val achievementList: MutableList<QuizAchievement>) :
    RecyclerView.Adapter<AchievementAdapter.ImageViewHolder>(),
    View.OnClickListener{

    private val layout = R.layout.achievement_item

    private var clickListener : View.OnClickListener? = null


    class ImageViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        var imageAchievement : ImageView
        val textTitle : TextView
        val textDescription : TextView
        val cardId : CardView

        init {
            imageAchievement = view.findViewById(R.id.imageAchievement)
            textTitle = view.findViewById(R.id.textTitle)
            textDescription = view.findViewById(R.id.textDescription)
            cardId = view.findViewById(R.id.startCard)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = achievementList[position].image
        val title = achievementList[position].title
        val description = achievementList[position].description
        bindAchievement(holder, image, title, description)

    }

    fun bindAchievement(holder: ImageViewHolder, image : Int, title : String, description : String){
        holder.imageAchievement.setImageResource(image)
        holder.textTitle.text = title
        holder.textDescription.text = description

    }

    override fun getItemCount() = achievementList.size

    fun setOnClickListener(listener: View.OnClickListener){
        clickListener = listener
    }

    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

}
