package com.disneyLand.usecase

import androidx.paging.PagingData
import com.disneyLand.model.DisneyListCharacter
import kotlinx.coroutines.flow.Flow

interface DisneyCharactersListUsecase {
    operator fun invoke(): Flow<PagingData<DisneyListCharacter>>
}