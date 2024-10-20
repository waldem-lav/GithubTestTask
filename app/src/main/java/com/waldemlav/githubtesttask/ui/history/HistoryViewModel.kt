package com.waldemlav.githubtesttask.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waldemlav.githubtesttask.data.database.dao.RepositoryDao
import com.waldemlav.githubtesttask.data.database.entity.RepositoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HistoryScreenState(val downloadedRepos: List<RepositoryEntity> = listOf())

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repositoryDao: RepositoryDao
) : ViewModel() {

    private val _screenState = MutableStateFlow(HistoryScreenState())
    val screenState = _screenState.asStateFlow()

    init { getDownloadedRepos() }

    private fun getDownloadedRepos() {
        viewModelScope.launch {
            repositoryDao.getAll().collect {
                _screenState.value = HistoryScreenState(it)
            }
        }
    }
}