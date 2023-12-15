package com.disneyLand.usecase

import com.disneyLand.Outcome
import com.disneyLand.model.DisneyActor
import com.disneyLand.repository.DisneyCharactersRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test

class DisneyActorUsecaseImplTest {

    private lateinit var disneyActorUsecaseImpl: DisneyActorUsecaseImpl

    @MockK
    private lateinit var disneyCharactersRepository: DisneyCharactersRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        disneyActorUsecaseImpl = DisneyActorUsecaseImpl(disneyCharactersRepository)
    }

    @Test
    fun `fetch disney character by Id successfully`() {
        coEvery { disneyCharactersRepository.fetchDisneyCharacterById(ID) } answers {
            flow {
                emit(Outcome.Success(getDisneyActor()))
            }
        }
        disneyActorUsecaseImpl(ID)
        coVerify {
            disneyCharactersRepository.fetchDisneyCharacterById(ID)
        }
    }

    private fun getDisneyActor(): DisneyActor = DisneyActor(
        "Alladin",
        "Aladdin(film), The Return of Jafar",
        "World of color, Golden FairyTale Fanfare",
        "",
        "",
        ""
    )

    companion object {
        private const val ID = "209"
    }
}
