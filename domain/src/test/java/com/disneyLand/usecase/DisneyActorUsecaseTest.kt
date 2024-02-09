package com.disneyLand.usecase

import com.disneyLand.Result
import com.disneyLand.model.DisneyActor
import com.disneyLand.repository.DisneyCharactersRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DisneyActorUsecaseTest {

    private lateinit var disneyActorUsecase: DisneyActorUsecase

    private val disneyCharactersRepository = mockk<DisneyCharactersRepository>()

    @Before
    fun setUp() {
        disneyActorUsecase = DisneyActorUsecase(disneyCharactersRepository)
    }

    @Test
    fun `fetch disney character by Id successfully`() = runTest {
        coEvery { disneyCharactersRepository.fetchDisneyCharacterById(ID) } answers {
            Result.Success(disneyActor)
        }
        disneyActorUsecase(ID)
        coVerify {
            disneyCharactersRepository.fetchDisneyCharacterById(ID)
        }
    }

    private companion object {
        private const val ID = "209"
        val disneyActor = DisneyActor(
            "Alladin",
            "Aladdin(film), The Return of Jafar",
            "World of color, Golden FairyTale Fanfare",
            "",
            "",
            ""
        )
    }
}
