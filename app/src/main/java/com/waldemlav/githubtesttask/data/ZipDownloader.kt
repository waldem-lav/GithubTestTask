package com.waldemlav.githubtesttask.data

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.waldemlav.githubtesttask.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ZipDownloader @Inject constructor(@ApplicationContext private val context: Context) {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    fun downloadFile(url: String, fileName: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setTitle(fileName)
            .setDescription(context.getString(R.string.downloading_repository))
            .setMimeType("application/zip")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        return downloadManager.enqueue(request)
    }
}