package com.disneyLand.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.disneyLand.ui.theme.Dimen
import com.disneyland.R
@Composable
fun IntroText(
    text: String = stringResource(R.string.intro_text),
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = Dimen.UI_SIZE_15_SP,
    color: Color = MaterialTheme.colorScheme.onPrimary
) {
    Text(
        text = text,
        fontSize = fontSize,
        textAlign = textAlign,
        modifier = Modifier.fillMaxWidth().padding(Dimen.UI_SIZE_10_DP),
        color = color
    )
}
