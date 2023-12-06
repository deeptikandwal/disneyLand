package com.disneyLand.ui.view

import androidx.paging.PagingData
import app.cash.turbine.test
import com.disneyLand.model.Character
import com.disneyLand.model.DisneyListCharacter
import com.disneyLand.source.HomeScreenMapper
import com.disneyLand.ui.components.DisneyListScreenIntent
import com.disneyLand.ui.components.DisneyListScreenViewState
import com.disneyLand.usecase.DisneyCharactersListUsecase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DisneyCharactersViewModelTest {

    private lateinit var disneyCharactersViewModel: DisneyCharactersViewModel

    @MockK
    private lateinit var disneyCharactersListUsecase: DisneyCharactersListUsecase

    @MockK
    private lateinit var mapper: HomeScreenMapper

    @MockK
    private lateinit var pagingData: PagingData<DisneyListCharacter>

    @MockK
    private lateinit var pagingDataCharacter: PagingData<Character>

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        disneyCharactersViewModel =
            DisneyCharactersViewModel(disneyCharactersListUsecase, mapper)
    }

    @Test
    fun `fetch disney characters list successfully GIVEN intent WHEN fetchDisneyCharacters called THEN verify`() =
        runTest {
            val flowListCharacter = flow { emit(pagingData) }
            val flowCharacter = flow { emit(pagingDataCharacter) }
            coEvery { disneyCharactersListUsecase() } returns flowListCharacter
            every { mapper.mapToHomeScreenData(pagingData) } returns pagingDataCharacter
            disneyCharactersViewModel.sendIntent(DisneyListScreenIntent.FetchCharactersList)
            disneyCharactersViewModel.viewState.test {
                Assert.assertEquals(DisneyListScreenViewState.SUCCESS(flowCharacter), awaitItem())
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
