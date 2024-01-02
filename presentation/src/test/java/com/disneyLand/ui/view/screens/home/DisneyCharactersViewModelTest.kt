package com.disneyLand.ui.view.screens.home

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import app.cash.turbine.test
import com.disneyLand.BaseTest
import com.disneyLand.model.DisneyListCharacter
import com.disneyLand.ui.view.screens.home.DisneyListMviContract.DisneyListScreenIntent
import com.disneyLand.ui.view.screens.home.DisneyListMviContract.DisneyListScreenViewState
import com.disneyLand.usecase.DisneyCharactersListUsecaseImpl
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DisneyCharactersViewModelTest : BaseTest() {

    private lateinit var disneyCharactersViewModel: DisneyCharactersViewModel

    @MockK
    private lateinit var disneyCharactersListUsecase: DisneyCharactersListUsecaseImpl

    @Before
    override fun setUp() {
        super.setUp()
        disneyCharactersViewModel =
            DisneyCharactersViewModel(disneyCharactersListUsecase)
    }

    @Test
    fun `fetch disney characters list successfully GIVEN intent WHEN fetchDisneyCharacters called THEN verify usecase called to get success result`() =
        runTest {
            val pagingData = PagingData.from(disneyListCharacters)
            val flowListCharacter = flow { emit(pagingData) }
            coEvery { disneyCharactersListUsecase() } answers { flowListCharacter }

            disneyCharactersViewModel.sendIntent(DisneyListScreenIntent.FetchCharactersList)

            verify {
                disneyCharactersListUsecase()
            }
        }

    @Test
    fun `navigate to details screen when DisneyListScreenIntent NavigateToDetails intent passed`() =
        runTest {
            with(disneyCharactersViewModel) {
                sideEffect.test {
                    sendIntent(DisneyListScreenIntent.NavigateToDetails(ID))

                    Assert.assertEquals(
                        DisneyListMviContract.DisneyListScreenSideEffect.NavigateToDetailsScreen(
                            ID
                        ), awaitItem()
                    )
                    awaitComplete()
                }
            }
        }

    @Test
    fun `navigate back when DisneyListScreenIntent NavigateUp intent passed`() =
        runTest {
            with(disneyCharactersViewModel) {
                sideEffect.test {

                    sendIntent(DisneyListScreenIntent.NavigateUp)

                    Assert.assertEquals(
                        DisneyListMviContract.DisneyListScreenSideEffect.NavigateUp, awaitItem()
                    )
                    awaitComplete()
                }
            }
        }

    @Test
    fun `test load state is Error`() = runTest {
        val loadState = LoadStates(
            LoadState.Loading,
            LoadState.Loading,
            LoadState.Error(Throwable("Not found"))
        )

        disneyCharactersViewModel.handleLoadState(loadState)

        disneyCharactersViewModel.viewState.test {
            Assert.assertEquals(DisneyListScreenViewState.Error("Not found"), awaitItem())
        }
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
