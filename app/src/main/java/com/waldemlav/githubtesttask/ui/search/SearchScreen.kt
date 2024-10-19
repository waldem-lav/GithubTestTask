package com.waldemlav.githubtesttask.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.waldemlav.githubtesttask.R
import com.waldemlav.githubtesttask.data.network.model.GithubRepository
import com.waldemlav.githubtesttask.data.network.model.Owner

@Composable
fun SearchScreen(
    state: SearchScreenState,
    searchUserRepos: (username: String) -> Unit,
    downloadZip: (repo: GithubRepository) -> Unit
) {
    var username by rememberSaveable { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(id = R.string.enter_username)) },
            trailingIcon = {
                IconButton(onClick = { searchUserRepos(username) }) {
                    Icon(Icons.Filled.Search, stringResource(R.string.search))
                }
            }
        )

        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (!state.errorMessage.isNullOrBlank()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = stringResource(R.string.error_try_again),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = state.errorMessage,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        } else {
            LazyColumn (Modifier.padding(top = 8.dp)) {
                items(items = state.userRepos, key = { repo -> repo.id }) { repo ->
                    RepositoryItem(repo, downloadZip)
                }
            }
        }
    }
}

@Composable
fun RepositoryItem(
    item: GithubRepository,
    downloadZip: (repo: GithubRepository) -> Unit
) {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                if (item.htmlUrl.isNotBlank())
                    uriHandler.openUri(item.htmlUrl)
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.name, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { downloadZip.invoke(item) }) {
                Text(text = stringResource(id = R.string.download_zip))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SearchScreenPreview() {
    SearchScreen(
        state = SearchScreenState(
            errorMessage = "Unknown Error",
            userRepos = listOf(
                GithubRepository(
                    1,
                    "FakeRepo",
                    "",
                    Owner("User#12"),
                    "Yet another piece of software",
                    "C++",
                    0,
                    0,
                    0
                ),
                GithubRepository(
                    2,
                    "FakeRepo2",
                    "",
                    Owner("User#51231"),
                    "Telegram",
                    "PHP",
                    1,
                    0,
                    3
                )
            )
        ),
        searchUserRepos = {},
        downloadZip = {}
    )
}

@Composable
@Preview(showBackground = true)
fun RepoItemPreview() {
    RepositoryItem(
        GithubRepository(
            1,
            "FakeRepo",
            "",
            Owner("User#12"),
            "Yet another piece of software",
            "C++",
            0,
            0,
            0
        ),
        downloadZip = {}
    )
}