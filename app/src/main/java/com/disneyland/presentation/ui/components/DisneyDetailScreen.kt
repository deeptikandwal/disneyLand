package com.disneyland.presentation.ui.components

import androidx.compose.runtime.Composable
import com.disneyland.presentation.ui.view.DisneyCharactersViewModel

@Composable
fun DisneyDetailScreen( id: String?, disneyCharactersViewModel: DisneyCharactersViewModel) {
    disneyCharactersViewModel.fetchDisneyCharactersById(id!!)
}