package com.test.spacedemoapp.ui.main


import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.observable
import com.test.spacedemoapp.data.repositories.GetPhotosRxPagingSource
import com.test.spacedemoapp.data.repositories.RoverPhotosRepository
import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@InjectViewState
class MainActivityPresenter @Inject constructor(
    private val roverPhotosRepository: RoverPhotosRepository,
    private val internetStateObservable: Observable<Boolean>
) : MvpPresenter<MainActivityView>() {

    private val presenterScope: CoroutineScope by lazy {
        val context: CoroutineContext = Dispatchers.Main.plus(SupervisorJob(null))
        CoroutineScope(context)
    }

    override fun attachView(view: MainActivityView?) {
        super.attachView(view)

        internetStateObservable.subscribe { newInternetState ->
            Log.d("INTERNET_CHECK", "PRESENTER NEW VALUE = $newInternetState")
            setInternetAvailable(newInternetState)
        }

        Pager(PagingConfig(pageSize = 25)) {
            GetPhotosRxPagingSource(roverPhotosRepository)
        }.observable.observeOn(AndroidSchedulers.mainThread()).cachedIn(presenterScope)
            .subscribe { pagingData ->  //set it to view
                Log.d("Progress", "attachView in Presenter")
                viewState.hideProgress()
                viewState.setPagingData(pagingData)
            }
    }


    fun onPhotoClicked(roverPhoto: RoverPhoto) {
        Log.d("PHOTOCLICK", "onPhotoClicked + $roverPhoto")
        var photoForDetails = roverPhoto.urlItemPhoto
        viewState.openDetailsActivity(photoForDetails)
    }

    private fun setInternetAvailable(isAvailable: Boolean) {
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