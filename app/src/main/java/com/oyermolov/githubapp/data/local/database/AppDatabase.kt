package com.oyermolov.githubapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oyermolov.githubapp.data.database.entities.DatabaseRepositoryEntity

@Database(
    entities = [DatabaseRepositoryEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoriesDao(): RepositoriesDao
}