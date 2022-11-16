package com.example.eywa_android.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eywa_android.R

class CategoryPercentageAdapter(private val context: Context,
                                private val percentageList: MutableList<String>) :
    RecyclerView.Adapter<CategoryPercentageAdapter.ImageViewHolder>(){

    private val layout = R.layout.category_item

    private val categoryImages = arrayListOf<Int>(
        R.drawable.gun,
        R.drawable.ufo,
        R.drawable.disneyland,
        R.drawable.comedy,
        R.drawable.horror,
        R.drawable.drama

    )


    class ImageViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        val imageCategory : ImageView
        val textPercentage : TextView

        init {
            imageCategory = view.findViewById(R.id.imageCategory)
            textPercentage = view.findViewById(R.id.textPercentage)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val text = percentageList[position]
        val image = categoryImages[position]
        bindImage(holder, text, image)

    }

    fun bindImage(holder: ImageViewHolder, text: String, image: Int){
        holder.imageCategory.setImageResource(image)
        holder.textPercentage.text = text
    }

    override fun getItemCount() = percentageList.size

}
