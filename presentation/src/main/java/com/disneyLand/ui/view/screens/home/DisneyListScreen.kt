package com.disneyLand.ui.view.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.disneyLand.model.Character
import com.disneyLand.ui.components.CustomCard
import com.disneyLand.ui.components.CustomImage
import com.disneyLand.ui.components.IntroText
import com.disneyLand.ui.components.PlaceHolderText
import com.disneyLand.ui.components.loadProgressBar
import com.disneyLand.ui.theme.Dimen.UI_SIZE_15_SP
import com.disneyLand.ui.theme.Dimen.UI_SIZE_200_DP

private const val fixedGridCount = 2

@Composable
fun DisneyListScreen(
    navigateToDetailsScreen: (DisneyListMviContract.DisneyListScreenSideEffect) -> Unit,
    navigateUp: () -> Unit
) {
    val disneyCharactersViewModel = hiltViewModel<DisneyCharactersViewModel>()
    with(disneyCharactersViewModel) {
        fetchList(this)
        handleViewState(this) { id ->
            disneyCharactersViewModel.sendIntent(
                DisneyListMviContract.DisneyListScreenIntent.NavigateToDetails(
                    id
                )
            )
        }
        handleSideEffect(
            this,
            {
                navigateToDetailsScreen(it)
            },
            {
                navigateUp()
            }
        )
        BackHandler {
            sendIntent(DisneyListMviContract.DisneyListScreenIntent.NavigateUp)
        }
    }
}

@Composable
private fun fetchList(disneyCharactersViewModel: DisneyCharactersViewModel) {
    var initialApiCalled by rememberSaveable { mutableStateOf(false) }
    if (!initialApiCalled) {
        LaunchedEffect(Unit) {
            disneyCharactersViewModel.sendIntent(DisneyListMviContract.DisneyListScreenIntent.FetchCharactersList)
            initialApiCalled = true
        }
    }
}

@Composable
private fun handleViewState(
    disneyCharactersViewModel: DisneyCharactersViewModel,
    goToDetailsScreen: (Int) -> Unit
) {
    val viewState by disneyCharactersViewModel.viewState.collectAsState()
    when (viewState) {
        is DisneyListMviContract.DisneyListScreenViewState.Loading -> {
            loadProgressBar(true)
        }

        is DisneyListMviContract.DisneyListScreenViewState.Success -> {
            HandleUiOnSuccess(viewState, disneyCharactersViewModel, goToDetailsScreen)
        }

        is DisneyListMviContract.DisneyListScreenViewState.Error -> {
            loadProgressBar(false)
            PlaceHolderText()
        }
    }
}

@Composable
private fun handleSideEffect(
    disneyCharactersViewModel: DisneyCharactersViewModel,
    navigateToDetailsScreen: (DisneyListMviContract.DisneyListScreenSideEffect) -> Unit,
    navigateUp: () -> Unit
) {
    val sideEffect by disneyCharactersViewModel.sideEffect.collectAsState(0)
    when (sideEffect) {
        is DisneyListMviContract.DisneyListScreenSideEffect.NavigateToDetailsScreen -> {
            navigateToDetailsScreen(sideEffect as DisneyListMviContract.DisneyListScreenSideEffect)
        }

        is DisneyListMviContract.DisneyListScreenSideEffect.NavigateUp -> {
            navigateUp()
        }
    }
}

@Composable
private fun HandleUiOnSuccess(
    viewState: DisneyListMviContract.DisneyListScreenViewState,
    disneyCharactersViewModel: DisneyCharactersViewModel,
    goToDetailsScreen: (Int) -> Unit
) {
    val disneyCharacters =
        (viewState as DisneyListMviContract.DisneyListScreenViewState.Success).data

    val listState: LazyGridState = rememberLazyGridState()
    val characters = disneyCharacters.collectAsLazyPagingItems()
    val pagingLoadStates = characters.loadState.mediator ?: characters.loadState.source
    LaunchedEffect(pagingLoadStates) {
        disneyCharactersViewModel.handleLoadState(pagingLoadStates)
    }
    ListScreen(listState, characters, goToDetailsScreen)
}

@Composable
private fun ListScreen(
    listState: LazyGridState,
    characters: LazyPagingItems<Character>,
    goToDetailsScreen: (Int) -> Unit
) {
    var inProgress by remember { mutableStateOf(true) }
    loadProgressBar(inProgress)
    Column {
        IntroText()
        LazyVerticalGrid(GridCells.Fixed(fixedGridCount), state = listState) {
            items(characters.itemCount) { index ->
                gridItem(characters, index, goToDetailsScreen)
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
private fun gridItem(
    characters: LazyPagingItems<Character>,
    index: Int,
    goToDetailsScreen: (Int) -> Unit
) {
    CustomCard({
        Box(Modifier.height(UI_SIZE_200_DP).fillMaxWidth()) {
            CustomImage(characters[index]?.image)
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = characters[index]?.name.toString(),
                    fontSize = UI_SIZE_15_SP,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.onSurface)
                )
            }
        }
    }, {
        characters[index]?.id?.let { goToDetailsScreen(it) }
    })
}
