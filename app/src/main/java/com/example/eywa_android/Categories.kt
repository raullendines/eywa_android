package com.example.eywa_android

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.widget.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialContainerTransform

class Categories : AppCompatActivity() {

    var buttonSelected : Int = 0
    var lang : String = "eng"
    var volume : Boolean = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)



        val buttons = arrayOf(
                findViewById<Button>(R.id.buttonAction),
                findViewById<Button>(R.id.buttonComedy),
                findViewById<Button>(R.id.buttonScienceFiction),
                findViewById<Button>(R.id.buttonHorror),
                findViewById<Button>(R.id.buttonAnimation),
                findViewById<Button>(R.id.buttonDrama),
        )

        for (button in buttons){
            button.setOnClickListener(){
                uncheckButton(buttons[buttonSelected])
                buttonStyle(button)
            }
        }

        //MATERIAL CARD USER MENU

        val btnUser = findViewById<ImageButton>(R.id.userMenu)
        btnUser.setOnClickListener(){
            displayUserMenu(btnUser)
        }
        val userLayout = findViewById<LinearLayout>(R.id.userLayout)
        val settingsLayout = findViewById<LinearLayout>(R.id.settingsLayout)
        val lougoutLayout = findViewById<LinearLayout>(R.id.logoutLayout)

        userLayout.setOnClickListener(){
            hideUserMenu(btnUser)
        }
        settingsLayout.setOnClickListener(){
            hideUserMenu(btnUser)
        }
        lougoutLayout.setOnClickListener(){
            hideUserMenu(btnUser)
        }


        //MATERIAL CARD VIEW DISPLAY CHANGE LANG

        val btnLang = findViewById<LinearLayout>(R.id.btnLangMenu)

        btnLang.setOnClickListener(){
            displayLangMenu(btnLang)
        }



        val catalanLayout = findViewById<LinearLayout>(R.id.catalanLayout)
        val espanyolLayout = findViewById<LinearLayout>(R.id.espanyolLayout)
        val englishLayout = findViewById<LinearLayout>(R.id.englishLayout)

        catalanLayout.setOnClickListener(){
            hideLangMenu(btnLang)
            lang = "cat"
            changeLang(buttons)
        }
        espanyolLayout.setOnClickListener(){
            hideLangMenu(btnLang)
            lang = "esp"
            changeLang(buttons)
        }
        englishLayout.setOnClickListener(){
            hideLangMenu(btnLang)
            lang = "eng"
            changeLang(buttons)
        }

        //VOLUME ON / OFF

        val changeVolume = findViewById<ImageView>(R.id.changeVolume)

        changeVolume.setOnClickListener(){
            if(volume){
                changeVolume.setImageResource(R.drawable.ic_baseline_volume_off_24)
            } else{
                changeVolume.setImageResource(R.drawable.ic_baseline_volume_on_24)
            }
            volume = !volume
        }


    }


    private fun uncheckButton(button : Button){
            button.setBackgroundResource(R.drawable.rounded_corners_white)
            button.setTextColor(Color.parseColor("#3d0066"))
    }


    private fun buttonStyle(button : Button){

        when (button.id){
            resources.getIdentifier("buttonAction", "id", this.packageName) -> {
                button.setBackgroundResource(R.drawable.rounded_corners_red)
                buttonSelected = 0
            }
            resources.getIdentifier("buttonComedy", "id", this.packageName) -> {
                button.setBackgroundResource(R.drawable.rounded_corners_yellow)
                buttonSelected = 1
            }
            resources.getIdentifier("buttonScienceFiction", "id", this.packageName) -> {
                button.setBackgroundResource(R.drawable.rounded_corners_red)
                buttonSelected = 2
            }
            resources.getIdentifier("buttonHorror", "id", this.packageName) -> {
                button.setBackgroundResource(R.drawable.rounded_corners_yellow)
                buttonSelected = 3
            }
            resources.getIdentifier("buttonAnimation", "id", this.packageName) -> {
                button.setBackgroundResource(R.drawable.rounded_corners_red)
                buttonSelected = 4
            }
            resources.getIdentifier("buttonDrama", "id", this.packageName) -> {
                button.setBackgroundResource(R.drawable.rounded_corners_yellow)
                buttonSelected = 5
            }
        }
        button.setTextColor(Color.parseColor("#FFFFFF"))

    }

    private fun displayLangMenu(selectLang : LinearLayout){
        val endCard = findViewById<MaterialCardView>(R.id.end_card)
        val backgroundOpacity = findViewById<LinearLayout>(R.id.backgroundOpacity)
        val root = findViewById<CoordinatorLayout>(R.id.root)
        val goBack = findViewById<TextView>(R.id.goBack)
        val transition : MaterialContainerTransform = MaterialContainerTransform()
        val myInterpolator : FastOutSlowInInterpolator = FastOutSlowInInterpolator()
        transition.scrimColor = Color.TRANSPARENT

        transition.setInterpolator(myInterpolator)

        transition.setDuration(0)

        transition.startView = selectLang
        transition.endView = endCard
        transition.addTarget(selectLang)

        TransitionManager.beginDelayedTransition(root, transition as? Transition)


        if(endCard != null){
            endCard.visibility = View.VISIBLE
        }


        backgroundOpacity.visibility = View.VISIBLE
        goBack.isClickable = true
        goBack.visibility = View.VISIBLE
        goBack.setOnClickListener(){
            hideLangMenu(selectLang)

        }

    }

    private fun displayUserMenu(userButton: ImageButton){
        val endCard = findViewById<MaterialCardView>(R.id.end_card_user)
        val backgroundOpacity = findViewById<LinearLayout>(R.id.backgroundOpacity)
        val root = findViewById<CoordinatorLayout>(R.id.root)
        val goBack = findViewById<TextView>(R.id.goBack)
        val transition : MaterialContainerTransform = MaterialContainerTransform()
        val myInterpolator : FastOutSlowInInterpolator = FastOutSlowInInterpolator()
        transition.scrimColor = Color.TRANSPARENT

        transition.setInterpolator(myInterpolator)

        transition.setDuration(0)

        transition.startView = userButton
        transition.endView = endCard
        transition.addTarget(userButton)

        TransitionManager.beginDelayedTransition(root, transition as? Transition)


        if(endCard != null){
            endCard.visibility = View.VISIBLE
        }


        backgroundOpacity.visibility = View.VISIBLE
        goBack.isClickable = true
        goBack.visibility = View.VISIBLE
        goBack.setOnClickListener(){
            hideUserMenu(userButton)
        }
    }

    private fun hideLangMenu(selectLang : LinearLayout){
        val goBack = findViewById<TextView>(R.id.goBack)
        goBack.isClickable = false
        goBack.visibility = View.INVISIBLE
        val backgroundOpacity = findViewById<LinearLayout>(R.id.backgroundOpacity)
        val startCard = findViewById<MaterialCardView>(R.id.end_card)
        val root = findViewById<CoordinatorLayout>(R.id.root)
        val transition : MaterialContainerTransform = MaterialContainerTransform()
        val myInterpolator : FastOutSlowInInterpolator = FastOutSlowInInterpolator()
        transition.scrimColor = Color.TRANSPARENT

        transition.setInterpolator(myInterpolator)
        transition.startView = startCard
        transition.endView = selectLang
        transition.addTarget(startCard)

        TransitionManager.beginDelayedTransition(root, transition as? Transition)
        if(startCard != null){
            startCard.visibility = View.INVISIBLE
        }
        if(selectLang != null){
            selectLang.visibility = View.VISIBLE
        }
        backgroundOpacity.visibility = View.INVISIBLE
    }

    private fun hideUserMenu(userButton: ImageButton){
        val goBack = findViewById<TextView>(R.id.goBack)
        goBack.isClickable = false
        goBack.visibility = View.INVISIBLE
        val backgroundOpacity = findViewById<LinearLayout>(R.id.backgroundOpacity)
        val startCard = findViewById<MaterialCardView>(R.id.end_card_user)
        val root = findViewById<CoordinatorLayout>(R.id.root)
        val transition : MaterialContainerTransform = MaterialContainerTransform()
        val myInterpolator : FastOutSlowInInterpolator = FastOutSlowInInterpolator()
        transition.scrimColor = Color.TRANSPARENT

        transition.setInterpolator(myInterpolator)
        transition.startView = startCard
        transition.endView = userButton
        transition.addTarget(startCard)

        TransitionManager.beginDelayedTransition(root, transition as? Transition)
        if(startCard != null){
            startCard.visibility = View.INVISIBLE
        }
        if(userButton != null){
            userButton.visibility = View.VISIBLE
        }
        backgroundOpacity.visibility = View.INVISIBLE
    }

    private fun changeLang(categoryButtons : Array<Button>){
        val txtViewCategory = findViewById<TextView>(R.id.txtCategories)
        val playButton = findViewById<Button>(R.id.btnPlay)
        val imageBandera = findViewById<ImageView>(R.id.imageBandera)
        val txtLang = findViewById<TextView>(R.id.txtLang)
        when(lang){
            "cat" -> {
                imageBandera.setImageResource(R.drawable.catalunya_bandera)
                txtLang.setText("CAT")
                txtViewCategory.setText("CATEGORIES")
                categoryButtons[0].setText("ACCIÓ")
                categoryButtons[1].setText("COMEDIA")
                categoryButtons[2].setText("CIENCIA FICCIÓ")
                categoryButtons[3].setText("TERROR")
                categoryButtons[4].setText("ANIMACIÓ")
                categoryButtons[5].setText("DRAMA")

                playButton.setText("JUGAR")
            }
            "esp" -> {
                imageBandera.setImageResource(R.drawable.espanya_bandera)
                txtLang.setText("ESP")
                txtViewCategory.setText("CATEGORIAS")
                categoryButtons[0].setText("ACCIÓN")
                categoryButtons[1].setText("COMEDIA")
                categoryButtons[2].setText("CIENCIA FICCIÓN")
                categoryButtons[3].setText("TERROR")
                categoryButtons[4].setText("ANIMACIÓN")
                categoryButtons[5].setText("DRAMA")
                playButton.setText("JUGAR")
            }
            "eng" -> {
                imageBandera.setImageResource(R.drawable.english_bandera)
                txtLang.setText("ENG")
                txtViewCategory.setText("CATEGORIES")
                categoryButtons[0].setText("ACTION")
                categoryButtons[1].setText("COMEDY")
                categoryButtons[2].setText("SCIENCE FICTION")
                categoryButtons[3].setText("HORROR")
                categoryButtons[4].setText("ANIMATION")
                categoryButtons[5].setText("DRAMA")
                playButton.setText("PLAY")
                }
            }
        }
}