package com.disneyland.domain.usecase

import androidx.paging.PagingData
import com.disneyland.domain.model.DisneyListCharacter
import kotlinx.coroutines.flow.Flow

interface DisneyCharactersListUsecase {
    operator fun invoke(): Flow<PagingData<DisneyListCharacter>>
}