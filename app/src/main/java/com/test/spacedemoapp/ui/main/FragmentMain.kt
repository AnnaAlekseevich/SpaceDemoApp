package com.test.spacedemoapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.test.itemdetails.DetailsActivity
import com.test.spacedemoapp.SpaceDemoApp
import com.test.spacedemoapp.data.repositories.RoverPhotosRepository
import com.test.spacedemoapp.databinding.FragmentMainBinding
import com.test.spacedemoapp.domain.models.RoverPhoto
import com.test.spacedemoapp.ui.adapter.RoverPhotosListAdapter
import io.reactivex.Observable
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

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
        SpaceDemoApp.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            presenter.onPhotoClicked(photo)

            Log.d("PHOTOCLICK", "presenter.onPhotoClicked(photo)")
        }
        binding.photosRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = photoListAdapter
        }

    }

    override fun resetPhotosList() {
        photoListAdapter?.refresh()
    }

    override fun showInternetConnectionError() {
        view?.let {
            Snackbar.make(it, "Please check your internet connection", Snackbar.LENGTH_LONG)
                .setAction("OK", View.OnClickListener { /*Take Action*/ }).show()
        }
        Log.d("NETWORKCONNEKTION", "Internet don't available")
    }

    override fun showException(errorMessage: String) {
        Log.d("AdapterData", "errorMessage + $errorMessage")
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

    override fun openDetailsActivity(photoForDetails: String) {
        val launchIntent: Intent? =
            Intent(context, DetailsActivity::class.java)
        launchIntent?.putExtra("photo", photoForDetails)
        if (launchIntent != null) {
            startActivity(launchIntent) //null pointer check in case package name was not found
        }
    }

}