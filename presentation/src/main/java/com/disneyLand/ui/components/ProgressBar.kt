package com.disneyLand.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.disneyLand.ui.theme.Dimen.UI_SIZE_100_DP
import com.disneyLand.ui.theme.Dimen.UI_SIZE_64_DP

@Composable
fun loadProgressBar(isLoading: Boolean) {
    if (isLoading) {
        ProgressBar()
    }
}
@Composable
fun ProgressBar() {
    Box(Modifier.fillMaxSize().size(UI_SIZE_100_DP), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier.width(UI_SIZE_64_DP),
            color = MaterialTheme.colorScheme.primary
        )
    }
}
