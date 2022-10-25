package com.example.eywa_android

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialContainerTransform
import org.w3c.dom.Text

class Difficulty : AppCompatActivity() {

    var difficultySelected : Int = 0
    var lang : String = "eng"
    var volume : Boolean = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)


        val btnLang = findViewById<LinearLayout>(R.id.btnLangMenu)
        val changeVolume = findViewById<ImageView>(R.id.changeVolume)

        val btnEasy = findViewById<Button>(R.id.btnEasy)
        val btnMedium = findViewById<Button>(R.id.btnMedium)
        val btnHard = findViewById<Button>(R.id.btnHard)
        val btnLegend = findViewById<Button>(R.id.btnLegend)

        changeVolume.setOnClickListener(){
            if(volume){
                changeVolume.setImageResource(R.drawable.ic_baseline_volume_off_24)
            } else{
                changeVolume.setImageResource(R.drawable.ic_baseline_volume_on_24)
            }
            volume = !volume
        }


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




        btnLang.setOnClickListener(){
            displayLangMenu(btnLang)
        }


        val catalanLayout = findViewById<LinearLayout>(R.id.catalanLayout)
        val espanyolLayout = findViewById<LinearLayout>(R.id.espanyolLayout)
        val englishLayout = findViewById<LinearLayout>(R.id.englishLayout)

        catalanLayout.setOnClickListener(){
            hideLangMenu(btnLang)
            lang = "cat"
            changeLang(difficultyBtn)
        }
        espanyolLayout.setOnClickListener(){
            hideLangMenu(btnLang)
            lang = "esp"
            changeLang(difficultyBtn)
        }
        englishLayout.setOnClickListener(){
            hideLangMenu(btnLang)
            lang = "eng"
            changeLang(difficultyBtn)
        }



    }

    private fun displayLangMenu(fabMenu : LinearLayout){
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

    private fun hideLangMenu(fabMenu : LinearLayout){
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
        val imageBandera = findViewById<ImageView>(R.id.imageBandera)
        val txtLang = findViewById<TextView>(R.id.txtLang)
        when(lang){
            "cat" -> {
                imageBandera.setImageResource(R.drawable.catalunya_bandera)
                txtLang.setText("CAT")
                txtViewDifficulty.setText("DIFICULTAT")
                difficultyBtn[0].setText("FÀCIL")
                difficultyBtn[1].setText("MITJANA")
                difficultyBtn[2].setText("DIFICIL")
                difficultyBtn[3].setText("LLEGENDA")
                playButton.setText("JUGAR")
            }
            "esp" -> {
                imageBandera.setImageResource(R.drawable.espanya_bandera)
                txtLang.setText("ESP")
                txtViewDifficulty.setText("DIFICULTAD")
                difficultyBtn[0].setText("FÁCIL")
                difficultyBtn[1].setText("MEDIANA")
                difficultyBtn[2].setText("DIFÍCIL")
                difficultyBtn[3].setText("LEYENDA")
                playButton.setText("JUGAR")
            }
            "eng" -> {
                imageBandera.setImageResource(R.drawable.english_bandera)
                txtLang.setText("ENG")
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
