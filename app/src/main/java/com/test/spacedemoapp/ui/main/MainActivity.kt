package com.test.spacedemoapp.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.test.photodetails.ui.DetailsActivity
import com.test.spacedemoapp.SpaceDemoApp
import com.test.spacedemoapp.data.repositories.RoverPhotosRepository
import com.test.spacedemoapp.databinding.ActivityMainBinding
import com.test.spacedemoapp.domain.models.RoverPhoto
import com.test.spacedemoapp.domain.net.networkconnection.NetWorkConnection
import com.test.spacedemoapp.ui.adapter.RoverPhotosListAdapter
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainActivityView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var photoListAdapter: RoverPhotosListAdapter

    @Inject
    lateinit var repository: RoverPhotosRepository

    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    @ProvidePresenter
    fun provideDetailsPresenter(): MainActivityPresenter? {
        return MainActivityPresenter(repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        SpaceDemoApp.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.photosRecyclerView.layoutManager = LinearLayoutManager(this)
        setContentView(binding.root)

        setupPhotoList()
    }

    private fun setupPhotoList() {
        photoListAdapter = RoverPhotosListAdapter() { photo ->
            presenter.onPhotoClicked(photo)

            Log.d("PHOTOCLICK", "presenter.onPhotoClicked(photo)")
        }
        binding.photosRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = photoListAdapter
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
        photoListAdapter.submitData(lifecycle, pagingData)
        photoListAdapter.notifyDataSetChanged()
    }

    override fun openDetailsActivity(photoForDetails: String) {
        Log.d("PHOTOCLICK", "openDetailsActivity + $photoForDetails")
        //val launchIntent: Intent?  = packageManager.getLaunchIntentForPackage("com.test.itemdetails.ui.DetailsActivity")
        val launchIntent: Intent? =
            Intent(this, DetailsActivity::class.java)
        //packageManager.getLaunchIntentForPackage("package com.test.itemdetails.ui.DetailsActivity")
//        val launchIntent = Intent()
//        intent.setClassName(this@MainActivity, DetailsActivity::class)
        launchIntent?.putExtra("photo", photoForDetails)
        if (launchIntent != null) {
            startActivity(launchIntent) //null pointer check in case package name was not found
        }

    }

    //todo add BroadcastReviser
    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            presenter.setInternetAvailable(NetWorkConnection.isInternetAvailable(this@MainActivity))
        } else {
            showInternetConnectionError()
        }
    }

    override fun showInternetConnectionError() {
        Snackbar.make(
            findViewById(android.R.id.content),
            "Please check your internet connection",
            Snackbar.LENGTH_LONG
        ).setAction("OK", View.OnClickListener { /*Take Action*/ }).show()

        Log.d("NETWORKCONNEKTION", "Internet don't available")
    }


}