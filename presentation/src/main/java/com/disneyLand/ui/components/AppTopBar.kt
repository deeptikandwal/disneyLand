package com.disneyLand.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.disneyland.R

private const val TEST_TAG = "ic_back"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(navigateBack: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            Text(
                text = stringResource(R.string.title_home_screen),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = TEST_TAG,
                modifier = Modifier
                    .padding(5.dp)
                    .semantics { testTag = TEST_TAG }
                    .clickable {
                        // back press
                        navigateBack()
                    }
            )
        }
    )
}
