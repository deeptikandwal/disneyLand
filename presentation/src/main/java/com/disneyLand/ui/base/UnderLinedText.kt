package com.disneyLand.ui.base

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UnderLinedText(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
        textDecoration = TextDecoration.Underline,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(10.dp)
    )
}