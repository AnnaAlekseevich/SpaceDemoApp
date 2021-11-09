package com.test.spacedemoapp.data.repositories

import android.util.Log
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPhotosRxPagingSource @Inject constructor(
    private val repository: RoverPhotosRepository,
) : RxPagingSource<Int, RoverPhoto>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, RoverPhoto>> {
        val currentLoadingPageKey = params.key ?: 1

        return repository.getPhotos(
            earthDate = "2021-10-30",
            page = currentLoadingPageKey,
            apiKey = "gNsHlfPRJY7iFwXdTsIzdNNO7YyPipOIg79CFjK1"
        )
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, currentLoadingPageKey) }
            .onErrorReturn {
                Log.d("DB_LOADING", "GetPhotosRxPagingSource error $it ")
                LoadResult.Error(it)
            }
    }

    private fun toLoadResult(
        data: List<RoverPhoto>,
        currentLoadingPageKey: Int
    ): LoadResult<Int, RoverPhoto> {
        Log.d("Progress", "GetPhotosRxPagingSource")
        //Log.d("PHOTOCLICK", "GetPhotosRxPagingSource toLoadResult data = $data")
        val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
        Log.d("CheckKey", "prevKey = $prevKey")
        Log.d("CheckKey", "nextKey = ${currentLoadingPageKey.plus(1)}")
        return LoadResult.Page(
            data = data,
            prevKey = prevKey,
            nextKey = currentLoadingPageKey.plus(1)
        )
    }

    override fun getRefreshKey(state: PagingState<Int, RoverPhoto>): Int? {
        TODO("Not yet implemented")
    }

}