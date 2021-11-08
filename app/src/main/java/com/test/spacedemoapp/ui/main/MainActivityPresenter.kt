package com.test.spacedemoapp.ui.main

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava2.observable
import com.test.spacedemoapp.data.repositories.GetPhotosRxPagingSource
import com.test.spacedemoapp.data.repositories.RoverPhotosRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@InjectViewState
class MainActivityPresenter @Inject constructor(
    private val roverPhotosRepository: RoverPhotosRepository
) :
    MvpPresenter<MainActivityView>() {

    override fun attachView(view: MainActivityView?) {
        super.attachView(view)
        Pager(PagingConfig(pageSize = 25)) {
            GetPhotosRxPagingSource(roverPhotosRepository)
        }.observable.observeOn(AndroidSchedulers.mainThread())
            .subscribe { pagingData ->  //set it to view
                viewState.setPagingData(pagingData)
            }
    }

    fun setInternetAvailable(isAvailable: Boolean) {
        roverPhotosRepository.isInternetAvailable = isAvailable
        if (isAvailable) {
            roverPhotosRepository.getPhotos("2021-10-30", 1, "DEMO_KEY")
            Log.d("NETWORKCONNEKTION", "Internet's available")
        } else {
            roverPhotosRepository.getPhotos("2021-10-30", 1, "DEMO_KEY")
            Log.d("NETWORKCONNEKTION", "Internet doesn't available")
        }
        if (!isAvailable) {
            viewState.showInternetConnectionError()
        }
    }

    private fun showException(throwable: Throwable?) {
        val error = throwable as? HttpException
        try {
            viewState.showException(errorMessage = error?.message() ?: "")
        } catch (e: IOException) {
            Log.d("Retrofit", "error = $e")
        }
    }
}