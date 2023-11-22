package com.disneyland.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.disneyland.AppConstants
import com.disneyland.Outcome
import com.disneyland.data.source.ActorMapper
import com.disneyland.data.source.DisneyApiService
import com.disneyland.data.source.DisneyMapper
import com.disneyland.data.source.DisneyPagingSource
import com.disneyland.domain.entity.DisneyActor
import com.disneyland.domain.entity.DisneyListCharacter
import com.disneyland.domain.repository.DisneyCharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DisneyCharactersRepositoryImpl @Inject constructor(
    private val disneyApiService: DisneyApiService,
    private val mapper: DisneyMapper,
    private val actorMapper: ActorMapper,
) : DisneyCharactersRepository {
    override fun fetchDisneyCharacters(): Flow<PagingData<DisneyListCharacter>> {
        return Pager(
            config = PagingConfig(pageSize = AppConstants.MAX_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                DisneyPagingSource(disneyApiService, mapper)
            }
        ).flow
    }

    override fun fetchDisneyCharacterById(id: String): Flow<Outcome<DisneyActor>> {
        return flow {
            try {
                val response = disneyApiService.fetchDisneyCharacterById(id)
                emit(Outcome.Success(actorMapper.mapToDisneyActor(response)!!))
            } catch (e: Exception) {
                emit(Outcome.Failure(e))
            }
        }
    }
}