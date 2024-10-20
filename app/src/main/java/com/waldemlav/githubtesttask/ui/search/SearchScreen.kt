package com.waldemlav.githubtesttask.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Star
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
import androidx.compose.ui.res.painterResource
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
            Row {
                Column(Modifier.weight(1f)) {
                    Text(text = item.name, style = MaterialTheme.typography.titleLarge)
                    item.language?.let { language ->
                        Spacer(Modifier.height(16.dp))
                        Text(text = language)
                    }
                }
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Outlined.Star, stringResource(R.string.stargazers_count))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = item.starCount.toString(), style = MaterialTheme.typography.titleLarge)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painterResource(R.drawable.icon_fork_20),
                        stringResource(R.string.forks_count),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = item.forksCount.toString(), style = MaterialTheme.typography.titleLarge)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            item.description?.let { description ->
                Text(text = description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
            }
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
            userRepos = listOf(
                GithubRepository(
                    1,
                    "FakeRepo",
                    "",
                    Owner("User#12"),
                    "Yet another piece of software",
                    "C++",
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
                    3,
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
            0
        ),
        downloadZip = {}
    )
}