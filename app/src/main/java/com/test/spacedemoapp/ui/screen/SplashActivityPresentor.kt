package com.test.spacedemoapp.ui.screen

import com.test.spacedemoapp.data.repositories.LocalRoverPhotosDataStore
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class SplashActivityPresentor
@Inject constructor(private val localRoverPhotosDataStore: LocalRoverPhotosDataStore): MvpPresenter<SplashActivityView>() {



}