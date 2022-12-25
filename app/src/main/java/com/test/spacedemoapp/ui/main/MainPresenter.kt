package com.test.spacedemoapp.ui.main

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.test.spacedemoapp.data.repositories.GetPhotosPagingSource
import com.test.spacedemoapp.data.repositories.RoverPhotosRepository
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

        val listData =
            Pager(PagingConfig(pageSize = 25)) {
                GetPhotosPagingSource(roverPhotosRepository)
            }

        presenterScope.launch {
            listData.flow.collectLatest {
                viewState.hideProgress()
                viewState.setPagingData(it)
            }
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