package com.disneyLand.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.disneyLand.ui.components.createAppTopBar
import com.disneyLand.ui.theme.DisneyLandTheme
import com.disneyLand.ui.view.screens.details.DisneyDetailScreen
import com.disneyLand.ui.view.screens.home.DisneyListMviContract
import com.disneyLand.ui.view.screens.home.DisneyListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setComposeUi()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun setComposeUi() {
        setContent {
            DisneyLandTheme(true) {
                navController = rememberNavController()
                Scaffold(topBar = {
                    createAppTopBar(navController,this)
                }) {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                            .padding(top = it.calculateTopPadding()),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        setScreens(navController)
                    }
                }
            }
        }
    }

    @Composable
    private fun setScreens(navController: NavHostController) {
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

    @Composable
    private fun setDetailsScreen(it: NavBackStackEntry) {
        val id = it.arguments?.getString(ID)

        DisneyDetailScreen(id.toString()) {
            navController.navigate(ScreenDestination.Home.route)
        }
    }

    @Composable
    private fun setHomeScreen() {
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
                finish()
            })
    }

    companion object {
        private const val ID = "id"
        private const val ID_REDUX = "/{id}"
        private const val DEFAULT = ""
        private const val REGEX_DETAILS = "/"
    }
}
