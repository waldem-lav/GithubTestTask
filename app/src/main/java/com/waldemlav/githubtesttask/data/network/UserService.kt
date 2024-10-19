package com.waldemlav.githubtesttask.data.network

import com.waldemlav.githubtesttask.data.network.model.GithubRepository
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users/{user}/repos")
    suspend fun getUserRepos(@Path("user") user: String): List<GithubRepository>
}