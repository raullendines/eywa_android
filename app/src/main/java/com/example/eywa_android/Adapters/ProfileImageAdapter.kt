package com.example.eywa_android.Adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.eywa_android.R

class ProfileImageAdapter(private val context: Context) :
    RecyclerView.Adapter<ProfileImageAdapter.ImageViewHolder>(),
    View.OnClickListener{


    private val images = mutableListOf<String>(
        "antonio_banderas",
        "joaquin_phoenix",
        "spencer_tracy",
        "clint_eastwood",
        "robert_de_niro",
        "leonardo_dicaprio",
        "marlon_brando",
        "emilia_clarkeCienc",
        "bennedict_cumberbatch",
        "russel_crowe",
        "marty_mcfly",
        "han_solo",
        "robocop",
        "luke_skywalker",
        "tony_stark",
        "katniss_everdeen",
        "sam_bell",
        "sarah_connor",
        "frankenstein",
        "neo",
        "john_rambo",
        "james_bond",
        "indiana_jones",
        "jack_sparrow",
        "dr_evil",
        "eggsy",
        "viuda_negra",
        "wonder_woman",
        "hermoine_granger",
        "bruce_lee",
        "adam_sandler",
        "borat",
        "alig",
        "noah_levenstein",
        "bradley_cooper",
        "melissa_mccarthy",
        "jeff_lebowski",
        "ryan_reynolds",
        "jim_carrey",
        "mclovin",
        "po",
        "shrek",
        "minions",
        "gato_botas",
        "kowalski",
        "alegria",
        "simba",
        "russell",
        "woody",
        "remy",
        "jack_nicholson",
        "pennywise",
        "conde_dracula",
        "hombre_lobo",
        "imhotep",
        "ghost_rider",
        "blackheart",
        "jinete_cabeza",
        "ryan_macneil",
        "anabelle"
    )

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
        val image = images[position]
        bindImage(holder, image)

    }

    fun bindImage(holder: ImageViewHolder, image: String){
        val imagePath = context.filesDir.toString() + "/img/" + image
        val bitmap = BitmapFactory.decodeFile(imagePath)
        holder.imageProfile.setImageBitmap(bitmap)
    }

    override fun getItemCount() = images.size

    fun setOnClickListener(listener: View.OnClickListener){
        clickListener = listener
    }

    override fun onClick(view: View?) {
        clickListener?.onClick(view)
    }

}
