package com.test.itemdetails

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface DetailsActivityView : MvpView {
    fun showRoverPhoto(pictureUri: String)
}