package com.waldemlav.githubtesttask.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waldemlav.githubtesttask.data.ZipDownloader
import com.waldemlav.githubtesttask.data.database.dao.RepositoryDao
import com.waldemlav.githubtesttask.data.database.entity.RepositoryEntity
import com.waldemlav.githubtesttask.data.network.model.GithubRepository
import com.waldemlav.githubtesttask.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.waldemlav.githubtesttask.data.network.Result.Error
import com.waldemlav.githubtesttask.data.network.Result.Loading
import com.waldemlav.githubtesttask.data.network.Result.Success
import com.waldemlav.githubtesttask.di.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userRepos: List<GithubRepository> = listOf()
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val zipDownloader: ZipDownloader,
    private val repositoryDao: RepositoryDao
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow(SearchScreenState())
    val searchScreenState = _searchScreenState.asStateFlow()

    fun searchUserRepos(username: String) {
        viewModelScope.launch {
            userRepo.getUserRepos(username).collect { result ->
                when (result) {
                    is Loading -> _searchScreenState.update { it.copy(isLoading = true) }
                    is Success -> _searchScreenState.update {
                        SearchScreenState(userRepos = result.data)
                    }
                    is Error -> _searchScreenState.update {
                        SearchScreenState(
                            errorMessage = result.exception.message ?: "Unknown error"
                        )
                    }
                }
            }
        }
    }

    fun downloadRepo(repo: GithubRepository) {
        val url = NetworkModule.BASE_URL + "repos/${repo.owner.login}/${repo.name}/zipball"
        val downloadId = zipDownloader.downloadFile(url, repo.name)
        viewModelScope.launch {
            repositoryDao.insert(
                RepositoryEntity(
                    downloadId = downloadId,
                    downloadTime = null,
                    repoName = repo.name,
                    username = repo.owner.login
                )
            )
        }
    }
}