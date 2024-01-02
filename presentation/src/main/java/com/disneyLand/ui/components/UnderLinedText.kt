package com.disneyLand.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.disneyLand.ui.theme.Dimen.UI_SIZE_12_DP
import com.disneyLand.ui.theme.Dimen.UI_SIZE_18_SP

@Composable
fun UnderLinedText(text: String) {
    Text(
        text = text,
        fontSize = UI_SIZE_18_SP,
        textDecoration = TextDecoration.Underline,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(UI_SIZE_12_DP)
    )
}
