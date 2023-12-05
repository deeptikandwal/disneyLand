package com.disneyLand.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.disneyLand.dto.DisneyCharactersListDto
import com.disneyland.data.source.FakeDisneyListCharacters
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class DisneyPagingSourceTest {

    private lateinit var disneyPagingSource: DisneyPagingSource

    @MockK
    private lateinit var disneyApiService: DisneyApiService

    @MockK
    private lateinit var disneyMapper: DisneyMapper

    @get:Rule
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        disneyPagingSource = DisneyPagingSource(disneyApiService, disneyMapper)
    }

    @Test
    fun `get characters list successfully`() = runTest {
        val list = FakeDisneyListCharacters.getDisneyListCharacters()
        coEvery { disneyMapper.mapToDisneyCharacter(any()) } returns list
        coEvery {
            disneyApiService.fetchDisneyCharacters(
                any(), any()
            )
        } returns FakeDisneyListCharacters.getDisneyListCharactersDto()
        val expectedResult = PagingSource.LoadResult.Page(
            data = list, prevKey = null, nextKey = null
        )
        Assert.assertEquals(
            expectedResult,
            disneyPagingSource.load(PagingSource.LoadParams.Refresh(1, 1, false))
        )
    }

    @Test
    fun `get characters list  failed`() = runTest {
        coEvery {
            disneyApiService.fetchDisneyCharacters(
                any(), any()
            )
        } coAnswers  {
            throw IOException()
        }
        val expectedResult =
            PagingSource.LoadResult.Error<Int, DisneyCharactersListDto>(IOException())
        Assert.assertEquals(
            expectedResult.toString(),
            disneyPagingSource.load(PagingSource.LoadParams.Refresh(0, 1, false)).toString()
        )
    }
}