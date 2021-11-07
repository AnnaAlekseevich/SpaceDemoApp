package com.test.spacedemoapp.ui.screen

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SplashActivityView: MvpView {
//    fun checkInternet(): Boolean
//    fun validateError()
}