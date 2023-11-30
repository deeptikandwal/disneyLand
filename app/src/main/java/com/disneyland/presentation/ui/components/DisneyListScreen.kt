package com.disneyland.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.disneyland.R
import com.disneyland.presentation.model.Character
import com.disneyland.presentation.ui.base.CustomCard
import com.disneyland.presentation.ui.base.CustomImage
import com.disneyland.presentation.ui.base.NotFound
import com.disneyland.presentation.ui.base.ProgressBar
import kotlinx.coroutines.flow.Flow

@Composable
fun DisneyListScreen(
    isLoading: Boolean,
    showError: Boolean,
    disneyCharacters: Flow<PagingData<Character>>,
    goToDetailsScreen: (id: Int) -> Unit,
    handleLoadStates: (LoadStates) -> Unit,
) {
    val listState: LazyGridState = rememberLazyGridState()
    var inProgress by remember { mutableStateOf(true) }
    loadProgressBar(isLoading || inProgress)
    val characters = disneyCharacters.collectAsLazyPagingItems()
    val pagingLoadStates = characters.loadState.mediator ?: characters.loadState.source
    LaunchedEffect(pagingLoadStates) {
        handleLoadStates(pagingLoadStates)
    }

    if (showError) {
        NotFound()
    }
    Column {
        Text(
            text = stringResource(R.string.intro_text),
            fontSize = 15.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
        LazyVerticalGrid(GridCells.Fixed(2), state = listState) {
            items(characters.itemCount) { index ->
                CustomCard({
                    Box(Modifier.height(200.dp).fillMaxWidth()) {
                        CustomImage(characters[index]?.image)
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                            Text(
                                text = characters[index]?.name!!,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                                    .background(color = MaterialTheme.colorScheme.onSurface)
                            )
                        }
                    }

                }, {
                    goToDetailsScreen(characters[index]?.id!!)
                })

            }
            inProgress = when (characters.loadState.append) {
                is LoadState.NotLoading -> false
                is LoadState.Loading -> true
                else -> {
                    false
                }
            }
        }

    }

}



@Composable
private fun loadProgressBar(isLoading: Boolean) {
    if (isLoading) {
        ProgressBar()
    }
}