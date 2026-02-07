package com.cypress.xingcodechallengeapplication.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.cypress.xingcodechallengeapplication.AppThemeColors.lightGreen
import com.cypress.xingcodechallengeapplication.Screen
import com.cypress.xingcodechallengeapplication.domain.XingRepoModel
import com.cypress.xingcodechallengeapplication.presentation.viewModel.XingViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun XingRepoRoute(navController: NavController) {

    val xingViewModel: XingViewModel = koinViewModel()
    val xingLazyPagingItems = xingViewModel.xingFlow.collectAsLazyPagingItems()
    val isRefreshing = xingLazyPagingItems.loadState.refresh is LoadState.Loading

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { xingLazyPagingItems.refresh() },
        state = rememberPullToRefreshState()
    ) {
        Scaffold { paddingValues ->

            if (xingLazyPagingItems.loadState.refresh is LoadState.Loading) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            }
            else if((xingLazyPagingItems.loadState.refresh is LoadState.Error ||
                        xingLazyPagingItems.loadState.refresh is LoadState.NotLoading) &&
                xingLazyPagingItems.itemCount == 0){

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Empty list",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Text(
                                text = "Empty list",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Button(onClick = { xingLazyPagingItems.refresh() }) {
                            Text("Retry")
                        }
                    }
                }

            }
            else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                ) {

                    items(
                        count = xingLazyPagingItems.itemCount,
                        key = { index -> xingLazyPagingItems[index]?.id ?: index }
                    ) { index ->

                        val item = xingLazyPagingItems[index]

                        item?.let { repo ->

                            val backgroundColor = if (repo.isFork) {
                                Color(lightGreen)
                            } else {
                                Color.White
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        navController.navigate(
                                            Screen.XingDetails.route
                                                .replace("{${Screen.ID_ARG}}", repo.id.toString())
                                        )
                                    },
                                colors = CardDefaults.cardColors(containerColor = backgroundColor),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {

                                Row(modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (!item.ownerAvatarUrl.isNullOrEmpty()) {
                                        SubcomposeAsyncImage(
                                            model = item.ownerAvatarUrl,
                                            contentDescription = "Owner Avatar",
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(CircleShape)
                                                .border(1.dp, Color.Gray, CircleShape)
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(CircleShape)
                                                .background(Color.Gray)
                                                .border(1.dp, Color.DarkGray, CircleShape),
                                            contentAlignment = androidx.compose.ui.Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Person,
                                                contentDescription = "Default Avatar",
                                                tint = Color.White
                                            )
                                        }
                                    }
                                    Spacer(Modifier.width(8.dp))
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {

                                        Text(
                                            text = repo.name ?: "Unnamed repository",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.primary
                                        )

                                        Text(
                                            text = repo.description ?: "No description provided.",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )

                                        Text(
                                            text = "Owner: ${repo.ownerLogin}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                            }

                        }

                    }

                    item {
                        if (xingLazyPagingItems.loadState.append is LoadState.Loading) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }


}
