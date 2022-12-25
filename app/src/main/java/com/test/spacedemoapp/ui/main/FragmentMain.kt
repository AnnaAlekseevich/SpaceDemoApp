package com.test.spacedemoapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.test.itemdetails.DetailsActivity
import com.test.spacedemoapp.R
import com.test.spacedemoapp.SpaceDemoApp
import com.test.spacedemoapp.data.repositories.RoverPhotosRepository
import com.test.spacedemoapp.databinding.FragmentMainBinding
import com.test.spacedemoapp.domain.models.RoverPhoto
import com.test.spacedemoapp.ui.adapter.ItemDecorationColumns
import com.test.spacedemoapp.ui.adapter.RoverPhotosListAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@AndroidEntryPoint
class FragmentMain: MvpAppCompatFragment(), MainView {

    private lateinit var binding: FragmentMainBinding
    private lateinit var photoListAdapter: RoverPhotosListAdapter

    @Inject
    lateinit var repository: RoverPhotosRepository

    @Inject
    lateinit var internetStateObservable: Observable<Boolean>

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun provideDetailsPresenter(): MainPresenter? {
        return MainPresenter(repository, internetStateObservable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        setupPhotoList()
        photoListAdapter.addLoadStateListener { loadStates ->
            if (loadStates.refresh == LoadState.Loading) {
                showProgress()
            } else {
                hideProgress()
            }
        }
        return binding.root
    }

    private fun setupPhotoList() {
        photoListAdapter = RoverPhotosListAdapter() { photo ->
            openDetailsScreen(photo.urlItemPhoto, photo.roverCamera.fullName, photo.rover.name)
        }
        binding.photosRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = photoListAdapter
        }
        binding.photosRecyclerView.addItemDecoration(
            ItemDecorationColumns(
                resources.getInteger(R.integer.photo_list_preview_columns),
                resources.getDimensionPixelSize(R.dimen.photos_list_spacing),
                true
            )
        )
    }

    override fun resetPhotosList() {
        photoListAdapter?.refresh()
    }

    override fun showInternetConnectionError() {
        view?.let {
            Snackbar.make(it, R.string.check_internet_connection, Snackbar.LENGTH_LONG)
                .setAction(R.string.OK, View.OnClickListener { /*Take Action*/ }).show()
        }
    }

    override fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

    override fun setPagingData(pagingData: PagingData<RoverPhoto>) {
        photoListAdapter.submitData(lifecycle, pagingData)
        photoListAdapter.notifyDataSetChanged()
    }

    override fun openDetailsScreen(
        photoForDetails: String,
        cameraName: String,
        roverName: String
    ) {
        val launchIntent = Intent(context, DetailsActivity::class.java)
        with(launchIntent) {
            putExtra("photo", photoForDetails)
            putExtra("CameraName", cameraName)
            putExtra("RoverName", roverName)
        }
        startActivity(launchIntent)
    }

}