package com.disneyLand.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.disneyLand.ui.theme.Dimen
import com.disneyland.R
@Composable
fun ErrorScreen(error: Boolean) {
    if (error) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeaderText(
                text = stringResource(R.string.data_not_found),
                fontSize = Dimen.UI_SIZE_15_SP,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(Dimen.UI_SIZE_10_DP),
                textDecoration = TextDecoration.None,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
