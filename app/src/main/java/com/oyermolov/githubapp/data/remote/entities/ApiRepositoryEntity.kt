package com.oyermolov.githubapp.data.remote.entities

import com.google.gson.annotations.SerializedName

data class ApiRepositoryEntity(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("owner")
    val owner: ApiUserEntity
)