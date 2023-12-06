package com.disneyLand.usecase

import androidx.paging.PagingData
import com.disneyLand.model.DisneyListCharacter
import com.disneyLand.repository.DisneyCharactersRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test
class DisneyCharactersListUsecaseImplTest {

    private lateinit var disneyCharactersListUsecaseImpl: DisneyCharactersListUsecaseImpl

    @MockK
    private lateinit var pagingData: PagingData<DisneyListCharacter>

    @MockK
    private lateinit var disneyCharactersRepository: DisneyCharactersRepository
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        disneyCharactersListUsecaseImpl =
            DisneyCharactersListUsecaseImpl(disneyCharactersRepository)
    }
    @Test
    fun `fetch disney characters list successfully`() {
        coEvery { disneyCharactersRepository.fetchDisneyCharacters() } answers {
            flow {
                emit(pagingData)
            }
        }
        disneyCharactersListUsecaseImpl()
        coVerify {
            disneyCharactersRepository.fetchDisneyCharacters()
        }
    }
    companion object {
        private const val ID = "209"
    }
}
