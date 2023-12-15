package com.disneyLand.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.disneyLand.Outcome
import com.disneyLand.di.IODispatcher
import com.disneyLand.model.DisneyActor
import com.disneyLand.model.DisneyListCharacter
import com.disneyLand.source.DisneyApiService
import com.disneyLand.source.DisneyPagingSource
import com.disneyLand.source.mapToDisneyActor
import com.disneyLand.source.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DisneyCharactersRepositoryImpl @Inject constructor(
    private val disneyApiService: DisneyApiService,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : DisneyCharactersRepository {
    override fun fetchDisneyCharacters(): Flow<PagingData<DisneyListCharacter>> {
        return Pager(
            config = PagingConfig(pageSize = MAX_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                DisneyPagingSource(disneyApiService)
            }
        ).flow
            .flowOn(ioDispatcher)
    }

    override fun fetchDisneyCharacterById(id: String): Flow<Outcome<DisneyActor>> {
        return flow {
            val response = safeApiCall(
                ioDispatcher,
                {
                    disneyApiService.fetchDisneyCharacterById(id)
                },
                { dto ->
                    dto.mapToDisneyActor()!!
                }
            )
            emit(response)
        }.flowOn(ioDispatcher)
    }

    companion object {
        private const val MAX_PAGE_SIZE = 372
    }
}
