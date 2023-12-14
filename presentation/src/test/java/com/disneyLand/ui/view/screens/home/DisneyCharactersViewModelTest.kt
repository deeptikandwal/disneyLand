package com.disneyLand.ui.view.screens.home

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import app.cash.turbine.test
import com.disneyLand.model.Character
import com.disneyLand.model.DisneyListCharacter
import com.disneyLand.source.HomeScreenMapper
import com.disneyLand.ui.view.screens.home.DisneyListMviContract.DisneyListScreenIntent
import com.disneyLand.ui.view.screens.home.DisneyListMviContract.DisneyListScreenViewState
import com.disneyLand.usecase.DisneyCharactersListUsecase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
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
    fun `fetch disney characters list successfully GIVEN intent WHEN fetchDisneyCharacters called THEN verify usecase called to get success result`() =
        runTest {
            val pagingData = PagingData.from(getDisneyListCharacters())
            val pagingDataCharacter = PagingData.from(getDisneyCharacters())
            val flowListCharacter = flow { emit(pagingData) }
            coEvery { disneyCharactersListUsecase() } answers { flowListCharacter }
            every { mapper.mapToHomeScreenData(pagingData) } answers { pagingDataCharacter }
            disneyCharactersViewModel.sendIntent(DisneyListScreenIntent.FetchCharactersList)
            verify {
                disneyCharactersListUsecase()
            }
        }

    @Test
    fun `test load state is Error`() = runTest {
        val loadState = LoadStates(
            LoadState.Loading, LoadState.Loading, LoadState.Error(
                Throwable("Not found")
            )
        )
        disneyCharactersViewModel.handleLoadState(loadState)
        disneyCharactersViewModel.viewState.test {
            Assert.assertEquals(DisneyListScreenViewState.ERROR("Not found"), awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun getDisneyListCharacters(): List<DisneyListCharacter> {
        return arrayListOf(
            DisneyListCharacter(
                id = 247,
                name = "Angels",
                image = "https://static.wikia.nocookie.net/disney/images/c/cb/Angela_Fishberger.jpg"
            ),
            DisneyListCharacter(
                id = 223,
                name = "Erica Ange",
                image = "https://static.wikia.nocookie.net/disney/images/8/88/Erica_pic.png"
            ),
            DisneyListCharacter(
                id = 268,
                name = "Anthony Biddle",
                image = "https://static.wikia.nocookie.net/disney/images/6/64/Images_%2812%29-0.jpg"
            )
        )
    }

    private fun getDisneyCharacters(): List<Character> {
        return arrayListOf(
            Character(
                id = 247,
                name = "Angels",
                image = "https://static.wikia.nocookie.net/disney/images/c/cb/Angela_Fishberger.jpg"
            ),
            Character(
                id = 223,
                name = "Erica Ange",
                image = "https://static.wikia.nocookie.net/disney/images/8/88/Erica_pic.png"
            ),
            Character(
                id = 268,
                name = "Anthony Biddle",
                image = "https://static.wikia.nocookie.net/disney/images/6/64/Images_%2812%29-0.jpg"
            )
        )
    }

}
