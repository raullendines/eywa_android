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
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import com.example.eywa_android.ClassObject.QuizAchievement
import com.example.eywa_android.R
import com.example.eywa_android.Utility.FilesManager
import kotlinx.android.synthetic.main.achievement_item.view.*
import java.util.*

class AchievementAdapter(
    private val context: Context,
    private val achievementListUnchanged: MutableList<QuizAchievement>,
    private val userAchievements: MutableList<Int>,
    private val fullList :Boolean
) :
    RecyclerView.Adapter<AchievementAdapter.ImageViewHolder>(),
    View.OnClickListener{

    private val layout = R.layout.achievement_item

    val achievementList = achievementListUnchanged

    private var clickListener : View.OnClickListener? = null


    private lateinit var localeLang : String


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
        if(fullList){
            for (id in userAchievements){
                achievementList[id].owned = true
            }
            achievementList.sortBy { !it.owned }
        } else {
            for (element in achievementList){
                element.owned = true
            }
        }


        //Find out what is the current lang displayed
        var locale : Locale? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            locale = context.resources.configuration.locales.get(0);
        } else{
            //noinspection deprecation
            locale = context.resources.configuration.locale
        }
        localeLang = locale!!.language
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
        var title = achievementList[position].title
        when(localeLang){
            "ca" -> title = achievementList[position].title_ca
            "es" -> title = achievementList[position].title_es
            "en" -> title = achievementList[position].title
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            bindAchievement(
                holder = holder,
                image = image,
                title = title
            )
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

    fun changeLang(language : String, list : RecyclerView){
        var index = 0
        list.forEach { view ->
            when(language){
                "ca" -> view.textTitle.text = achievementList[index].title_ca
                "es" -> view.textTitle.text = achievementList[index].title_es
                "en" -> view.textTitle.text = achievementList[index].title
            }
        }
    }

}
