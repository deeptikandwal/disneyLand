package com.disneyLand.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.disneyLand.Outcome
import com.disneyLand.model.DisneyActor
import com.disneyLand.source.ActorMapper
import com.disneyLand.source.DisneyApiService
import com.disneyLand.source.DisneyMapper
import com.disneyLand.source.FakeDisneyActor
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class DisneyCharactersRepositoryImplTest {
    private lateinit var disneyCharactersRepositoryImpl: DisneyCharactersRepositoryImpl

    @get:Rule
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var disneyApiService: DisneyApiService

    @MockK
    private lateinit var disneyMapper: DisneyMapper

    @MockK
    private lateinit var actorMapper: ActorMapper

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        disneyCharactersRepositoryImpl = DisneyCharactersRepositoryImpl(
            disneyApiService,
            disneyMapper,
            actorMapper,
            testDispatcher
        )
    }

    @Test
    fun ` fetch disney character GIVEN id WHEN fetch Disney Character api call is made`() =
        runTest {
            val response = FakeDisneyActor.getSuccessfulDisneyActorDTO()
            val actor = FakeDisneyActor.getSuccessfulDisneyActor()

            coEvery { disneyApiService.fetchDisneyCharacterById(ID) } returns response
            every { actorMapper.mapToDisneyActor(response) } returns actor

            disneyCharactersRepositoryImpl.fetchDisneyCharacterById(ID).test {
                Assert.assertEquals(Outcome.Success<DisneyActor>(actor), awaitItem())
                awaitComplete()
            }
        }

    @Test
    fun `exception thrown GIVEN id WHEN fetch Disney Character api call is made`() = runTest {
        val exception = retrofit2.HttpException(
            Response.error<ResponseBody>(
                503,
                ResponseBody.create("plain/text".toMediaTypeOrNull(), "Address no found")
            )
        )

        coEvery { disneyApiService.fetchDisneyCharacterById(ID) } coAnswers {
            throw exception
        }
        disneyCharactersRepositoryImpl.fetchDisneyCharacterById(ID).test {
            Assert.assertEquals(Outcome.Failure<DisneyActor>(exception), awaitItem())
            awaitComplete()
        }
    }

    @After
    @OptIn(ExperimentalCoroutinesApi::class)
    fun tearDown() {
        Dispatchers.resetMain()
    }

    companion object {
        private const val ID = "268"
    }
}
