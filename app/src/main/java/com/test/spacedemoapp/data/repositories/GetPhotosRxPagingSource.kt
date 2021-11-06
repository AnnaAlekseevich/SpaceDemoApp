package com.test.spacedemoapp.data.repositories

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.test.spacedemoapp.data.common.repositories.RemoteRoverPhotosDataStore
import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPhotosRxPagingSource @Inject constructor(
    private val remoteRoverPhotosDataStore: RemoteRoverPhotosDataStore,
) : RxPagingSource<Int, RoverPhoto>() {


    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, RoverPhoto>> {
        var currentLoadingPageKey = params.key ?: 1

        return remoteRoverPhotosDataStore.getPhotos(
            earthDate = "2021-10-10",
            camera = "fhaz",
            page = currentLoadingPageKey,
            apiKey = "DEMO_KEY"
        )
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, currentLoadingPageKey) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(
        data: List<RoverPhoto>,
        currentLoadingPageKey: Int
    ): LoadResult<Int, RoverPhoto> {
        val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

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