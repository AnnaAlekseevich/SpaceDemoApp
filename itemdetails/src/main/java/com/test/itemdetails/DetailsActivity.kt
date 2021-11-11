package com.test.itemdetails

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.test.itemdetails.databinding.ActivityDetailsBinding
import moxy.MvpAppCompatActivity
import moxy.presenter.ProvidePresenter
import java.io.File
import java.io.FileOutputStream


class DetailsActivity: MvpAppCompatActivity(), DetailsActivityView {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent= intent

        val photoImage: String = intent.getStringExtra("photo").toString()
        val roverCameraName: String = intent.getStringExtra("CameraName").toString()
        val roverFullName: String = intent.getStringExtra("RoverName").toString()

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp)

        showRoverPhoto(photoImage)
        binding.cameraName.text = roverCameraName
        binding.roverName.text = roverFullName
    }

    override fun showRoverPhoto(pictureUri: String) {
        pictureUri?.toUri()?.let {
            Glide
                .with(binding.roverPhotoDetails.context)
                .load(it)
                .into(binding.roverPhotoDetails)
        }
    }

    //for converting String to Uri
    private fun String.toUri(): Uri = Uri.parse(this)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.share -> onShareImage()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater;
        inflater.inflate(R.menu.details_menu, menu)
        return true
    }

    private fun onShareImage(){
        var bitmap: Bitmap = (binding.roverPhotoDetails.drawable as BitmapDrawable).bitmap
        shareImageToAnotherApp(bitmap)
    }

    private fun shareImageToAnotherApp(bitmap: Bitmap){
        val uri: Uri = getmageToShare(bitmap)!!
        val intent = Intent(Intent.ACTION_SEND)
        // putting uri of image to be shared
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        // adding text to share
        intent.putExtra(Intent.EXTRA_TEXT, "Sharing Image")
        // Add subject Here
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
        // setting type to image
        intent.type = "image/png"
        // calling startactivity() to share
        startActivity(Intent.createChooser(intent, "Share Via"))
    }

    // Retrieving the url to share
    private fun getmageToShare(bitmap: Bitmap): Uri? {
        val imagefolder = File(cacheDir, "images")
        var uri: Uri? = null
        try {
            imagefolder.mkdirs()
            val file = File(imagefolder, "shared_image.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
            outputStream.flush()
            outputStream.close()
            uri = FileProvider.getUriForFile(this, "com.anni.shareimage.fileprovider", file)
        } catch (e: Exception) {
            Toast.makeText(this, "" + e.message, Toast.LENGTH_LONG).show()
        }
        return uri
    }

}