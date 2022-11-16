package com.example.eywa_android.Adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.eywa_android.R

class ProfileImageAdapter(private val context: Context,
                        private val imageList: MutableList<String>) :
    RecyclerView.Adapter<ProfileImageAdapter.ImageViewHolder>(),
    View.OnClickListener{

    private val layout = R.layout.image_item

    private var clickListener : View.OnClickListener? = null


    class ImageViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        var imageProfile : ImageView

        init {
            imageProfile = view.findViewById(R.id.imageToDisplay)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        view.setOnClickListener(this)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = imageList[position]
        bindImage(holder, image)

    }

    fun bindImage(holder: ImageViewHolder, image: String){
        val imagePath = context.filesDir.toString() + "/img/" + image + ".jpeg"
        val bitmap = BitmapFactory.decodeFile(imagePath)
        holder.imageProfile.setImageBitmap(bitmap)
    }

    override fun getItemCount() = imageList.size

    fun setOnClickListener(listener: View.OnClickListener){
        clickListener = listener
    }

    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

}
