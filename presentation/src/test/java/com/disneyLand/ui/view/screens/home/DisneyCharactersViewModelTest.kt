package com.disneyLand.ui.view.screens.home

import app.cash.turbine.test
import com.disneyLand.BaseTest
import com.disneyLand.Outcome
import com.disneyLand.model.Character
import com.disneyLand.model.DisneyListCharacter
import com.disneyLand.source.HomeScreenMapper
import com.disneyLand.ui.view.screens.home.DisneyListMviContract.DisneyListScreenIntent
import com.disneyLand.ui.view.screens.home.DisneyListMviContract.DisneyListScreenViewState
import com.disneyLand.usecase.DisneyCharactersListUsecaseImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DisneyCharactersViewModelTest : BaseTest() {

    private lateinit var disneyCharactersViewModel: DisneyCharactersViewModel

    @MockK
    private lateinit var disneyCharactersListUsecase: DisneyCharactersListUsecaseImpl

    @MockK
    private lateinit var homeScreenMapper: HomeScreenMapper

    @Before
    override fun setUp() {
        super.setUp()
        disneyCharactersViewModel =
            DisneyCharactersViewModel(disneyCharactersListUsecase, homeScreenMapper)
    }

    @Test
    fun `fetch disney characters list successfully GIVEN intent WHEN fetchDisneyCharacters called THEN verify usecase called to get success result`() =
        runTest {
            val flowListCharacter = flow { emit(Outcome.Success(disneyListCharacters)) }
            val list = listOf<Character>()
            coEvery { disneyCharactersListUsecase() } answers { flowListCharacter }
            every { homeScreenMapper.mapToHomeScreenData(any()) } returns list

            disneyCharactersViewModel.sendIntent(DisneyListScreenIntent.FetchCharactersList)
            disneyCharactersViewModel.viewState.test {
                Assert.assertEquals(DisneyListScreenViewState.Loading, awaitItem())
                Assert.assertEquals(awaitItem(), DisneyListScreenViewState.Success(list))
            }
        }

    @Test
    fun `fetch disney characters list failed GIVEN intent WHEN fetchDisneyCharacters called THEN verify usecase called to get success result`() =
        runTest {
            val exception = retrofit2.HttpException(
                retrofit2.Response.error<ResponseBody>(
                    503,
                    "Address not found".toResponseBody("plain/text".toMediaTypeOrNull())
                )
            )
            coEvery { disneyCharactersListUsecase() } answers {
                flow {
                    emit(Outcome.Failure(exception))
                }
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
    fun `navigate back when DisneyListScreenIntent NavigateUp intent passed`() =
        runTest {
            with(disneyCharactersViewModel) {
                sideEffect.test {

                    sendIntent(DisneyListScreenIntent.NavigateUp)

                    Assert.assertTrue(awaitItem() is DisneyListMviContract.DisneyListScreenSideEffect.NavigateUp)
                }
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
