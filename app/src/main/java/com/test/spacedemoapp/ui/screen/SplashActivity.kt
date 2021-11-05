package com.test.spacedemoapp.ui.screen

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.test.spacedemoapp.R
import com.test.spacedemoapp.databinding.ActivitySplashBinding
import com.test.spacedemoapp.ui.main.MainActivity

class SplashActivity: AppCompatActivity(), SplashActivityView {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var rocketAnimation: AnimatedVectorDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.splashImageAnim.apply {
            setBackgroundResource(R.drawable.splash_screen_anim)
            rocketAnimation = background as AnimatedVectorDrawable
            rocketAnimation.start()
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)
        }, 3000)
    }
}