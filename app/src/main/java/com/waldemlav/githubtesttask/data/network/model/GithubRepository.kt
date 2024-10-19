package com.waldemlav.githubtesttask.data.network.model

import com.google.gson.annotations.SerializedName

data class GithubRepository(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("html_url") val htmlUrl: String
)