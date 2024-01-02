package com.disneyLand.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.disneyLand.ui.theme.Dimen.UI_SIZE_12_DP
import com.disneyLand.ui.theme.Dimen.UI_SIZE_18_SP

@Composable
fun CustomText(text: String) {
    Text(
        text = text,
        fontSize = UI_SIZE_18_SP,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(UI_SIZE_12_DP).wrapContentSize(),
        color = MaterialTheme.colorScheme.onPrimary
    )
}
