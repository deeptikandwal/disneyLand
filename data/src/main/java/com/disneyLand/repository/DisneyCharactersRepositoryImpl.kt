package com.disneyLand.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.disneyLand.di.IODispatcher
import com.disneyLand.model.DisneyActor
import com.disneyLand.model.DisneyListCharacter
import com.disneyLand.source.ActorMapper
import com.disneyLand.source.DisneyApiService
import com.disneyLand.source.DisneyMapper
import com.disneyLand.source.DisneyPagingSource
import com.disneyLand.Outcome
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DisneyCharactersRepositoryImpl @Inject constructor(
    private val disneyApiService: DisneyApiService,
    private val mapper: DisneyMapper,
    private val actorMapper: ActorMapper,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : DisneyCharactersRepository {
    override fun fetchDisneyCharacters(): Flow<PagingData<DisneyListCharacter>> {
        return Pager(
            config = PagingConfig(pageSize = MAX_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                DisneyPagingSource(disneyApiService, mapper)
            }
        ).flow
            .flowOn(ioDispatcher)
    }

    override fun fetchDisneyCharacterById(id: String): Flow<Outcome<DisneyActor>> {
        return flow {
            try {
                val response = disneyApiService.fetchDisneyCharacterById(id)
                emit(Outcome.Success(actorMapper.mapToDisneyActor(response)!!))
            } catch (e: Exception) {
                emit(Outcome.Failure(e))
            }
        }.flowOn(ioDispatcher)
    }

    companion object{
        private const val MAX_PAGE_SIZE = 372
    }
}