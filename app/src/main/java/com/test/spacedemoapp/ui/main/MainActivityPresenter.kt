package com.test.spacedemoapp.ui.main

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava2.observable
import com.test.spacedemoapp.data.common.repositories.RemoteRoverPhotosDataStore
import com.test.spacedemoapp.data.repositories.GetPhotosRxPagingSource
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@InjectViewState
class MainActivityPresenter @Inject constructor(private val remoteRoverPhotosDataStore: RemoteRoverPhotosDataStore) :
    MvpPresenter<MainActivityView>() {

    override fun attachView(view: MainActivityView?) {
        super.attachView(view)
        Pager(PagingConfig(pageSize = 25)) {
            GetPhotosRxPagingSource(remoteRoverPhotosDataStore)
        }.observable.observeOn(AndroidSchedulers.mainThread())
            .subscribe { pagingData ->  //set it to view
            viewState.setPagingData(pagingData)
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