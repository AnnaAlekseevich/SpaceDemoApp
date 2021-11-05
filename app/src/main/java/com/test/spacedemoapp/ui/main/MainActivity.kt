package com.test.spacedemoapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.spacedemoapp.R
import com.test.spacedemoapp.SpaceDemoApp
import com.test.spacedemoapp.databinding.ActivityMainBinding
import com.test.spacedemoapp.domain.models.Photos
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainActivityView {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var presenter: MainActivityPresenter

    init {
        Log.d("RoverPhotos", "getPhotos Main presenter" )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        SpaceDemoApp.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun showException(errorMessage: String) {
        TODO("Not yet implemented")
    }

    override fun showPhotos(photos: Photos) {
        TODO("Not yet implemented")
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        super.onResume()
        presenter.getPhotos("2021-10-30", 1, "iqz2OJREH35Wnos1NV50CeEgB2tLWSyfCMSG2vaV")
    }
}