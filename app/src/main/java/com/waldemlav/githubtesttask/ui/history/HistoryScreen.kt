package com.waldemlav.githubtesttask.ui.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.waldemlav.githubtesttask.R
import com.waldemlav.githubtesttask.data.database.entity.RepositoryEntity

@Composable
fun HistoryScreen(state: HistoryScreenState) {
    if (state.downloadedRepos.isEmpty()) {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            Text(
                text = stringResource(R.string.no_history),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    } else {
        LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
            items(items = state.downloadedRepos, key = { repo -> repo.downloadId }) { repo ->
                DownloadedRepositoryItem(
                    username = repo.username,
                    repoName = repo.repoName,
                    downloadTime = repo.downloadTime
                )
            }
        }
    }
}

@Composable
fun DownloadedRepositoryItem(username: String, repoName: String, downloadTime: String?) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column (modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                text = repoName,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "by $username",
                style = MaterialTheme.typography.bodyLarge
            )
            downloadTime?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = downloadTime,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DownloadsScreenComposablePreview() {
    HistoryScreen(
        state = HistoryScreenState(
            listOf(
                RepositoryEntity(
                    0,
                    "20/10/2024 13:30",
                    "Steam",
                    "Volvo"
                ),
                RepositoryEntity(
                    1,
                    "20/10/2024 13:31",
                    "Danbooru",
                    "idk"
                )
            )
        )
    )
}