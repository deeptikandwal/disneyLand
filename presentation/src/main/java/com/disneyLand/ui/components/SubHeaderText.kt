package com.disneyLand.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.disneyLand.ui.theme.Dimen.UI_SIZE_12_DP
import com.disneyLand.ui.theme.Dimen.UI_SIZE_18_SP

@Composable
fun SubHeaderText(
    text: String,
    fontWeight: FontWeight = FontWeight.Medium,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    fontSize: TextUnit = UI_SIZE_18_SP
) {
    Text(
        text = text,
        modifier = modifier.padding(UI_SIZE_12_DP).wrapContentSize(),
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color
    )
}
