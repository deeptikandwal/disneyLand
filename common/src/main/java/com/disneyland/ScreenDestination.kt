package com.disneyland
sealed class ScreenDestination(val route: String) {
    object Home: ScreenDestination(AppConstants.HOME)
    object Details: ScreenDestination("{id}/${AppConstants.DETAILS}") {
        fun createRoute(id: Int) = "$id/${AppConstants.DETAILS}"
    }
}