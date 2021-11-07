package com.test.spacedemoapp.ui.screen

import android.util.Log
import com.test.spacedemoapp.data.repositories.RoverPhotosRepository
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class SplashActivityPresentor
@Inject constructor(private val roverPhotosRepository: RoverPhotosRepository) :
    MvpPresenter<SplashActivityView>() {

    fun setInternetAvailable(isAvailable: Boolean) {
        if (isAvailable) {
            roverPhotosRepository.getPhotos("2021-10-30", 1, "DEMO_KEY", isAvailable)
            Log.d("NETWORKCONNEKTION", "Internet's available")
        } else {
            roverPhotosRepository.getPhotos("2021-10-30", 1, "DEMO_KEY", isAvailable)
            Log.d("NETWORKCONNEKTION", "Internet doesn't available")
        }
        if(!isAvailable){
            viewState.showInternetConnectionError()
        }
    }


}