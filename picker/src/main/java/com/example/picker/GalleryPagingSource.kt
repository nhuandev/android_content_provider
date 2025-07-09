package com.example.picker

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import androidx.paging.PagingSource
import androidx.paging.PagingState

class GalleryPagingSource(
    private val contentResolver: ContentResolver
) : PagingSource<Int, Uri>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Uri> {
        val page = params.key ?: 0
        val pageSize = params.loadSize
        val imageUris = mutableListOf<Uri>()

        val projection = arrayOf(MediaStore.Images.Media._ID)
        val sortOrder =
            "${MediaStore.Images.Media.DATE_ADDED} DESC LIMIT $pageSize OFFSET ${page * pageSize}"
        val queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val cursor = contentResolver.query(
            queryUri,
            projection,
            null,
            null,
            sortOrder
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val uri = ContentUris.withAppendedId(queryUri, id)
                imageUris.add(uri)
            }
        }

        return LoadResult.Page(
            data = imageUris,
            prevKey = if (page == 0) null else page - 1,
            nextKey = if (imageUris.size < pageSize) null else page + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Uri>): Int? {
        return state.anchorPosition?.let { anchor ->
            val closest = state.closestPageToPosition(anchor)
            closest?.prevKey?.plus(1) ?: closest?.nextKey?.minus(1)
        }
    }
}
