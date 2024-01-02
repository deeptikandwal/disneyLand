package com.disneyLand.ui.view.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.disneyLand.model.Actor
import com.disneyLand.ui.components.CustomImage
import com.disneyLand.ui.components.CustomText
import com.disneyLand.ui.components.PlaceHolderText
import com.disneyLand.ui.components.UnderLinedText
import com.disneyLand.ui.components.loadProgressBar
import com.disneyLand.ui.theme.Dimen.UI_SIZE_10_DP
import com.disneyLand.ui.theme.Dimen.UI_SIZE_20_DP
import com.disneyLand.ui.theme.Dimen.UI_SIZE_20_SP
import com.disneyLand.ui.theme.Dimen.UI_SIZE_300_DP
import com.disneyland.R

@Composable
fun DisneyDetailScreen(
    id: String,
    navigateUp: () -> Unit,
) {
    val disneyDetailScreenViewModel = hiltViewModel<DisneyDetailScreenViewModel>()
    with(disneyDetailScreenViewModel) {
        LaunchedEffect(id) {
            sendIntent(
                DisneyDetailMviContract.DisneyDetailScreenIntent.FetchCharacterById(
                    id
                )
            )
        }
        handleViewState(this)
        handleSideEffect(this) {
            navigateUp()
        }
        BackHandler {
            sendIntent(DisneyDetailMviContract.DisneyDetailScreenIntent.NavigateUp)
        }
    }
}

@Composable
private fun handleSideEffect(
    disneyDetailScreenViewModel: DisneyDetailScreenViewModel,
    navigateUp: () -> Unit,
) {
    val sideEffectDetails by disneyDetailScreenViewModel.sideEffect.collectAsState(0)
    when (sideEffectDetails) {
        is DisneyDetailMviContract.DisneyDetailScreenSideEffect.NavigateUp -> {
            navigateUp()
        }
    }
}

@Composable
private fun handleViewState(disneyDetailScreenViewModel: DisneyDetailScreenViewModel) {
    val viewState by disneyDetailScreenViewModel.viewState.collectAsState()
    var isDetailScreenLoading by remember { mutableStateOf(false) }
    var errorFound by remember { mutableStateOf(false) }
    when (viewState) {
        is DisneyDetailMviContract.DisneyDetailScreenViewState.Loading -> {
            isDetailScreenLoading = true
        }

        is DisneyDetailMviContract.DisneyDetailScreenViewState.Success -> {
            errorFound = false
            isDetailScreenLoading = false
            setDetailsScreen(
                (viewState as DisneyDetailMviContract.DisneyDetailScreenViewState.Success).data,
                disneyDetailScreenViewModel
            )
        }

        is DisneyDetailMviContract.DisneyDetailScreenViewState.Error -> {
            errorFound = false
            isDetailScreenLoading = false
        }
    }

    loadProgressBar(isDetailScreenLoading)
    if (errorFound) {
        PlaceHolderText()
    }
}

@Composable
private fun setDetailsScreen(
    actor: Actor,
    disneyDetailScreenViewModel: DisneyDetailScreenViewModel,
) {
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        Box(Modifier.fillMaxWidth().size(UI_SIZE_300_DP)) {
            CustomImage(actor.image)
        }

        Column(modifier = Modifier.padding(top = UI_SIZE_10_DP)) {
            setActorName(actor)
            setActorDescription(disneyDetailScreenViewModel, actor)
        }
    }

}

@Composable
private fun setActorDescription(
    disneyDetailScreenViewModel: DisneyDetailScreenViewModel,
    actor: Actor,
) {
    val list = disneyDetailScreenViewModel.getActorCharacteristicsList(actor.description)
    UnderLinedText(list.first())
    CustomText(list.last())

    if (disneyDetailScreenViewModel.setVisibility(actor.allies)) {
        UnderLinedText(stringResource(R.string.allies))
        CustomText(actor.allies)
    }
    if (disneyDetailScreenViewModel.setVisibility(actor.enemies)) {
        UnderLinedText(stringResource(R.string.enemies))
        CustomText(actor.enemies)
    }
    if (disneyDetailScreenViewModel.setVisibility(actor.majorAttraction)) {
        UnderLinedText(stringResource(R.string.attractions))
        CustomText(actor.majorAttraction)
    }
}

@Composable
private fun setActorName(actor: Actor) {
    Text(
        actor.name,
        textAlign = TextAlign.Center,
        fontSize = UI_SIZE_20_SP,
        modifier = Modifier.fillMaxWidth().padding(top = UI_SIZE_20_DP),
        color = MaterialTheme.colorScheme.onPrimary
    )
}
