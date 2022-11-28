package com.oyermolov.githubapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class DatabaseRepositoryEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String,
    val ownerName: String,
)