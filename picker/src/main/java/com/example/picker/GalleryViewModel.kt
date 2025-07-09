package com.example.picker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GalleryRepository(application.contentResolver)

    val pagedImages = repository.getPagedImages().flow.cachedIn(viewModelScope)
}
