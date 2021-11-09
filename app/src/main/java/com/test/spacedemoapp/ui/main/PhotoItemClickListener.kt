package com.test.spacedemoapp.ui.main

import com.test.spacedemoapp.domain.models.RoverPhoto

interface PhotoItemClickListener {
    fun onPhotoClicked (roverPhoto: RoverPhoto)
}