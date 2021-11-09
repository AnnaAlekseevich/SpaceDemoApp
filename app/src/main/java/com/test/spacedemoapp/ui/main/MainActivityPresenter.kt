package com.test.spacedemoapp.ui.main


import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.observable
import com.test.spacedemoapp.data.repositories.GetPhotosRxPagingSource
import com.test.spacedemoapp.data.repositories.RoverPhotosRepository
import com.test.spacedemoapp.domain.models.RoverPhoto
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
    private val roverPhotosRepository: RoverPhotosRepository
) : MvpPresenter<MainActivityView>() {

    private val presenterScope: CoroutineScope by lazy {
        val context: CoroutineContext = Dispatchers.Main.plus(SupervisorJob(null))
        CoroutineScope(context)
    }

    override fun attachView(view: MainActivityView?) {
        super.attachView(view)
        Pager(PagingConfig(pageSize = 25)) {
            GetPhotosRxPagingSource(roverPhotosRepository)
        }.observable.observeOn(AndroidSchedulers.mainThread()).cachedIn(presenterScope)
            .subscribe { pagingData ->  //set it to view
                viewState.setPagingData(pagingData)
            }
    }

    fun onPhotoClicked(roverPhoto: RoverPhoto) {
        Log.d("PHOTOCLICK", "onPhotoClicked + $roverPhoto")
        var photoForDetails = roverPhoto.urlItemPhoto
        viewState.openDetailsActivity(photoForDetails)
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