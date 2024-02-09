package com.disneyLand.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.disneyLand.model.Character
import com.disneyLand.ui.theme.Dimen
import com.disneyLand.ui.theme.Dimen.UI_SIZE_15_SP
import com.disneyland.R

private const val fixedGridCount = 2

@Composable
fun ListScreen(
    listState: LazyGridState,
    characters: List<Character>,
    goToDetailsScreen: (Int) -> Unit
) {
    Column {
        HeaderText(
            text = stringResource(R.string.intro_text),
            textAlign = TextAlign.Start,
            fontSize = UI_SIZE_15_SP,
            modifier = Modifier.fillMaxWidth().padding(Dimen.UI_SIZE_10_DP),
            fontWeight = FontWeight.Medium,
            textDecoration = TextDecoration.None,
            color = MaterialTheme.colorScheme.onPrimary
        )
        LazyVerticalGrid(GridCells.Fixed(fixedGridCount), state = listState) {
            items(characters.size) { index ->
                gridItem(characters, index, goToDetailsScreen)
            }
        }
    }
}

@Composable
fun gridItem(
    characters: List<Character>,
    index: Int,
    goToDetailsScreen: (Int) -> Unit
) {
    ListItemCard({
        Box(Modifier.height(Dimen.UI_SIZE_200_DP).fillMaxWidth()) {
            ImageView(characters[index].image)
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                HeaderText(
                    characters[index].name,
                    fontSize = UI_SIZE_15_SP,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.onSurface),
                    fontWeight = FontWeight.Medium,
                    textDecoration = TextDecoration.None,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }, {
        goToDetailsScreen(characters[index].id)
    })
}
