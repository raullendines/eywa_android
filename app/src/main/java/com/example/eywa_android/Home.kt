package com.example.eywa_android

import android.app.Fragment
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.widget.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.example.eywa_android.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialContainerTransform

class Home : AppCompatActivity() {


    var lang = "eng"

    interface mainPage {
        fun changeLang(lang: String)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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


            val homeFragment = this.supportFragmentManager.findFragmentById(R.id.homeFragment)
            if(homeFragment is HomeFragment ){
                Toast.makeText(this, "sometext", 10000)
            }

//            homeFragment.changeLang(lang)
            //changeLang(buttons)
        }
        espanyolLayout.setOnClickListener(){
            hideLangMenu(btnLang)
            lang = "esp"
            //changeLang(buttons)
        }
        englishLayout.setOnClickListener(){
            hideLangMenu(btnLang)
            lang = "eng"
            //changeLang(buttons)
        }

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

    private fun test (omplirFragment : mainPage){
        omplirFragment.changeLang(lang)
    }
}