package com.lerie_valerie.newsfeed.data.remote.coil.repository

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

class BitmapSaveRepositoryImpl @Inject constructor(private val context: Context) : BitmapSaveRepository {
//    fun saveBitmapToStorage(bitmap: Bitmap) {
//        val filename = "${System.currentTimeMillis()}.jpg"
//        var fos: OutputStream? = null
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            contentResolver?.also { resolver ->
//                val contentValues = ContentValues().apply {
//                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
//                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
//                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
//                }
//                val imageUri: Uri? =
//                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//                fos = imageUri?.let { resolver.openOutputStream(it) }
//            }
//        } else {
//            val imagesDir =
//                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//            val image = File(imagesDir, filename)
//            fos = FileOutputStream(image)
//        }
//        fos?.use {
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
//            toast("Saved to Photos")
//        }
//    }

    override fun saveBitmapToStorage(bitmap: Bitmap, imageName: String) {
//                val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null



//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            contentResolver?.also { resolver ->
//                val contentValues = ContentValues().apply {
//                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
//                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
//                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
//                }
//                val imageUri: Uri? =
//                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//                fos = imageUri?.let { resolver.openOutputStream(it) }
//            }
//        } else {
//            val imagesDir =
//                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val dirPath = context.filesDir.absolutePath.toString() + File.separator + "Images"
            val image = File(dirPath, imageName)
            fos = FileOutputStream(image)
//        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }

//    fun createFolder() {
//        val dirPath = context.filesDir.absolutePath.toString() + File.separator + "Images"
//        val folder = File(dirPath)
//        if (!folder.exists()) {
//            folder.mkdir()
//            Log.i("IO", "" + folder.exists())
//        }
//    }
}