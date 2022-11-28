package com.oyermolov.githubapp.data.remote

import com.oyermolov.githubapp.data.remote.entities.ApiRepositoryEntity
import com.oyermolov.githubapp.data.remote.entities.ApiUserEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("/users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String) : List<ApiRepositoryEntity>

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username: String) : ApiUserEntity
}