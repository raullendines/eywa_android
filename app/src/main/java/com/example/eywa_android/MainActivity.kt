package com.example.eywa_android

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var imageView : ImageView = findViewById(R.id.logoEywa)

        val animation_in : Animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        val animation_out : Animation = AnimationUtils.loadAnimation(this,R.anim.fade_out)

        imageView.startAnimation(animation_in)

        val timer = object: CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {

                imageView.startAnimation(animation_out)
            }
        }
        timer.start()











    }
}