package com.waldemlav.githubtesttask.app

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.waldemlav.githubtesttask.data.database.dao.RepositoryDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class DownloadCompletedReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repositoryDao: RepositoryDao

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.DOWNLOAD_COMPLETE") {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id != -1L) {
                runBlocking {
                    repositoryDao.update(id, getCurrentTimeString())
                }
            }
        }
    }

    private fun getCurrentTimeString(): String {
        val currentTime = LocalDateTime.now(ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        return currentTime.format(formatter)
    }
}