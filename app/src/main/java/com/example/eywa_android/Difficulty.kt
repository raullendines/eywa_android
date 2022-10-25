package com.example.eywa_android

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialContainerTransform

class Difficulty : AppCompatActivity() {

    var difficultySelected : Int = 0
    var lang : String = "eng"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)


        val fabMenu = findViewById<FloatingActionButton>(R.id.fab)
        //val close = findViewById<TextView>(R.id.tvClose)
        val linearLay = findViewById<LinearLayout>(R.id.LinLayout)

        val btnEasy = findViewById<Button>(R.id.btnEasy)
        val btnMedium = findViewById<Button>(R.id.btnMedium)
        val btnHard = findViewById<Button>(R.id.btnHard)
        val btnLegend = findViewById<Button>(R.id.btnLegend)

        val difficultyBtn = arrayOf<Button>(
            btnEasy, btnMedium, btnHard, btnLegend
        )

        btnEasy.setOnClickListener(){
            if (difficultySelected != 0){
                difficultyBtn[difficultySelected].setBackgroundResource(R.drawable.rounded_corners_white)
                difficultySelected = 0
                btnEasy.setBackgroundResource(R.drawable.rounded_corners_yellow)
            }
        }

        btnMedium.setOnClickListener(){
            if (difficultySelected != 1){
                difficultyBtn[difficultySelected].setBackgroundResource(R.drawable.rounded_corners_white)
                difficultySelected = 1
                btnMedium.setBackgroundResource(R.drawable.rounded_corners_yellow)
            }
        }

        btnHard.setOnClickListener(){
            if (difficultySelected != 2){
                difficultyBtn[difficultySelected].setBackgroundResource(R.drawable.rounded_corners_white)
                difficultySelected = 2
                btnHard.setBackgroundResource(R.drawable.rounded_corners_yellow)
            }
        }

        btnLegend.setOnClickListener(){
            if (difficultySelected != 3){
                difficultyBtn[difficultySelected].setBackgroundResource(R.drawable.rounded_corners_white)
                difficultySelected = 3
                btnLegend.setBackgroundResource(R.drawable.rounded_corners_yellow)
            }
        }




        fabMenu.setOnClickListener(){
            //fabMenu.setExpanded(true)
            displayLangMenu(fabMenu)
        }

        /*close.setOnClickListener(){
            fabMenu.setExpanded(false)
        }
        */
        val catalanLayout = findViewById<LinearLayout>(R.id.catalanLayout)
        val espanyolLayout = findViewById<LinearLayout>(R.id.espanyolLayout)
        val englishLayout = findViewById<LinearLayout>(R.id.englishLayout)

        catalanLayout.setOnClickListener(){
            hideLangMenu(fabMenu)
            fabMenu.setImageResource(R.drawable.catalunya_bandera)
            lang = "cat"
            changeLang(difficultyBtn)
        }
        espanyolLayout.setOnClickListener(){
            hideLangMenu(fabMenu)
            fabMenu.setImageResource(R.drawable.espanya_bandera)
            lang = "esp"
            changeLang(difficultyBtn)
        }
        englishLayout.setOnClickListener(){
            hideLangMenu(fabMenu)
            fabMenu.setImageResource(R.drawable.english_bandera)
            lang = "eng"
            changeLang(difficultyBtn)
        }



    }

    private fun displayLangMenu(fabMenu : FloatingActionButton){
        val endCard = findViewById<MaterialCardView>(R.id.end_card)
        val myLayout = findViewById<LinearLayout>(R.id.LinLayout)
        val backgroundOpacity = findViewById<LinearLayout>(R.id.backgroundOpacity)
        val root = findViewById<CoordinatorLayout>(R.id.root)
        val goBack = findViewById<TextView>(R.id.goBack)
        val transition : MaterialContainerTransform = MaterialContainerTransform()
        val myInterpolator : FastOutSlowInInterpolator = FastOutSlowInInterpolator()
        transition.scrimColor = Color.TRANSPARENT

        transition.setInterpolator(myInterpolator)

        transition.setDuration(0)

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


        backgroundOpacity.visibility = View.VISIBLE
        goBack.isClickable = true
        goBack.visibility = View.VISIBLE
        goBack.setOnClickListener(){
            hideLangMenu(fabMenu)

        }

    }

    private fun hideLangMenu(fabMenu : FloatingActionButton){
        val goBack = findViewById<TextView>(R.id.goBack)
        goBack.isClickable = false
        goBack.visibility = View.INVISIBLE
        val backgroundOpacity = findViewById<LinearLayout>(R.id.backgroundOpacity)
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
        backgroundOpacity.visibility = View.INVISIBLE
    }

    private fun changeLang(difficultyBtn : Array<Button>){
        val txtViewDifficulty = findViewById<TextView>(R.id.txtViewDifficulty)
        val playButton = findViewById<Button>(R.id.btnPlay)
        when(lang){
            "cat" -> {
                txtViewDifficulty.setText("DIFICULTAT")
                difficultyBtn[0].setText("FÀCIL")
                difficultyBtn[1].setText("MITJANA")
                difficultyBtn[2].setText("DIFICIL")
                difficultyBtn[3].setText("LLEGENDA")
                playButton.setText("JUGAR")
            }
            "esp" -> {
                txtViewDifficulty.setText("DIFICULTAD")
                difficultyBtn[0].setText("FÁCIL")
                difficultyBtn[1].setText("MEDIANA")
                difficultyBtn[2].setText("DIFÍCIL")
                difficultyBtn[3].setText("LEYENDA")
                playButton.setText("JUGAR")
            }
            "eng" -> {
                txtViewDifficulty.setText("DIFFICULTY")
                difficultyBtn[0].setText("EASY")
                difficultyBtn[1].setText("MEDIUM")
                difficultyBtn[2].setText("HARD")
                difficultyBtn[3].setText("LEGEND")
                playButton.setText("PLAY")
            }
        }

    }



}
