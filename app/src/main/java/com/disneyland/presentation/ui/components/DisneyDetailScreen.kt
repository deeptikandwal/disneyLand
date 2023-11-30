package com.disneyland.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.disneyland.R
import com.disneyland.presentation.model.Actor
import com.disneyland.presentation.ui.base.CustomImage
import com.disneyland.presentation.ui.base.CustomText
import com.disneyland.presentation.ui.base.ProgressBar
import com.disneyland.presentation.ui.base.UnderLinedText


@Composable
fun DisneyDetailScreen(actor: Actor?) {
    if (actor != null) {
        setDetailScreen(actor)
    }
}

@Composable
fun setDetailScreen(actor: Actor) {
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        Box(Modifier.fillMaxWidth().size(300.dp)) {
            CustomImage(actor.image)
        }

        Column(modifier = Modifier.padding(10.dp)) {

            Text(
                actor.name,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
            if (actor.description.isNotEmpty()) {
                val desc = actor.description.split(":")
                UnderLinedText(desc[0])
                CustomText(desc[1])
            }
            if (actor.allies.isNotEmpty()) {
                UnderLinedText(stringResource(R.string.allies))
                CustomText(actor.allies)

            }
        }
        if (actor.enemies.isNotEmpty()) {
            UnderLinedText(stringResource(R.string.enemies))
            CustomText(actor.enemies)

        }

        if (actor.majorAttraction.isNotEmpty()) {
            UnderLinedText(stringResource(R.string.attractions))
            CustomText(actor.majorAttraction)
        }

    }
}
