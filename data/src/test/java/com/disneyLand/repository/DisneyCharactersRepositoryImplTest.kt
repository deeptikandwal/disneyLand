package com.disneyLand.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.BaseHelper
import com.disneyLand.Result
import com.disneyLand.dto.Characters
import com.disneyLand.dto.DisneyCharactersList
import com.disneyLand.dto.DisneyOriginalActor
import com.disneyLand.model.DisneyActor
import com.disneyLand.model.DisneyListCharacter
import com.disneyLand.source.ActorMapper
import com.disneyLand.source.DisneyApiService
import com.disneyLand.source.DisneyMapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
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

class DisneyCharactersRepositoryImplTest {
    private lateinit var disneyCharactersRepositoryImpl: DisneyCharactersRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val disneyApiService = mockk<DisneyApiService>()
    private val disneyMapper = mockk<DisneyMapper>()
    private val actorMapper = mockk<ActorMapper>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        disneyCharactersRepositoryImpl = DisneyCharactersRepositoryImpl(
            disneyApiService,
            disneyMapper,
            actorMapper,
            testDispatcher
        )
    }

    @Test
    fun `fetch disney characters list successfully`() = runTest {
        val disneyCharactersListResponseModel = BaseHelper.convertJsonToModel(
            BaseHelper.getJsonFile("disney_actor_list.json"),
            DisneyCharactersList::class.java
        )
        val list = disneyCharactersListResponseModel.data
        val disneylist = arrayListOf<DisneyListCharacter>()
        list.map {
            disneylist.add(getDisneyListCharacter(it))
        }
        coEvery { disneyApiService.fetchDisneyCharacters(any(), any()) } answers {
            disneyCharactersListResponseModel
        }
        coEvery { disneyMapper.mapToDisneyCharacter(any()) } answers {
            disneylist
        }

        val result = disneyCharactersRepositoryImpl.fetchDisneyCharacters()
        Assert.assertTrue(result is Result.Success)
    }

    @Test
    fun `fetch disney characters list failed`() = runTest {
        coEvery { disneyApiService.fetchDisneyCharacters(any(), any()) } answers {
            throw Exception()
        }

        val result = disneyCharactersRepositoryImpl.fetchDisneyCharacters()
        Assert.assertTrue(result is Result.Error)
    }

    @Test
    fun `fetch disney character GIVEN id WHEN fetch Disney Character api call is made`() =
        runTest {
            val response = BaseHelper.convertJsonToModel(
                BaseHelper.getJsonFile("disney_actor.json"),
                DisneyOriginalActor::class.java
            )

            val actor = BaseHelper.convertJsonToModel(
                BaseHelper.getJsonFile("disney_actor.json"),
                DisneyActor::class.java
            )

            coEvery { disneyApiService.fetchDisneyCharacterById(ID) } returns response
            every { actorMapper.mapToDisneyActor(any()) } answers { actor }

            val result = disneyCharactersRepositoryImpl.fetchDisneyCharacterById(ID)
            Assert.assertEquals(Result.Success<DisneyActor>(actor), result)
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

        val result = disneyCharactersRepositoryImpl.fetchDisneyCharacterById(ID)
        Assert.assertEquals(Result.Error<DisneyActor>(exception), result)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private companion object {
        private const val ID = "268"
        private fun getDisneyListCharacter(character: Characters) = DisneyListCharacter(
            character.id ?: 0,
            character.name ?: "",
            character.imageUrl ?: ""
        )
    }
}
