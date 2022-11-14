package com.example.eywa_android.Loading_Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.eywa_android.R


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