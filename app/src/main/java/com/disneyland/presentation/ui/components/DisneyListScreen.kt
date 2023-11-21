package com.disneyland.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.disneyland.presentation.model.Character
import kotlinx.coroutines.flow.Flow

@Composable
fun DisneyListScreen(
    isLoading: Boolean,
    showError: Boolean,
    disneyCharacters: Flow<PagingData<Character>>,
    goToDetailsScreen: (id: Int) -> Unit,
) {
    val listState: LazyGridState = rememberLazyGridState()
    var inProgress by remember { mutableStateOf(true) }
    loadProgressBar(isLoading || inProgress)
    val characters = disneyCharacters.collectAsLazyPagingItems()
    LazyVerticalGrid(GridCells.Fixed(2), state = listState) {
        items(characters.itemCount) { index ->
            Card(
                modifier = Modifier.padding(10.dp).clickable {
                    goToDetailsScreen(characters[index]?.id!!)
                },
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Box(Modifier.height(200.dp).fillMaxWidth()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(characters[index]?.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                        Text(
                            text = characters[index]?.name!!,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.onSurface)
                        )
                    }
                }

            }
        }
        when (characters.loadState.append) {
            is LoadState.NotLoading -> inProgress = false
            LoadState.Loading -> inProgress = true

            else -> {
//no action required
            }
        }
    }

}

@Composable
private fun loadProgressBar(isLoading: Boolean) {
    if (isLoading) {
        Box(Modifier.size(100.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.primary,
            )
        }

    }
}