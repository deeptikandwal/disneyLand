package com.disneyLand.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.disneyLand.ui.view.ScreenDestination

private const val ID = "id"
private const val ID_REDUX = "/{id}"
private const val DEFAULT = ""

@Composable
fun CustomNavGraph(
    navController: NavHostController,
    setHomeScreen: @Composable () -> Unit,
    setDetailsScreen: @Composable (NavBackStackEntry) -> Unit
) {
    NavHost(navController, startDestination = ScreenDestination.Home.route) {
        composable(route = ScreenDestination.Home.route) {
            setHomeScreen()
        }
        composable(
            route = ScreenDestination.Details.route + ID_REDUX,
            arguments = listOf(
                navArgument(ID) {
                    defaultValue = DEFAULT
                    type = NavType.StringType
                }
            )
        ) {
            setDetailsScreen(it)
        }
    }
}
