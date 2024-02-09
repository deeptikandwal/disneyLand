package com.disneyLand.ui.view.screens.home

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.disneyLand.ui.components.ErrorScreen
import com.disneyLand.ui.components.ListScreen
import com.disneyLand.ui.components.loadProgressBar

@Composable
fun handleViewState(
    disneyCharactersViewModel: DisneyCharactersViewModel,
    goToDetailsScreen: (Int) -> Unit
) {
    val viewState by disneyCharactersViewModel.viewState.collectAsState()
    when (viewState) {
        is DisneyListMviContract.DisneyListScreenViewState.Loading -> {
            loadProgressBar(true)
        }

        is DisneyListMviContract.DisneyListScreenViewState.Success -> {
            loadProgressBar(false)
            HandleUiOnSuccess(viewState as DisneyListMviContract.DisneyListScreenViewState.Success, goToDetailsScreen)
        }

        is DisneyListMviContract.DisneyListScreenViewState.Error -> {
            loadProgressBar(false)
            ErrorScreen(true)
        }
    }
}

@Composable
private fun HandleUiOnSuccess(
    viewState: DisneyListMviContract.DisneyListScreenViewState,
    goToDetailsScreen: (Int) -> Unit
) {
    val disneyCharacters =
        (viewState as DisneyListMviContract.DisneyListScreenViewState.Success).data

    val listState: LazyGridState = rememberLazyGridState()
    ListScreen(listState, disneyCharacters, goToDetailsScreen)
}
