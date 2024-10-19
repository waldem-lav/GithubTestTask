package com.waldemlav.githubtesttask.data.repository

import com.waldemlav.githubtesttask.data.network.UserService
import com.waldemlav.githubtesttask.data.network.asResult
import com.waldemlav.githubtesttask.data.network.model.GithubRepository
import javax.inject.Inject
import com.waldemlav.githubtesttask.data.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository @Inject constructor(private val apiService: UserService) {

    fun getUserRepos(user: String): Flow<Result<List<GithubRepository>>> {
        return flow { emit(apiService.getUserRepos(user)) }.asResult()
    }
}