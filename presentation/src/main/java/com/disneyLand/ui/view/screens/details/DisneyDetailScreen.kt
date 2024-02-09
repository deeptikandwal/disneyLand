package com.disneyLand.ui.view.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DisneyDetailScreen(
    navigateUp: () -> Unit
) {
    val disneyDetailScreenViewModel = hiltViewModel<DisneyDetailScreenViewModel>()
    with(disneyDetailScreenViewModel) {
        handleViewState(this)
        handleSideEffect(this) {
            navigateUp()
        }
        BackHandler {
            navigateUp()
        }
    }
}
