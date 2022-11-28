package com.oyermolov.githubapp.domain

data class RepositoryEntity(
    val id: Long,
    val name: String,
    val description: String,
    val ownerName: String
)