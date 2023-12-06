package com.disneyLand.ui.view

sealed class ScreenDestination(val route: String) {
    object Home : ScreenDestination(HOME)
    object Details : ScreenDestination(DETAILS) {
        fun createRoute(id: Int) = "$id/$DETAILS"
    }

    companion object {
        private const val HOME = "Home"
        private const val DETAILS = "Details"
    }
}
