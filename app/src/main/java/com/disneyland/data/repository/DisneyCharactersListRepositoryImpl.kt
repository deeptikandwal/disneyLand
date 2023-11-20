package com.disneyland.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.disneyland.AppConstants
import com.disneyland.data.source.DisneyApiService
import com.disneyland.data.source.DisneyMapper
import com.disneyland.data.source.DisneyPagingSource
import com.disneyland.domain.entity.DisneyCharacter
import com.disneyland.domain.repository.DisneyCharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DisneyCharactersListRepositoryImpl @Inject constructor(
    private val disneyApiService: DisneyApiService,
    private val mapper: DisneyMapper
) : DisneyCharactersRepository {
    override  fun fetchDisneyCharacters(): Flow<PagingData<DisneyCharacter>> {
        return Pager(
            config = PagingConfig(pageSize = AppConstants.MAX_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                DisneyPagingSource(disneyApiService,mapper)
            }
        ).flow
    }

}