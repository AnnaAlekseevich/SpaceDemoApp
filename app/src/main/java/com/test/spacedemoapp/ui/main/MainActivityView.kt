package com.test.spacedemoapp.ui.main

import androidx.paging.PagingData
import com.test.spacedemoapp.domain.models.RoverPhoto
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainActivityView: MvpView {
    fun showInternetConnectionError()
    fun showException(errorMessage: String)
    fun showProgress()
    fun hideProgress()
    fun setPagingData(pagingData: PagingData<RoverPhoto>)
    fun openDetailsActivity(photoForDetails: String)
}