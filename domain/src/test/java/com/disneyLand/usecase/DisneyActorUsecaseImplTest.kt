package com.disneyLand.usecase

import com.disneyLand.BaseTest
import com.disneyLand.Outcome
import com.disneyLand.model.DisneyActor
import com.disneyLand.repository.DisneyCharactersRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test

class DisneyActorUsecaseImplTest : BaseTest() {

    private lateinit var disneyActorUsecaseImpl: DisneyActorUsecaseImpl

    @MockK
    private lateinit var disneyCharactersRepository: DisneyCharactersRepository

    @Before
    override fun setUp() {
        super.setUp()
        disneyActorUsecaseImpl = DisneyActorUsecaseImpl(disneyCharactersRepository)
    }

    @Test
    fun `fetch disney character by Id successfully`() {
        coEvery { disneyCharactersRepository.fetchDisneyCharacterById(ID) } answers {
            flow {
                emit(Outcome.Success(disneyActor))
            }
        }
        disneyActorUsecaseImpl(ID)
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
