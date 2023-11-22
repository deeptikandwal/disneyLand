package com.disneyland.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.disneyland.presentation.ui.view.DisneyCharactersViewModel

@Composable
fun DisneyDetailScreen( id: String?, disneyCharactersViewModel: DisneyCharactersViewModel) {
    LaunchedEffect(Unit){
        disneyCharactersViewModel.fetchDisneyCharactersById(id!!)
    }
}