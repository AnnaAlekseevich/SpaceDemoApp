package com.test.spacedemoapp.ui.screen

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.test.spacedemoapp.R
import com.test.spacedemoapp.SpaceDemoApp
import com.test.spacedemoapp.data.repositories.LocalRoverPhotosDataStore
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
    lateinit var localRoverPhotosDataStore: LocalRoverPhotosDataStore

    @InjectPresenter
    lateinit var presenter: SplashActivityPresentor

    @ProvidePresenter
    fun provideDetailsPresenter(): SplashActivityPresentor? {
        return SplashActivityPresentor(localRoverPhotosDataStore)
    }

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

//    @SuppressLint("NewApi")
//    override fun checkInternet(): Boolean {
//        Log.d("NETWORKCONNEKTION", "Internet's available")
//        return NetWorkConnection.isInternetAvailable(this@SplashActivity)
//    }
//
//    override fun validateError() {
//        Snackbar.make(constraintMainActivity, "Please check your internet connection", Snackbar.LENGTH_SHORT)
//            .setAction("OK", View.OnClickListener { /*Take Action*/ }).show()
//
//        Log.d("NETWORKCONNEKTION", "Internet don't available")
//    }

}