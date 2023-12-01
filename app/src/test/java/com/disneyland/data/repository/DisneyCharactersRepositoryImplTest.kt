package com.disneyland.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.disneyland.Outcome
import com.disneyland.data.di.IODispatcher
import com.disneyland.data.source.ActorMapper
import com.disneyland.data.source.DisneyApiService
import com.disneyland.data.source.DisneyMapper
import com.disneyland.data.source.FakeDisneyActor
import com.disneyland.domain.model.DisneyActor
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineDispatcher
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
                Assert.assertEquals(Outcome.Success(actor), awaitItem())
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

