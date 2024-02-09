package com.disneyLand.ui.view.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DisneyListScreen(
    navigateToDetailsScreen: (DisneyListMviContract.DisneyListScreenSideEffect) -> Unit,
    navigateUp: () -> Unit
) {
    val disneyCharactersViewModel = hiltViewModel<DisneyCharactersViewModel>()
    with(disneyCharactersViewModel) {
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
            navigateUp()
        }
    }
}
