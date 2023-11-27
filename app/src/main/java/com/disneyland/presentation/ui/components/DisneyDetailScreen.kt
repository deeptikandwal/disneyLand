package com.disneyland.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.disneyland.R
import com.disneyland.presentation.model.Actor
import com.disneyland.presentation.ui.base.CustomImage
import com.disneyland.presentation.ui.base.CustomText
import com.disneyland.presentation.ui.base.ProgressBar
import com.disneyland.presentation.ui.base.UnderLinedText

private lateinit var actor: Actor

@Composable
fun DisneyDetailScreen(state: DisneyDetailScreenViewState) {
    when (state) {
        is DisneyDetailScreenViewState.LOADING -> {
            ProgressBar()
        }

        is DisneyDetailScreenViewState.SUCCESS -> {
            actor = state.data
        }

        else -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.data_not_found))
            }
        }
    }
    setDetailScreen()
}

@Composable
fun setDetailScreen() {
    val scrollState = rememberScrollState()

    if (::actor.isInitialized) {
        Column(Modifier.verticalScroll(scrollState)) {
            Box(Modifier.fillMaxWidth().size(300.dp)) {
                CustomImage(actor.image)
            }

            Column(modifier = Modifier.padding(10.dp)) {
                if (actor.description.isNotEmpty()) {
                    val desc = actor.description.split(":")
                    UnderLinedText(desc[0])
                    CustomText(desc[1])
                }
                if (actor.allies.isNotEmpty()) {
                    UnderLinedText("Allies")
                    CustomText(actor.allies)

                }
            }
            if (actor.enemies.isNotEmpty()) {
                UnderLinedText("Enemies")
                CustomText(actor.enemies)

            }

            if (actor.majorAttraction.isNotEmpty()) {
                UnderLinedText("Attractions")
                CustomText(actor.majorAttraction)
            }

        }
    }

}
