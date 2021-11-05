package com.test.spacedemoapp.ui.main

import android.annotation.SuppressLint
import android.util.Log
import com.test.spaceapp.data.common.repositories.RemoteRoverPhotosDataStoreImpl
import com.test.spacedemoapp.domain.models.Photos
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.MvpPresenter
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(private val remoteRoverPhotosDataStoreImpl: RemoteRoverPhotosDataStoreImpl): MvpPresenter<MainActivityView>() {
    init {
        Log.d("RoverPhotos", "getPhotos Main presenter" )
    }

    override fun attachView(view: MainActivityView?) {
        super.attachView(view)
        getPhotos("2021-10-30", 1, "DEMO_KEY")
    }

    @SuppressLint("CheckResult")
    fun getPhotos(earthDate: String, page: Int, apiKey: String) {
        Log.d("RoverPhotos", "getPhotos Main presenter")
        remoteRoverPhotosDataStoreImpl.getPhotos(earthDate, page, apiKey)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showProgress() }
            .subscribe({ photos -> showPhotos(photos) },
                { throwable -> showException(throwable) })

    }

    private fun showPhotos(photos: Photos) {
        Log.d("RoverPhotos", "RoverPhotos + $photos")
        viewState.showPhotos(photos)
    }

    private fun showException(throwable: Throwable?) {
        Log.d("Retrofit", "error = $throwable")
        val error = throwable as? HttpException
        try {
            viewState.showException(errorMessage = error?.message()?:"")
        } catch (e: IOException) {
            Log.d("Retrofit", "error = $e")
        }
    }
}