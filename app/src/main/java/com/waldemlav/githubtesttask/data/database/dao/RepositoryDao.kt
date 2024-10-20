package com.waldemlav.githubtesttask.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.waldemlav.githubtesttask.data.database.entity.RepositoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM downloaded_repositories WHERE downloadTime IS NOT NULL ORDER BY downloadId DESC")
    fun getAll(): Flow<List<RepositoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repository: RepositoryEntity)

    @Query("UPDATE downloaded_repositories SET downloadTime = :downloadTime WHERE downloadId = :downloadId")
    suspend fun update(downloadId: Long, downloadTime: String)
}