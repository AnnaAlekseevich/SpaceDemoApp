package com.test.spacedemoapp.ui.main

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava2.observable
import com.test.spacedemoapp.data.common.repositories.RemoteRoverPhotosDataStore
import com.test.spacedemoapp.data.repositories.GetPhotosRxPagingSource
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@InjectViewState
class MainActivityPresenter @Inject constructor(private val remoteRoverPhotosDataStore: RemoteRoverPhotosDataStore) :
    MvpPresenter<MainActivityView>() {

    init {
        Log.d("AdapterData", "getPhotos Main presenter")
    }

    override fun attachView(view: MainActivityView?) {
        super.attachView(view)
        Log.d("AdapterData", "attachView")
        Pager(PagingConfig(pageSize = 20)) {
            GetPhotosRxPagingSource(remoteRoverPhotosDataStore)
        }.observable.subscribe { pagingData ->  //set it to view
            viewState.setPagingData(pagingData)
            Log.d("AdapterData", "pagingData = $pagingData")
        }
    }

    private fun showException(throwable: Throwable?) {
        Log.d("Retrofit", "error = $throwable")
        Log.d("AdapterData", "errorMessage + $throwable")
        val error = throwable as? HttpException
        try {
            viewState.showException(errorMessage = error?.message() ?: "")
        } catch (e: IOException) {
            Log.d("Retrofit", "error = $e")
        }
    }
}