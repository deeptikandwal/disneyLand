package com.disneyland.presentation.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import com.disneyland.ScreenDestination
import com.disneyland.presentation.model.Character
import com.disneyland.presentation.ui.base.AppTopBar
import com.disneyland.presentation.ui.components.DisneyDetailScreen
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
                        viewState.collectLatest { viewState ->
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
                Scaffold(topBar = {
                    AppTopBar{
                        disneyCharactersViewModel.sendIntent(DisneyListScreenIntent.NavigateUp)
                    }
                }) {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                            .padding(top = it.calculateTopPadding()),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        setScreens()
                    }
                }
            }
        }
    }

    @Composable
    private fun setScreens() {
        val navController = rememberNavController()

        val sideEffect by disneyCharactersViewModel.sideEffect.collectAsState(0)
        when (sideEffect) {
            is DisneyListScreenSideEffect.NavigateToDetailsScreen -> navController.navigate(
                ScreenDestination.Details.createRoute(
                    (sideEffect as DisneyListScreenSideEffect.NavigateToDetailsScreen).id
                )
            )

            is DisneyListScreenSideEffect.NavigateUp -> {
                navController.navigateUp()
            }
        }

        NavHost(navController, startDestination = ScreenDestination.Home.route) {
            composable(route = ScreenDestination.Home.route) {
                DisneyListScreen(isLoading, showError, disneyCharacters) { id ->
                    disneyCharactersViewModel.sendIntent(DisneyListScreenIntent.NavigateToDetails(id))
                }

            }
            composable(route = ScreenDestination.Details.route) {
                val id = it.arguments?.getString("id")
                DisneyDetailScreen(id, disneyCharactersViewModel)
            }
        }
    }

}


