package com.disneyLand.usecase

import com.disneyLand.Result
import com.disneyLand.model.DisneyListCharacter
import com.disneyLand.repository.DisneyCharactersRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DisneyCharactersListUsecaseTest {

    private lateinit var disneyCharactersListUsecase: DisneyCharactersListUsecase

    private val disneyCharactersRepository = mockk<DisneyCharactersRepository>()

    @Before
    fun setUp() {
        disneyCharactersListUsecase =
            DisneyCharactersListUsecase(disneyCharactersRepository)
    }

    @Test
    fun `fetch disney characters list successfully`() = runTest {
        coEvery { disneyCharactersRepository.fetchDisneyCharacters() } answers {
            Result.Success(listOf(disneyListCharacter))
        }
        disneyCharactersListUsecase()
        coVerify {
            disneyCharactersRepository.fetchDisneyCharacters()
        }
    }

    companion object {
        val disneyListCharacter = DisneyListCharacter(209, "Anthony Biddle", "")
    }
}
