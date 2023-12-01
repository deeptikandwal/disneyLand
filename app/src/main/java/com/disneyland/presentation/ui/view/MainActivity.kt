package com.disneyland.presentation.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import com.disneyland.AppConstants
import com.disneyland.R
import com.disneyland.ScreenDestination
import com.disneyland.presentation.model.Character
import com.disneyland.presentation.ui.base.AppTopBar
import com.disneyland.presentation.ui.base.NotFound
import com.disneyland.presentation.ui.base.ProgressBar
import com.disneyland.presentation.ui.components.DisneyDetailScreen
import com.disneyland.presentation.ui.components.DisneyDetailScreenIntent
import com.disneyland.presentation.ui.components.DisneyDetailScreenSideEffect
import com.disneyland.presentation.ui.components.DisneyDetailScreenViewState
import com.disneyland.presentation.ui.components.DisneyListScreen
import com.disneyland.presentation.ui.components.DisneyListScreenIntent
import com.disneyland.presentation.ui.components.DisneyListScreenSideEffect
import com.disneyland.presentation.ui.components.DisneyListScreenViewState
import com.disneyland.presentation.ui.theme.DisneyLandTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val disneyCharactersViewModel: DisneyCharactersViewModel by viewModels()
    private val disneyDetailScreenViewModel: DisneyDetailScreenViewModel by viewModels()
    private var isLoading by mutableStateOf(false)
    private var showError by mutableStateOf(false)
    private lateinit var disneyCharacters: Flow<PagingData<Character>>

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(disneyCharactersViewModel) {
            sendIntent(DisneyListScreenIntent.FetchCharactersList)

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        disneyCharactersViewModel.viewState.collectLatest { viewState ->
                            when (viewState) {
                                is DisneyListScreenViewState.LOADING -> isLoading = true
                                is DisneyListScreenViewState.SUCCESS -> {
                                    disneyCharacters = viewState.data
                                }

                                is DisneyListScreenViewState.ERROR -> {
                                    showError = true
                                }
                            }
                        }
                    }

                }
            }

        }

        setContent {
            DisneyLandTheme(true) {
                val navController = rememberNavController()

                Scaffold(topBar = {
                    AppTopBar {
                        if (navController.currentDestination?.route == ScreenDestination.Home.route)
                            disneyCharactersViewModel.sendIntent(DisneyListScreenIntent.NavigateUp)
                        else if (navController.currentDestination?.route == ScreenDestination.Details.route) {
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
                if (::disneyCharacters.isInitialized) {
                    DisneyListScreen(isLoading, showError, disneyCharacters,
                        { id ->
                            disneyCharactersViewModel.sendIntent(
                                DisneyListScreenIntent.NavigateToDetails(
                                    id
                                )
                            )
                        }, {
                            disneyCharactersViewModel.handleLoadState(it)
                        })
                } else {
                    NotFound()
                }

            }
            composable(route = ScreenDestination.Details.route) {
                val id = it.arguments?.getString(AppConstants.ID)
                LaunchedEffect(Unit) {
                    disneyDetailScreenViewModel.sendIntent(
                        DisneyDetailScreenIntent.FetchCharacterById(
                            id!!
                        )
                    )
                }
                val state by disneyDetailScreenViewModel.viewState.collectAsState()
                when (state) {
                    is DisneyDetailScreenViewState.LOADING -> ProgressBar()
                    is DisneyDetailScreenViewState.SUCCESS -> DisneyDetailScreen((state as DisneyDetailScreenViewState.SUCCESS).data)
                    else -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(stringResource(R.string.data_not_found))
                        }
                    }
                }
            }
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
                            is DisneyListScreenSideEffect.NavigateToDetailsScreen -> navController.navigate(
                                ScreenDestination.Details.createRoute(
                                    (sideEffectHome as DisneyListScreenSideEffect.NavigateToDetailsScreen).id
                                )
                            )

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


}


