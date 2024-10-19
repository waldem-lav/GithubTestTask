package com.waldemlav.githubtesttask.data.network.model

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("login") val login: String
)