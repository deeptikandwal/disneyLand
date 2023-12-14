package com.disneyLand.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomCard(content: @Composable () -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(10.dp).clickable { onClick() },
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        content()
    }
}
