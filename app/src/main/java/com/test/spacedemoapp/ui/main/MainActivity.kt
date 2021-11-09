package com.test.spacedemoapp.ui.main

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.test.itemdetails.DetailsActivity
import com.test.spacedemoapp.SpaceDemoApp
import com.test.spacedemoapp.data.repositories.RoverPhotosRepository
import com.test.spacedemoapp.databinding.ActivityMainBinding
import com.test.spacedemoapp.domain.models.RoverPhoto
import com.test.spacedemoapp.domain.net.networkconnection.ConnectivityReceiver
import com.test.spacedemoapp.ui.adapter.RoverPhotosListAdapter
import io.reactivex.Observable
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainActivityView{
    //ConnectivityReceiver.ConnectivityReceiverListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var photoListAdapter: RoverPhotosListAdapter

    @Inject
    lateinit var repository: RoverPhotosRepository

    @Inject
    lateinit var internetStateObservable: Observable<Boolean>

    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    @ProvidePresenter
    fun provideDetailsPresenter(): MainActivityPresenter? {
        return MainActivityPresenter(repository, internetStateObservable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        SpaceDemoApp.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        setContentView(binding.root)
        setupPhotoList()
        photoListAdapter.addLoadStateListener { loadStates ->
            if (loadStates.refresh == LoadState.Loading) {
                showProgress()
            } else {
                hideProgress()
            }
        }
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
        Log.d("PHOTOCLICK", "openDetailsActivity + $photoForDetails")
        val launchIntent: Intent? =
            Intent(this, DetailsActivity::class.java)
        launchIntent?.putExtra("photo", photoForDetails)
        if (launchIntent != null) {
            startActivity(launchIntent) //null pointer check in case package name was not found
        }

    }
//    override fun onResume() {
//        super.onResume()
//        ConnectivityReceiver.connectivityReceiverListener = this
//    }

    override fun showInternetConnectionError() {
        Snackbar.make(
            findViewById(android.R.id.content),
            "Please check your internet connection",
            Snackbar.LENGTH_LONG
        ).setAction("OK", View.OnClickListener { /*Take Action*/ }).show()

        Log.d("NETWORKCONNEKTION", "Internet don't available")
    }

//    //FOR BROADCAST
//    override fun onNetworkConnectionChanged(isConnected: Boolean) {
//        showStateInternetConnection(isConnected)
//    }
//
//    private fun showStateInternetConnection(isConnected: Boolean) {
//        if (isConnected) {
//            Snackbar.make(
//                findViewById(android.R.id.content),
//                "YOU HAVE THE INTERNET",
//                Snackbar.LENGTH_LONG
//            ).setAction("OK", View.OnClickListener { /*Take Action*/ }).show()
//        } else {
//            Snackbar.make(
//                findViewById(android.R.id.content),
//                "Please check your internet connection",
//                Snackbar.LENGTH_LONG
//            ).setAction("OK", View.OnClickListener { /*Take Action*/ }).show()
//        }
//        Log.d("NETWORKCONNEKTION", "Internet don't available")
//    }


}