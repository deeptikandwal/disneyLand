package com.disneyland.presentation

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.disneyland.R
import com.disneyland.ScreenDestination
import com.disneyland.presentation.ui.components.DisneyDetailScreen
import com.disneyland.presentation.ui.components.DisneyListScreen
import com.disneyland.presentation.ui.theme.DisneyLandTheme
import com.disneyland.presentation.viewmodel.DisneyCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val disneyCharactersViewModel: DisneyCharactersViewModel by viewModels()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                                fontWeight = FontWeight.Medium
                            )
                        },
                    )
                }) {
                    Surface(
                        modifier = Modifier.fillMaxSize().padding(top = it.calculateTopPadding()),
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
        NavHost(navController, startDestination =ScreenDestination.Home.route) {
            composable(route = ScreenDestination.Home.route) {
                DisneyListScreen(disneyCharactersViewModel) { id ->
                    navController.navigate(ScreenDestination.Details.createRoute(id))
                }
            }
            composable(route = ScreenDestination.Details.route) {
                val id = it.arguments?.getString("id")
                DisneyDetailScreen(id,disneyCharactersViewModel)
            }
        }
    }
}

