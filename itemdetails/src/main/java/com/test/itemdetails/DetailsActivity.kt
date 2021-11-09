package com.test.itemdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.test.itemdetails.databinding.ActivityDetailsBinding
import moxy.MvpAppCompatActivity

class DetailsActivity: MvpAppCompatActivity(), DetailsActivityView {

    private lateinit var binding: ActivityDetailsBinding

//    @InjectPresenter
//    lateinit var presenter: DetailsActivityPresenter
//
//    @ProvidePresenter
//    fun provideDetailsPresenter(): DetailsActivityPresenter? = DetailsActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_details)
        val intent: Intent = getIntent()
        var photoImage: String = intent.getStringExtra("photo").toString()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp)

        showRoverPhoto(photoImage)
    }

    override fun showRoverPhoto(pictureUri: String) {
        Log.d("PHOTOCLICK", "showRoverPhoto + $pictureUri")
        pictureUri?.toUri()?.let {
            Glide
                .with(binding.roverPhotoDetails.context)
                .load(it)
                .into(binding.roverPhotoDetails)
        }
    }

    //for converting String to Uri
    private fun String.toUri(): Uri = Uri.parse(this)

    override fun onBackPressed() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}