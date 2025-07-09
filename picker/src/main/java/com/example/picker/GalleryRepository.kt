package com.example.picker

import android.content.ContentResolver
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig

class GalleryRepository(private val contentResolver: ContentResolver) {

    fun getPagedImages(): Pager<Int, Uri> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GalleryPagingSource(contentResolver) }
        )
    }
}
