package com.test.spacedemoapp.ui.screen

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.test.photodetails.ui.DetailsActivity
import com.test.spacedemoapp.R
import com.test.spacedemoapp.SpaceDemoApp
import com.test.spacedemoapp.data.repositories.RoverPhotosRepository
import com.test.spacedemoapp.databinding.ActivitySplashBinding
import com.test.spacedemoapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class SplashActivity : MvpAppCompatActivity(), SplashActivityView {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var rocketAnimation: AnimatedVectorDrawable

    @Inject
    lateinit var roverPhotosRepository: RoverPhotosRepository

    @InjectPresenter
    lateinit var presenter: SplashActivityPresentor

    @ProvidePresenter
    fun provideDetailsPresenter(): SplashActivityPresentor? = SplashActivityPresentor()

    override fun onCreate(savedInstanceState: Bundle?) {
        SpaceDemoApp.INSTANCE.appComponent.inject(this)
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