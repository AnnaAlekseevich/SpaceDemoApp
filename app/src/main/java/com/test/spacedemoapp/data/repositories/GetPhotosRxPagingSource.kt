package com.test.spacedemoapp.data.repositories

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
            apiKey = "gNsHlfPRJY7iFwXdTsIzdNNO7YyPipOIg79CFjK1",
            perPage = 25
        )
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, currentLoadingPageKey) }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }

    private fun toLoadResult(
        data: List<RoverPhoto>,
        currentLoadingPageKey: Int
    ): LoadResult<Int, RoverPhoto> {

        //this check does not allow error "429"
        if (data.isNullOrEmpty()) {
            return LoadResult.Error(Throwable("EmptyListFromRepo"))
        }

        val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
        return LoadResult.Page(
            data = data,
            prevKey = prevKey,
            nextKey = currentLoadingPageKey.plus(1)
        )
    }

    override fun getRefreshKey(state: PagingState<Int, RoverPhoto>): Int? {
        return state.anchorPosition
    }

}