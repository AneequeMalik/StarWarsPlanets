package com.aneeq.starwarsplanets.ui.list

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aneeq.starwarsplanets.ui.list.PlanetUiState
import com.aneeq.starwarsplanets.ui.detail.DetailActivity
import com.aneeq.starwarsplanets.ui.components.PlanetCard
import com.aneeq.starwarsplanets.ui.components.PlanetShimmerItem
import com.aneeq.starwarsplanets.utils.Constants
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull

/**
 * Displays the list of planets using Jetpack Compose.
 * Handles loading, success, and error UI states with pagination.
 */
@Composable
fun PlanetListScreen(viewModel: PlanetListViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val state = viewModel.uiState
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (state) {
            is PlanetUiState.Loading -> PlanetShimmerList()

            is PlanetUiState.Error -> ErrorView(message = state.message) {
                viewModel.fetchNextPage()
            }

            is PlanetUiState.Success -> {
                val planets = state.planets

                // Show planet list
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 4.dp, vertical = 6.dp),
                    state = listState
                ) {
                    items(planets) { planet ->
                        PlanetCard(
                            planet = planet,
                            onClick = {
                                // Launch DetailActivity
                                val intent = Intent(context, DetailActivity::class.java)
                                intent.putExtra(Constants.KEY_PLANET, planet)
                                context.startActivity(intent)
                            }
                        )
                    }
                    // Show shimmer loader while fetching more data
                    item {
                        if (viewModel.isLoadingMore()) {
                            PlanetShimmerItem()
                        }
                    }
                }

                // Detect scroll end to load next page
                LaunchedEffect(listState) {
                    snapshotFlow {
                        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    }
                        .distinctUntilChanged()
                        .filterNotNull()
                        .collectLatest { lastVisible ->
                            val total = listState.layoutInfo.totalItemsCount
                            if (
                                lastVisible >= total - 3 &&
                                !viewModel.isLoadingMore() &&
                                viewModel.hasNextPage()
                            ) {
                                viewModel.fetchNextPage()
                            }
                        }
                }
            }
        }
    }
}

/** Simple error view with retry option. */
@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Error: $message",
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

/** Displays shimmer placeholders while loading data. */
@Composable
fun PlanetShimmerList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        items(8) {
            PlanetShimmerItem()
        }
    }
}