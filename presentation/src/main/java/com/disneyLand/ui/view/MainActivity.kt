package com.disneyLand.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.disneyLand.ui.components.AppScreens
import com.disneyLand.ui.components.CreateTopAppBar
import com.disneyLand.ui.theme.DisneyLandTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setComposeUi()
    }

    private fun setComposeUi() {
        setContent {
            DisneyLandTheme {
                navController = rememberNavController()
                Scaffold(
                    topBar = { CreateTopAppBar(navController, this) }
                ) { paddingValues ->
                    AppScreens(navController, paddingValues) {
                        finish()
                    }
                }
            }
        }
    }
}
