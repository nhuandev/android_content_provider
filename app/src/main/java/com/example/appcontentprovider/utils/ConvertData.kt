package com.example.appcontentprovider.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.graphics.scale
import java.io.ByteArrayOutputStream

class ConvertData(private val context: Context) {
    fun uriToByteArray(uri: Uri): ByteArray {
        return context.contentResolver.openInputStream(uri)?.use {
            it.readBytes()
        } ?: ByteArray(0)
    }

    fun byteArrayToBitmap(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    fun resizeBitmap(bitmap: Bitmap): Bitmap {
        val maxSize = 512
        val width = bitmap.width
        val height = bitmap.height

        val scale = minOf(maxSize / width.toFloat(), maxSize / height.toFloat())
        val newWidth = (width * scale).toInt()
        val newHeight = (height * scale).toInt()

        return bitmap.scale(newWidth, newHeight)
    }

    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val resized = resizeBitmap(bitmap)
        val outputStream = ByteArrayOutputStream()
        resized.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        return outputStream.toByteArray()
    }
}
