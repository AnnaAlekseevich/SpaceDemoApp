package com.test.spacedemoapp.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.spacedemoapp.R
import com.test.spacedemoapp.databinding.ItemListBinding
import com.test.spacedemoapp.domain.models.RoverPhoto

class RoverPhotosListAdapter(
    val itemClickListener: (photo: RoverPhoto) -> Unit
) :
    PagingDataAdapter<RoverPhoto, RoverPhotosListAdapter.RoverPhotosViewHolder>(DataDifferntiator) {


    inner class RoverPhotosViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemListBinding.bind(itemView)

        fun updatePhotoItem(roverPhoto: RoverPhoto) {
            binding.photo.setImageURI(roverPhoto.urlItemPhoto.toUri())
            binding.titleText.text = roverPhoto.rover.name
            binding.titleTextDescription.text = roverPhoto.camera.fullCameraName
            roverPhoto.urlItemPhoto?.toUri()?.let {
                Glide
                    .with(binding.photo.context)
                    .load(it)
                    .into(binding.photo)
            }
            binding.clItem.setOnClickListener {
                itemClickListener.invoke(roverPhoto)
            }
        }

    }

    override fun onBindViewHolder(holder: RoverPhotosViewHolder, position: Int) {
        getItem(position)?.let { holder.updatePhotoItem(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoverPhotosViewHolder {
        return RoverPhotosViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        )
    }

    object DataDifferntiator : DiffUtil.ItemCallback<RoverPhoto>() {

        override fun areItemsTheSame(oldItem: RoverPhoto, newItem: RoverPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RoverPhoto, newItem: RoverPhoto): Boolean {
            return oldItem == newItem
        }
    }
}

private fun String.toUri(): Uri = Uri.parse(this)
