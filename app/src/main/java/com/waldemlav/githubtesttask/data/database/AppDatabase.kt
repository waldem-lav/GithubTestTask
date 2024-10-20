package com.waldemlav.githubtesttask.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.waldemlav.githubtesttask.data.database.dao.RepositoryDao
import com.waldemlav.githubtesttask.data.database.entity.RepositoryEntity

@Database(entities = [RepositoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}