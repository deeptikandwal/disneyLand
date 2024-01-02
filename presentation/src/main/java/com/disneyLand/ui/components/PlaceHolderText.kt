package com.disneyLand.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.disneyLand.ui.theme.Dimen
import com.disneyland.R

@Composable
fun PlaceHolderText(
    text: String = stringResource(R.string.data_not_found),
    fontSize: TextUnit = Dimen.UI_SIZE_15_SP,
    textAlign: TextAlign = TextAlign.Center
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            textAlign = textAlign,
            modifier = Modifier.fillMaxWidth().padding(Dimen.UI_SIZE_10_DP)
        )
    }
}
