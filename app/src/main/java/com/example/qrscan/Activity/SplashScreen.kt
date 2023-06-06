package com.example.qrscan.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.airbnb.lottie.LottieAnimationView
import com.example.qrscan.R

class SplashScreen : AppCompatActivity() {

   private lateinit var  lottie : LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

lottie = findViewById(R.id.lottieAnimation)
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
                                       startActivity(Intent(this@SplashScreen,LoginActivity::class.java))
            finish()
        },3000)
    }
}