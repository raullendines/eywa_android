package com.example.eywa_android

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController


class MainActivity : AppCompatActivity() {


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)



            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val fragmentLoading = LoadingFragment()


//            var imageView : ImageView = findViewById(R.id.logoEywa)
//
//            val animation_in : Animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
//            val animation_out : Animation = AnimationUtils.loadAnimation(this,R.anim.fade_out)
//
//            imageView.startAnimation(animation_in)





//            val timer = object: CountDownTimer(5000, 1000) {
//                override fun onTick(millisUntilFinished: Long) {
//
//                }
//
//                override fun onFinish() {
//                    fragmentTransaction.replace(R.id.fragmentContainerView, fragmentLoading)
//                    fragmentTransaction.addToBackStack(null)
//                    fragmentTransaction.commit()
////                    imageView.startAnimation(animation_out)
//                }
//            }
//            timer.start()










//            var users : MutableList<User> = FilesManager.getUsers(this)
//            var questionsCA : MutableList<Question> = FilesManager.getQuestionsCA(this)
//            var questionsEN : MutableList<Question> = FilesManager.getQuestionsEN(this)
//            var questionsES : MutableList<Question> = FilesManager.getQuestionsES(this)
//            var characters : MutableList<Characters> = FilesManager.getCharacters(this)







        }

}