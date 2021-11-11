package com.test.spacedemoapp.ui.main

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.observable
import com.test.spacedemoapp.data.repositories.GetPhotosRxPagingSource
import com.test.spacedemoapp.data.repositories.RoverPhotosRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@InjectViewState
class MainPresenter @Inject constructor(
    private val roverPhotosRepository: RoverPhotosRepository,
    private val internetStateObservable: Observable<Boolean>
) : MvpPresenter<MainView>() {

    private var isCurrentInternetState: Boolean = false

    private val presenterScope: CoroutineScope by lazy {
        val context: CoroutineContext = Dispatchers.Main.plus(SupervisorJob(null))
        CoroutineScope(context)
    }

    override fun attachView(view: MainView?) {
        super.attachView(view)

        internetStateObservable.subscribe { newInternetState ->
            setInternetAvailable(newInternetState)
        }

        Pager(PagingConfig(pageSize = 25)) {
            GetPhotosRxPagingSource(roverPhotosRepository)
        }.observable.observeOn(AndroidSchedulers.mainThread()).cachedIn(presenterScope)
            .subscribe { pagingData ->  //set it to view
                viewState.hideProgress()
                viewState.setPagingData(pagingData)
            }
    }

    private fun setInternetAvailable(isAvailable: Boolean) {
        if (!isAvailable) {
            viewState.showInternetConnectionError()
        }
        if (isCurrentInternetState != isAvailable) {
            viewState.resetPhotosList()
        }
        isCurrentInternetState = isAvailable


    }
}