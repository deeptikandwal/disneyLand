package com.disneyLand.ui.view

sealed class ScreenDestination(val route: String) {
    object Home : ScreenDestination(HOME)
    object Details : ScreenDestination(DETAILS)

    private companion object {
        const val HOME = "Home"
        const val DETAILS = "Details"
    }
}
