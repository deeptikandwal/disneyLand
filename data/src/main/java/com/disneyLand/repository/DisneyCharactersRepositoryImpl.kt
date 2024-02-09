package com.disneyLand.repository

import com.disneyLand.Result
import com.disneyLand.di.IODispatcher
import com.disneyLand.model.DisneyActor
import com.disneyLand.model.DisneyListCharacter
import com.disneyLand.source.ActorMapper
import com.disneyLand.source.DisneyApiService
import com.disneyLand.source.DisneyMapper
import com.disneyLand.source.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DisneyCharactersRepositoryImpl @Inject constructor(
    private val disneyApiService: DisneyApiService,
    private val disneyMapper: DisneyMapper,
    private val actorMapper: ActorMapper,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : DisneyCharactersRepository {
    override suspend fun fetchDisneyCharacters(): Result<List<DisneyListCharacter>> {
        return safeApiCall(
            ioDispatcher,
            {
                disneyApiService.fetchDisneyCharacters(SIZE, MAX_PAGE_SIZE)
            },
            { dto ->
                disneyMapper.mapToDisneyCharacter(dto.data)
            }
        )
    }

    override suspend fun fetchDisneyCharacterById(id: String): Result<DisneyActor> {
        return safeApiCall(
            ioDispatcher,
            {
                disneyApiService.fetchDisneyCharacterById(id)
            },
            { dto ->
                actorMapper.mapToDisneyActor(dto)
            }
        )
    }

    private companion object {
        const val MAX_PAGE_SIZE = 40
        const val SIZE = 1
    }
}
