package com.disneyLand.ui.view.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun handleSideEffect(
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
