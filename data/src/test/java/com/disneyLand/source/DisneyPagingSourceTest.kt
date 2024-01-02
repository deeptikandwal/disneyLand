package com.disneyLand.source

import android.net.Uri
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.disneyLand.BaseTest
import com.disneyLand.dto.Characters
import com.disneyLand.dto.DisneyCharactersList
import com.disneyLand.model.DisneyListCharacter
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import net.bytebuddy.matcher.ElementMatchers.any
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class DisneyPagingSourceTest : BaseTest() {

    private lateinit var disneyPagingSource: DisneyPagingSource

    @MockK
    private lateinit var disneyApiService: DisneyApiService

    @MockK
    private lateinit var disneyMapper: DisneyMapper

    @Before
    override fun setUp() {
        super.setUp()
        disneyPagingSource = DisneyPagingSource(disneyApiService, disneyMapper)
    }

    @Test
    fun `get characters list successfully`() = runTest {
        val list = FakeDisneyListCharacters.lisOfDisneyListCharacters
        val dto = mockk<DisneyCharactersList>()
        val data = mockk<ArrayList<Characters>>()
        every { dto.data } returns data
        every { disneyMapper.mapToDisneyCharacter(any()) } returns list
        coEvery {
            disneyApiService.fetchDisneyCharacters(
                any(),
                any()
            )
        } returns FakeDisneyListCharacters.disneyListCharacters
        mockkStatic(Uri::class)
        val uriMock = mockk<Uri>()
        every { Uri.parse(any()) } returns uriMock
        every { uriMock.getQueryParameter(any()) } answers {
            "2"
        }
        val expectedResult = PagingSource.LoadResult.Page(
            data = list,
            prevKey = null,
            nextKey = 2
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
                any(),
                any()
            )
        } coAnswers {
            throw IOException()
        }
        val expectedResult =
            PagingSource.LoadResult.Error<Int, DisneyCharactersList>(IOException())
        Assert.assertEquals(
            expectedResult.toString(),
            disneyPagingSource.load(PagingSource.LoadParams.Refresh(0, 1, false)).toString()
        )
    }

    @Test
    fun `getRefreshKey() returns the first page key`() {
        val state = PagingState<Int, DisneyListCharacter>(
            pages = listOf(),
            anchorPosition = 0,
            leadingPlaceholderCount = 0,
            config = PagingConfig(20)
        )

        val refreshKey = disneyPagingSource.getRefreshKey(state)

        Assert.assertEquals(0,refreshKey)
    }
}
