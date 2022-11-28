package com.oyermolov.githubapp.domain

data class UserEntity(
    val id: Long,
    val login: String,
    val name: String?,
    val avatarUrl: String?,
    val company: String?,
    val location: String?,
    val bio: String?,
    val publicRepos: Int,
    val followers: Int,
    val following: Int
)
