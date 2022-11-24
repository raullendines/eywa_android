package com.example.eywa_android.Adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.eywa_android.ClassObject.QuizAchievement
import com.example.eywa_android.R

class AchievementAdapter(private val context: Context,
                         private val achievementListUnchanged: MutableList<QuizAchievement>) :
    RecyclerView.Adapter<AchievementAdapter.ImageViewHolder>(),
    View.OnClickListener{

    private val layout = R.layout.achievement_item

    val achievementList = achievementListUnchanged

    private var clickListener : View.OnClickListener? = null


    class ImageViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        var imageAchievement : ImageView
        val textTitle : TextView
        val cardId : CardView

        init {
            imageAchievement = view.findViewById(R.id.imageAchievement)
            textTitle = view.findViewById(R.id.textTitle)
            cardId = view.findViewById(R.id.startCard)
        }
    }

    init {
        achievementList.sortBy { !it.owned }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = if (achievementList[position].owned){
            R.drawable.trophy
        } else {
            R.drawable.trophy_black_white
        }
        val title = achievementList[position].title
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            bindAchievement(holder, image, title)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun bindAchievement(holder: ImageViewHolder, image : Int, title : String){
        holder.imageAchievement.setImageResource(image)
        holder.textTitle.text = title
    }

    override fun getItemCount() = achievementList.size

    fun setOnClickListener(listener: View.OnClickListener){
        clickListener = listener
    }

    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

}
