package com.oyermolov.githubapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oyermolov.githubapp.data.database.entities.DatabaseRepositoryEntity

@Dao
interface RepositoriesDao {
    @Query("SELECT * from repositories")
    suspend fun getAll(): List<DatabaseRepositoryEntity>

    @Query("SELECT * from repositories WHERE ownerName = :ownerName")
    suspend fun getRepositoriesByOwnerName(ownerName: String): List<DatabaseRepositoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(repositories: List<DatabaseRepositoryEntity>)
}