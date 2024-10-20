package com.waldemlav.githubtesttask.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_repositories")
data class RepositoryEntity(
    @PrimaryKey val downloadId: Long,
    val downloadTime: String?,
    val repoName: String,
    val username: String
)