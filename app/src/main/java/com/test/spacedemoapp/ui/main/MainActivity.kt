package com.test.spacedemoapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.spacedemoapp.R
import com.test.spacedemoapp.SpaceDemoApp
import com.test.spacedemoapp.data.common.repositories.RemoteRoverPhotosDataStore
import com.test.spacedemoapp.databinding.ActivityMainBinding
import com.test.spacedemoapp.domain.models.RoverPhoto
import com.test.spacedemoapp.ui.adapter.RoverPhotosListAdapter
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainActivityView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var photoListAdapter: RoverPhotosListAdapter

    @Inject
    lateinit var remoteRoverPhotosDataStore: RemoteRoverPhotosDataStore

    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    @ProvidePresenter
    fun provideDetailsPresenter(): MainActivityPresenter? {
        return MainActivityPresenter(remoteRoverPhotosDataStore)
    }

    init {
        Log.d("RoverPhotos", "getPhotos Main presenter")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        SpaceDemoApp.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.photosRecyclerView.layoutManager = LinearLayoutManager(this)
        setContentView(R.layout.activity_main)

        setupPhotoList()
    }

    private fun setupPhotoList() {
        photoListAdapter = RoverPhotosListAdapter() { photo -> }
        binding.photosRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = photoListAdapter
            Log.d("AdapterData", "photoListAdapter + ${photoListAdapter.itemCount}")
        }
    }

    override fun showException(errorMessage: String) {
        Log.d("AdapterData", "errorMessage + $errorMessage")
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun setPagingData(pagingData: PagingData<RoverPhoto>) {
        Log.d("AdapterData", "activity pagingData = ${pagingData}")
        photoListAdapter.submitData(lifecycle, pagingData)
    }


}