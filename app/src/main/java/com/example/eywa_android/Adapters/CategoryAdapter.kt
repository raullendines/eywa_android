package com.example.eywa_android.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.eywa_android.ClassObject.Category
import com.example.eywa_android.R
import kotlinx.android.synthetic.main.spinner_category.view.*

class CategoryAdapter (context: Context,
    private val categories: MutableList<Category>) :
    ArrayAdapter<Category>(context, 0, categories)
    {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return initView(position,convertView,parent)
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            return initView(position,convertView,parent)
        }

        private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
            val category = getItem(position)

            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_category, parent, false)
            view.imgCategory.setImageResource(category!!.categoryImage)
            view.categoryName.text = category.categoryName
            return view
        }

    }