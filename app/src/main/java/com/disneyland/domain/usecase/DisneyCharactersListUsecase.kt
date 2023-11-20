package com.disneyland.domain.usecase

import androidx.paging.PagingData
import com.disneyland.domain.entity.DisneyCharacter
import kotlinx.coroutines.flow.Flow

interface DisneyCharactersListUsecase {
    operator fun invoke(): Flow<PagingData<DisneyCharacter>>
}