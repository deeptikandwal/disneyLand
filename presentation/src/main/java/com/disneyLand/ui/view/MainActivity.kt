package com.disneyLand.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.PagingData
import com.disneyLand.model.Actor
import com.disneyLand.model.Character
import com.disneyLand.ui.base.AppTopBar
import com.disneyLand.ui.base.NotFound
import com.disneyLand.ui.base.ProgressBar
import com.disneyLand.ui.components.DisneyDetailScreen
import com.disneyLand.ui.components.DisneyDetailScreenIntent
import com.disneyLand.ui.components.DisneyDetailScreenSideEffect
import com.disneyLand.ui.components.DisneyDetailScreenViewState
import com.disneyLand.ui.components.DisneyListScreen
import com.disneyLand.ui.components.DisneyListScreenIntent
import com.disneyLand.ui.components.DisneyListScreenSideEffect
import com.disneyLand.ui.components.DisneyListScreenViewState
import com.disneyLand.ui.theme.DisneyLandTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val disneyCharactersViewModel: DisneyCharactersViewModel by viewModels()
    private val disneyDetailScreenViewModel: DisneyDetailScreenViewModel by viewModels()
    private var isHomeScreenLoading by mutableStateOf(false)
    private var showHomeScreenError by mutableStateOf(false)

    private var isDetailScreenLoading by mutableStateOf(false)
    private var showDetailScreenError by mutableStateOf(false)

    private lateinit var disneyCharacters: Flow<PagingData<Character>>
    private var actor: Actor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleViewStates()
        setComposeUi()
    }

    private fun handleViewStates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    disneyCharactersViewModel.viewState.collectLatest { viewState ->
                        when (viewState) {
                            is DisneyListScreenViewState.LOADING -> isHomeScreenLoading = true
                            is DisneyListScreenViewState.SUCCESS -> {
                                isHomeScreenLoading = false
                                disneyCharacters = viewState.data
                            }

                            is DisneyListScreenViewState.ERROR -> {
                                isHomeScreenLoading = false
                                showHomeScreenError = true
                            }
                        }
                    }
                }
                launch {
                    disneyDetailScreenViewModel.viewState.collectLatest { state ->
                        when (state) {
                            is DisneyDetailScreenViewState.LOADING -> {
                                isDetailScreenLoading = true
                                actor = null
                            }

                            is DisneyDetailScreenViewState.SUCCESS -> {
                                showDetailScreenError = false
                                isDetailScreenLoading = false

                                actor = state.data
                            }

                            is DisneyDetailScreenViewState.ERROR -> {
                                showDetailScreenError = false
                                isDetailScreenLoading = false

                                actor = null
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun setComposeUi() {
        setContent {
            DisneyLandTheme(true) {
                navController = rememberNavController()
                var initialApiCalled by rememberSaveable { mutableStateOf(false) }
                if (!initialApiCalled) {
                    LaunchedEffect(Unit) {
                        disneyCharactersViewModel.sendIntent(DisneyListScreenIntent.FetchCharactersList)
                        initialApiCalled = true
                    }
                }

                Scaffold(topBar = {
                    AppTopBar {
                        if (navController.currentDestination?.route == ScreenDestination.Home.route) {
                            disneyCharactersViewModel.sendIntent(DisneyListScreenIntent.NavigateUp)
                        } else if (navController.currentDestination?.route == ScreenDestination.Details.route + ID_REDUX) {
                            disneyDetailScreenViewModel.sendIntent(DisneyDetailScreenIntent.NavigateUp)
                        }
                    }
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
        handleSideEffects(navController)
        NavHost(navController, startDestination = ScreenDestination.Home.route) {
            composable(route = ScreenDestination.Home.route) {
                BackHandler {
                    disneyCharactersViewModel.sendIntent(DisneyListScreenIntent.NavigateUp)
                }
                setHomeScreen()
            }
            composable(
                route = ScreenDestination.Details.route + ID_REDUX,
                arguments = listOf(
                    navArgument(ID) {
                        defaultValue = ""
                        type = NavType.StringType
                    }
                )
            ) {
                BackHandler {
                    disneyDetailScreenViewModel.sendIntent(DisneyDetailScreenIntent.NavigateUp)
                }
                setDetailsScreen(it)
            }
        }
    }

    @Composable
    private fun setDetailsScreen(it: NavBackStackEntry) {
        val id = it.arguments?.getString(ID)
        LaunchedEffect(id) {
            disneyDetailScreenViewModel.sendIntent(
                DisneyDetailScreenIntent.FetchCharacterById(
                    id!!
                )
            )
        }
        if (actor != null) {
            DisneyDetailScreen(actor)
        } else {
            if (isDetailScreenLoading) {
                ProgressBar()
            }
            if (showDetailScreenError) {
                NotFound()
            }
        }
    }

    @Composable
    private fun setHomeScreen() {
        if (::disneyCharacters.isInitialized) {
            DisneyListScreen(
                isHomeScreenLoading,
                showHomeScreenError,
                disneyCharacters,
                { id ->
                    disneyCharactersViewModel.sendIntent(
                        DisneyListScreenIntent.NavigateToDetails(
                            id
                        )
                    )
                },
                {
                    disneyCharactersViewModel.handleLoadState(it)
                }
            )
        } else {
            NotFound()
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    private fun handleSideEffects(navController: NavHostController) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    disneyCharactersViewModel.sideEffect.collectLatest { sideEffectHome ->
                        when (sideEffectHome) {
                            is DisneyListScreenSideEffect.NavigateToDetailsScreen -> {
                                navController.navigate(
                                    ScreenDestination.Details.route + "/" + ID_REDUX.replace(
                                        oldValue = ID_REDUX,
                                        newValue = sideEffectHome.id.toString()
                                    )
                                )
                            }

                            is DisneyListScreenSideEffect.NavigateUp -> {
                                finish()
                            }
                        }
                    }
                }

                launch {
                    disneyDetailScreenViewModel.sideEffect.collectLatest { sideEffectDetails ->
                        when (sideEffectDetails) {
                            is DisneyDetailScreenSideEffect.NavigateUp -> {
                                navController.navigate(ScreenDestination.Home.route)
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val ID = "id"
        private const val ID_REDUX = "/{id}"
    }
}
