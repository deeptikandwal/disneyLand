package com.disneyLand.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import com.disneyLand.ui.theme.Dimen.UI_SIZE_12_DP
import com.disneyLand.ui.theme.Dimen.UI_SIZE_18_SP

@Composable
fun HeaderText(
    text: String,
    fontSize: TextUnit = UI_SIZE_18_SP,
    textDecoration: TextDecoration = TextDecoration.Underline,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Bold,
    color: Color = MaterialTheme.colorScheme.primary,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        modifier = modifier.padding(UI_SIZE_12_DP),
        fontSize = fontSize,
        textDecoration = textDecoration,
        fontWeight = fontWeight,
        color = color,
        textAlign = textAlign
    )
}
