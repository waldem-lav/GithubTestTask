package com.waldemlav.githubtesttask.data.network.model

import com.google.gson.annotations.SerializedName

data class GithubRepository(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("description") val description: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("forks_count") val forksCount: Int,
    @SerializedName("stargazers_count") val starCount: Int
)