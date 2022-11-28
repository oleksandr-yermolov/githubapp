package com.oyermolov.githubapp.data.remote.entities

import com.google.gson.annotations.SerializedName

data class ApiUserEntity(
    @SerializedName("id")
    val id: Long,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("public_repos")
    val publicRepos: Int = 0,
    @SerializedName("followers")
    val followers: Int = 0,
    @SerializedName("following")
    val following: Int = 0
)