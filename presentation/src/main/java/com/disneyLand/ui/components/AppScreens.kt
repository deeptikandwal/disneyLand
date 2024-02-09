package com.disneyLand.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.disneyLand.ui.view.ScreenDestination
import com.disneyLand.ui.view.screens.details.DisneyDetailScreen
import com.disneyLand.ui.view.screens.home.DisneyListMviContract
import com.disneyLand.ui.view.screens.home.DisneyListScreen

private const val ID_REDUX = "/{id}"
private const val REGEX_DETAILS = "/"

@Composable
fun AppScreens(
    navController: NavHostController,
    paddingValues: PaddingValues,
    onBackPressedDispatcher: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
        color = MaterialTheme.colorScheme.background
    ) {
        NavGraph(
            navController,
            {
                setHomeScreen(navController) {
                    onBackPressedDispatcher()
                }
            },
            {
                setDetailsScreen(navController)
            }
        )
    }
}

@Composable
private fun setDetailsScreen(
    navController: NavHostController
) {
    DisneyDetailScreen {
        navController.navigate(ScreenDestination.Home.route)
    }
}

@Composable
private fun setHomeScreen(
    navController: NavHostController,
    onBackPressedDispatcher: () -> Unit
) {
    DisneyListScreen(
        { sideEffect ->
            navController.navigate(
                ScreenDestination.Details.route + REGEX_DETAILS + ID_REDUX.replace(
                    oldValue = ID_REDUX,
                    newValue = (sideEffect as DisneyListMviContract.DisneyListScreenSideEffect.NavigateToDetailsScreen).id.toString()
                )
            )
        },
        {
            onBackPressedDispatcher()
        }
    )
}
