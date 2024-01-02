package com.disneyLand.repository

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.map
import app.cash.turbine.test
import com.disneyLand.BaseTest
import com.disneyLand.Outcome
import com.disneyLand.model.DisneyActor
import com.disneyLand.source.ActorMapper
import com.disneyLand.source.DisneyApiService
import com.disneyLand.source.DisneyMapper
import com.disneyLand.source.FakeDisneyActor
import com.disneyLand.source.FakeDisneyListCharacters
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class DisneyCharactersRepositoryImplTest :BaseTest(){
    private lateinit var disneyCharactersRepositoryImpl: DisneyCharactersRepositoryImpl

    @MockK
    private lateinit var actorMapper: ActorMapper

    @MockK
    private lateinit var disneyMapper: DisneyMapper

    @MockK
    private lateinit var disneyApiService: DisneyApiService

    @Before
   override fun setUp() {
        super.setUp()
        disneyCharactersRepositoryImpl = DisneyCharactersRepositoryImpl(
            disneyApiService,
            disneyMapper,
            actorMapper,
            testDispatcher
        )
    }

    @Test
    fun `fetch disney characters list `() = runTest {
        coEvery { disneyApiService.fetchDisneyCharacters(any(), any()) } answers {
            FakeDisneyListCharacters.disneyListCharacters
        }
        coEvery { disneyMapper.mapToDisneyCharacter(any()) } answers {
            FakeDisneyListCharacters.lisOfDisneyListCharacters
        }

        disneyCharactersRepositoryImpl.fetchDisneyCharacters().test {
            awaitItem().map {
                Assert.assertEquals("", it.name)
            }

            awaitComplete()
        }
    }

    @Test
    fun `fetch disney character GIVEN id WHEN fetch Disney Character api call is made`() =
        runTest {
            val response = FakeDisneyActor.successfulDisneyActor
            val actor = FakeDisneyActor.disneyActor

            coEvery { disneyApiService.fetchDisneyCharacterById(ID) } returns response
            every { actorMapper.mapToDisneyActor(any()) } answers { FakeDisneyActor.disneyActor }

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
                "Address no found".toResponseBody("plain/text".toMediaTypeOrNull())
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

    companion object {
        private const val ID = "268"
    }
}
