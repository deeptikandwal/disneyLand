package com.disneyLand.ui.view.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
@Composable
fun handleSideEffect(
    disneyDetailScreenViewModel: DisneyDetailScreenViewModel,
    navigateUp: () -> Unit
) {
    val sideEffectDetails by disneyDetailScreenViewModel.sideEffect.collectAsState(0)
    when (sideEffectDetails) {
        is DisneyDetailMviContract.DisneyDetailScreenSideEffect.NavigateUp -> {
            LaunchedEffect(Unit) {
                navigateUp()
            }
        }
    }
}
