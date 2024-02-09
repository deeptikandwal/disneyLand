package com.disneyLand.ui.view.screens.home

import app.cash.turbine.test
import com.disneyLand.Result
import com.disneyLand.model.Character
import com.disneyLand.model.DisneyListCharacter
import com.disneyLand.source.HomeScreenMapper
import com.disneyLand.ui.view.screens.home.DisneyListMviContract.DisneyListScreenIntent
import com.disneyLand.ui.view.screens.home.DisneyListMviContract.DisneyListScreenViewState
import com.disneyLand.usecase.DisneyCharactersListUsecase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DisneyCharactersViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var disneyCharactersViewModel: DisneyCharactersViewModel

    private val disneyCharactersListUsecase = mockk<DisneyCharactersListUsecase>()

    private val homeScreenMapper = mockk<HomeScreenMapper>()

    private val list = listOf<Character>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val listCharacter = Result.Success<List<DisneyListCharacter>>(disneyListCharacters)
        coEvery { disneyCharactersListUsecase() } answers { listCharacter }
        every { homeScreenMapper.mapToHomeScreenData(any()) } returns list

        disneyCharactersViewModel = DisneyCharactersViewModel(
            disneyCharactersListUsecase,
            homeScreenMapper
        )
    }

    @Test
    fun `fetch disney characters list successfully GIVEN intent WHEN fetchDisneyCharacters called THEN verify usecase called to get success result`() =
        runTest {
            disneyCharactersViewModel.viewState.test {
                Assert.assertTrue(awaitItem() is DisneyListScreenViewState.Success)
            }
        }

    @Test
    fun `fetch disney characters list failed GIVEN intent WHEN fetchDisneyCharacters called THEN verify usecase called to get error result`() =
        runTest {
            disneyCharactersViewModel = DisneyCharactersViewModel(
                disneyCharactersListUsecase,
                homeScreenMapper
            )

            coEvery { disneyCharactersListUsecase() } answers {
                Result.Error(Exception())
            }

            disneyCharactersViewModel.sendIntent(DisneyListScreenIntent.FetchCharactersList)

            disneyCharactersViewModel.viewState.test {
                Assert.assertEquals(DisneyListScreenViewState.Loading, awaitItem())
                Assert.assertTrue(awaitItem() is DisneyListScreenViewState.Error)
            }
        }

    @Test
    fun `navigate to details screen when DisneyListScreenIntent NavigateToDetails intent passed`() =
        runTest {
            with(disneyCharactersViewModel) {
                sideEffect.test {
                    sendIntent(DisneyListScreenIntent.NavigateToDetails(ID))
                    Assert.assertTrue(awaitItem() is DisneyListMviContract.DisneyListScreenSideEffect.NavigateToDetailsScreen)
                }
            }
        }

    @Test
    fun `navigate back when DisneyListScreenIntent NavigateUp intent passed`() = runTest {
        with(disneyCharactersViewModel) {
            sideEffect.test {
                sendIntent(DisneyListScreenIntent.NavigateUp)

                Assert.assertTrue(awaitItem() is DisneyListMviContract.DisneyListScreenSideEffect.NavigateUp)
            }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private companion object {
        const val ID = 209
        val disneyListCharacters = arrayListOf(
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
}
