package com.disneyLand.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.disneyLand.ui.theme.Dimen.UI_SIZE_10_DP

@Composable
fun CustomCard(content: @Composable () -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(UI_SIZE_10_DP).clickable { onClick() },
        elevation = CardDefaults.cardElevation(UI_SIZE_10_DP)
    ) {
        content()
    }
}
