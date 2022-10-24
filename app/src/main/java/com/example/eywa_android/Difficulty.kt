package com.example.eywa_android

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialContainerTransform

class Difficulty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)

        val fabMenu = findViewById<FloatingActionButton>(R.id.fab)
        //val close = findViewById<TextView>(R.id.tvClose)
        val linearLay = findViewById<LinearLayout>(R.id.LinLayout)
        var menuExpanded : Boolean = false


        fabMenu.setOnClickListener(){
            //fabMenu.setExpanded(true)
            cositas(fabMenu)
        }

        /*close.setOnClickListener(){
            fabMenu.setExpanded(false)
        }
        */
        val txt1 = findViewById<TextView>(R.id.txtView1)
        val txt2 = findViewById<TextView>(R.id.txtView2)
        val txt3 = findViewById<TextView>(R.id.txtView3)

        txt1.setOnClickListener(){
            cositas2(fabMenu)
            fabMenu.background = resources.getDrawable(R.drawable.catalunya_bandera, theme)

        }
        txt2.setOnClickListener(){
            cositas2(fabMenu)

        }
        txt3.setOnClickListener(){
            cositas2(fabMenu)
        }
        when(menuExpanded){
            true -> {

            }
        }


    }

    private fun cositas(fabMenu : FloatingActionButton){
        val endCard = findViewById<MaterialCardView>(R.id.end_card)
        val myLayout = findViewById<LinearLayout>(R.id.LinLayout)
        val root = findViewById<CoordinatorLayout>(R.id.root)
        val goBack = findViewById<TextView>(R.id.goBack)
        val transition : MaterialContainerTransform = MaterialContainerTransform()
        val myInterpolator : FastOutSlowInInterpolator = FastOutSlowInInterpolator()
        transition.scrimColor = Color.TRANSPARENT

        transition.setInterpolator(myInterpolator)

        transition.setDuration(1)

        transition.startView = fabMenu
        transition.endView = endCard
        transition.addTarget(fabMenu)

        TransitionManager.beginDelayedTransition(root, transition as? Transition)

        if(fabMenu != null){
            fabMenu.visibility = View.INVISIBLE
        }
        if(endCard != null){
            endCard.visibility = View.VISIBLE
        }

        myLayout.setBackgroundColor(Color.parseColor("#40000000"))
        goBack.isClickable = true
        goBack.visibility = View.VISIBLE
        goBack.setOnClickListener(){
            cositas2(fabMenu)

        }

    }

    private fun cositas2(fabMenu : FloatingActionButton){
        val goBack = findViewById<TextView>(R.id.goBack)
        goBack.isClickable = false
        goBack.visibility = View.INVISIBLE
        val startCard = findViewById<MaterialCardView>(R.id.end_card)
        val myLayout = findViewById<LinearLayout>(R.id.LinLayout)
        val root = findViewById<CoordinatorLayout>(R.id.root)
        val transition : MaterialContainerTransform = MaterialContainerTransform()
        val myInterpolator : FastOutSlowInInterpolator = FastOutSlowInInterpolator()
        transition.scrimColor = Color.TRANSPARENT

        transition.setInterpolator(myInterpolator)
        transition.startView = startCard
        transition.endView = fabMenu
        transition.addTarget(startCard)

        TransitionManager.beginDelayedTransition(root, transition as? Transition)
        if(startCard != null){
            startCard.visibility = View.INVISIBLE
        }
        if(fabMenu != null){
            fabMenu.visibility = View.VISIBLE
        }
        myLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
    }


}
}