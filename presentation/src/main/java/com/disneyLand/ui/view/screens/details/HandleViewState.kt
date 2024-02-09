package com.disneyLand.ui.view.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.disneyLand.model.Actor
import com.disneyLand.ui.components.ErrorScreen
import com.disneyLand.ui.components.HeaderText
import com.disneyLand.ui.components.ImageView
import com.disneyLand.ui.components.SubHeaderText
import com.disneyLand.ui.components.loadProgressBar
import com.disneyLand.ui.theme.Dimen

@Composable
fun handleViewState(disneyDetailScreenViewModel: DisneyDetailScreenViewModel) {
    val viewState by disneyDetailScreenViewModel.viewState.collectAsState()
    when (viewState) {
        is DisneyDetailMviContract.DisneyDetailScreenViewState.Loading -> {
            loadProgressBar(true)
        }

        is DisneyDetailMviContract.DisneyDetailScreenViewState.Success -> {
            loadProgressBar(false)
            setDetailsScreen(
                (viewState as DisneyDetailMviContract.DisneyDetailScreenViewState.Success).data,
                disneyDetailScreenViewModel
            )
        }

        is DisneyDetailMviContract.DisneyDetailScreenViewState.Error -> {
            ErrorScreen(true)
            loadProgressBar(false)
        }
    }
}

@Composable
private fun setDetailsScreen(
    actor: Actor,
    disneyDetailScreenViewModel: DisneyDetailScreenViewModel
) {
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        Box(Modifier.fillMaxWidth().size(Dimen.UI_SIZE_300_DP)) {
            ImageView(actor.image)
        }

        Column(modifier = Modifier.padding(top = Dimen.UI_SIZE_10_DP)) {
            setActorName(actor)
            setActorDescription(disneyDetailScreenViewModel, actor)
        }
    }
}

@Composable
private fun setActorDescription(
    disneyDetailScreenViewModel: DisneyDetailScreenViewModel,
    actor: Actor
) {
    val list = disneyDetailScreenViewModel.getActorCharacteristicsList(actor.description)
    HeaderText(
        list.first(),
        fontSize = Dimen.UI_SIZE_20_SP
    )
    SubHeaderText(list.last())
}

@Composable
fun setActorName(actor: Actor) {
    HeaderText(
        text = actor.name,
        fontSize = Dimen.UI_SIZE_20_SP,
        textDecoration = TextDecoration.None,
        modifier = Modifier.fillMaxWidth().padding(top = Dimen.UI_SIZE_20_DP),
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colorScheme.tertiary,
        textAlign = TextAlign.Center
    )
}
