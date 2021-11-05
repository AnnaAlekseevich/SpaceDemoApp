package com.test.spacedemoapp.ui.main

import com.test.spacedemoapp.domain.models.Photos
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainActivityView: MvpView {
    fun showException(errorMessage: String)
    fun showPhotos(photos: Photos)
    fun showProgress()
}