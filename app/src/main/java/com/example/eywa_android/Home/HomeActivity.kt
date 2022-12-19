package com.example.eywa_android.Home

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.findNavController
import com.example.eywa_android.ClassObject.User
import com.example.eywa_android.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*


class HomeActivity : AppCompatActivity() {

    private val sharedViewModel : HomeSharedViewModel by viewModels()
    private var userLoaded = false

    interface mainPage {
        fun changeLang()
    }
    private var goingBack = false


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (!userLoaded){
            val intent = getIntent()
            val user = intent.getSerializableExtra("USER") as User
            sharedViewModel.setUserToDisplay(user)
        }
        userLoaded = true

        //MATERIAL CARD USER MENU

        val btnUser = findViewById<ImageButton>(R.id.userMenu)
        btnUser.setOnClickListener(){
            displayUserMenu(btnUser)
        }
        val userLayout = findViewById<LinearLayout>(R.id.userLayout)
        val settingsLayout = findViewById<LinearLayout>(R.id.settingsLayout)
        val lougoutLayout = findViewById<LinearLayout>(R.id.logoutLayout)
        val changeVolume = findViewById<ImageView>(R.id.changeVolume)

        changeVolume.setOnClickListener(){
            changeVolume()
        }

        userLayout.setOnClickListener(){
            hideUserMenu(btnUser)
            navigateToUserMenu()

        }
        settingsLayout.setOnClickListener(){
            hideUserMenu(btnUser)
        }
        lougoutLayout.setOnClickListener(){
            hideUserMenu(btnUser)
            finish()
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


        //SET LANGUAGE BUTTON AND TEXT
        setLangTextImage()

    }

    override fun onResume() {
        super.onResume()

        if (!sharedViewModel.muted){
            sharedViewModel.mediaPlayer = MediaPlayer.create(this, R.raw.fondo)
            sharedViewModel.mediaPlayer.isLooping = true
            sharedViewModel.mediaPlayer.start()
        }
    }

    private fun navigateToUserMenu(){

        val myFragmentManager = this.supportFragmentManager.findFragmentByTag("myfragment")
        val myFragment = myFragmentManager!!.childFragmentManager.fragments[0]

        when (myFragment){
            is HomeFragment ->  findNavController(R.id.nav_host_fragment_container_main).navigate(R.id.action_homeFragment_to_userFragment)
            is CategoryFragment -> findNavController(R.id.nav_host_fragment_container_main).navigate(
                R.id.action_categoryFragment_to_userFragment
            )
            is DifficultyFragment -> findNavController(R.id.nav_host_fragment_container_main).navigate(
                R.id.action_difficultyFragment_to_userFragment
            )
            is RankingFragment -> findNavController(R.id.nav_host_fragment_container_main).navigate(
                R.id.action_rankingFragment_to_userFragment
            )
        }
    }

    private fun setLangTextImage() {
        val imageBandera = findViewById<ImageView>(R.id.imageBandera)
        val textLang = findViewById<TextView>(R.id.txtLang)


        var locale : Locale? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            locale = resources.configuration.locales.get(0);
        } else{
            //noinspection deprecation
            locale = resources.configuration.locale
        }
        var localeLang = locale!!.language

        when(localeLang){
            "ca" -> {
                imageBandera.setImageResource(R.drawable.catalunya_bandera)
                textLang.setText("CAT")
            }
            "es" -> {
                imageBandera.setImageResource(R.drawable.espanya_bandera)
                textLang.setText("ESP")
            }
            "en" -> {
                imageBandera.setImageResource(R.drawable.english_bandera)
                textLang.setText("ENG")
            }
        }

    }

    private fun changeVolume(){
        if (changeVolume.tag == "unmute"){
            sharedViewModel.mediaPlayer?.release()
            changeVolume.setImageResource(R.drawable.ic_baseline_volume_off_24)
            changeVolume.tag = "mute"
            sharedViewModel.muted = true;
        }
        else{
            changeVolume.setImageResource(R.drawable.ic_baseline_volume_on_24)
            changeVolume.tag = "unmute"
            sharedViewModel.muted = false;
            sharedViewModel.mediaPlayer = MediaPlayer.create(this, R.raw.fondo)
            sharedViewModel.mediaPlayer.isLooping = true
            sharedViewModel.mediaPlayer.start()
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
        changeFragmentLang(myFragment as mainPage)


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