package com.example.eywa_android

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.findNavController
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*


class Home : AppCompatActivity() {

    interface mainPage {
        fun changeLang()
    }
    private var goingBack = false

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

        var myFragmentManager = this.supportFragmentManager.findFragmentByTag("myfragment")
        var myFragment = myFragmentManager!!.childFragmentManager.fragments[0]

        catalanLayout.setOnClickListener(){
            buttonChangeLang("ca")
        }

        espanyolLayout.setOnClickListener(){
            buttonChangeLang("es")
        }

        englishLayout.setOnClickListener(){
            buttonChangeLang("en")
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

    private fun buttonChangeLang(lang: String){
        val btnLang = findViewById<LinearLayout>(R.id.btnLangMenu)
        val imageBandera = findViewById<ImageView>(R.id.imageBandera)
        val txtLang = findViewById<TextView>(R.id.txtLang)
        hideLangMenu(btnLang)
        when(lang){
            "ca" -> {
                imageBandera.setImageResource(R.drawable.catalunya_bandera)
                txtLang.text = "CAT"
                setLocale(lang, "ES")
            }
            "es" -> {
                imageBandera.setImageResource(R.drawable.espanya_bandera)
                txtLang.text = "ESP"
                setLocale(lang, "ES")
            }
            "en" -> {
                imageBandera.setImageResource(R.drawable.english_bandera)
                txtLang.text = "ENG"
                setLocale(lang, "US")
            }

        }

        val myFragmentManager = this.supportFragmentManager.findFragmentByTag("myfragment")
        val myFragment = myFragmentManager!!.childFragmentManager.fragments[0]
        changeFragmentLang(myFragment as Home.mainPage)


    }


    private fun changeFragmentLang (currentFragment : mainPage){

        currentFragment.changeLang()

    }

    fun setLocale(lang: String, loc: String){
        val locale = Locale(lang, loc)
        val config = resources.configuration
        config.setLocale(locale)
        // createConfigurationContext(config) No funciona
        // updateConfiguration deprecated, pero funciona
        resources.updateConfiguration(config, resources.displayMetrics)
    }

//    override fun onStart() {
//        super.onStart()
//        if (goingBack){
//            onBackPressed()
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        goingBack = true
//    }

}