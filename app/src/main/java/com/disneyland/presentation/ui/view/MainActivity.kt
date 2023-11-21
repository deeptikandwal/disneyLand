package com.disneyland.presentation.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import com.disneyland.R
import com.disneyland.ScreenDestination
import com.disneyland.presentation.model.Character
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
                    TopAppBar(
                        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                        title = {
                            Text(
                                text = stringResource(R.string.title_home_screen),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        navigationIcon = {
                            Icon(painter = painterResource(R.drawable.ic_back),
                                tint = MaterialTheme.colorScheme.onSurface,
                                contentDescription = null,
                                modifier = Modifier.padding(5.dp).clickable {
                                    //back press
                                })
                        }
                    )
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
        }

        NavHost(navController, startDestination = ScreenDestination.Home.route) {
            composable(route = ScreenDestination.Home.route) {
                DisneyListScreen(isLoading, showError, disneyCharacters) { id ->
                    disneyCharactersViewModel.navigate(
                        DisneyListScreenSideEffect.NavigateToDetailsScreen(id)
                    )
                }
            }
            composable(route = ScreenDestination.Details.route) {
                val id = it.arguments?.getString("id")
                DisneyDetailScreen(id, disneyCharactersViewModel)
            }
        }
    }
}

