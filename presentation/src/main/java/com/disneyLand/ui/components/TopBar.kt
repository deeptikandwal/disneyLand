package com.disneyLand.ui.components

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavHostController
import com.disneyLand.ui.theme.Dimen
import com.disneyLand.ui.theme.Dimen.UI_SIZE_20_SP
import com.disneyLand.ui.view.ScreenDestination
import com.disneyland.R

private const val TEST_TAG = "ic_back"
private const val ID_REDUX = "/{id}"

@Composable
fun CreateTopAppBar(
    navController: NavHostController,
    activity: ComponentActivity
) {
    TopBar {
        when (navController.currentDestination?.route) {
            ScreenDestination.Home.route -> activity.finish()
            ScreenDestination.Details.route + ID_REDUX -> navController.navigate(ScreenDestination.Home.route)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navigateBack: () -> Unit) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            HeaderText(
                text = stringResource(R.string.title_home_screen),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textDecoration = TextDecoration.None,
                fontSize = UI_SIZE_20_SP
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = TEST_TAG,
                modifier = Modifier
                    .padding(Dimen.UI_SIZE_5_DP)
                    .semantics { testTag = TEST_TAG }
                    .clickable {
                        navigateBack()
                    }
            )
        }
    )
}
