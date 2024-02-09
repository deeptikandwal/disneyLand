package com.disneyLand.ui.view.screens.details

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.disneyLand.Result
import com.disneyLand.model.Actor
import com.disneyLand.model.DisneyActor
import com.disneyLand.source.DetailScreenMapper
import com.disneyLand.ui.view.screens.details.DisneyDetailMviContract.DisneyDetailScreenIntent
import com.disneyLand.ui.view.screens.details.DisneyDetailMviContract.DisneyDetailScreenViewState
import com.disneyLand.usecase.DisneyActorUsecase
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
import org.junit.Test

class DisneyDetailScreenViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var disneyDetailsScreenViewModel: DisneyDetailScreenViewModel

    private val disneyActorUsecase = mockk<DisneyActorUsecase>()

    private val detailScreenMapper = mockk<DetailScreenMapper>()

    private val savedStateHandle = mockk<SavedStateHandle>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { savedStateHandle.get<String>("id") } returns ID
        coEvery { disneyActorUsecase(ID) } answers {
            Result.Success(disneyActor)
        }
        every { detailScreenMapper.mapToDetailScreenData(any()) } answers {
            actor
        }

        disneyDetailsScreenViewModel =
            DisneyDetailScreenViewModel(savedStateHandle, disneyActorUsecase, detailScreenMapper)
    }

    @Test
    fun `fetch disney actor successfully GIVEN intent WHEN fetchDisneyActor by Id is called THEN verify`() =
        runTest {
            with(disneyDetailsScreenViewModel) {
                sendIntent(DisneyDetailScreenIntent.FetchCharacterById(ID))

                viewState.test {
                    Assert.assertEquals(DisneyDetailScreenViewState.Success(actor), awaitItem())
                }

                Assert.assertEquals(actor.name, disneyActor.name)
            }
        }

    @Test
    fun `fetch disney actor failed GIVEN intent WHEN fetchDisneyActor by Id is called THEN verify`() =
        runTest {
            val exception = retrofit2.HttpException(
                retrofit2.Response.error<ResponseBody>(
                    503,
                    "Address not found".toResponseBody("plain/text".toMediaTypeOrNull())
                )
            )

            coEvery { disneyActorUsecase(ID) } answers {
                Result.Error(exception)
            }

            with(disneyDetailsScreenViewModel) {
                sendIntent(DisneyDetailScreenIntent.FetchCharacterById(ID))
                viewState.test {
                    Assert.assertTrue(awaitItem() is DisneyDetailScreenViewState.Success)
                    Assert.assertTrue(awaitItem() is DisneyDetailScreenViewState.Error)
                }
            }
        }

    @Test
    fun `fetch actor failed GIVEN intent WHEN fetchDisneyActor by Id is called THEN verify`() =
        runTest {
            coEvery { disneyActorUsecase(ID) } answers {
                Result.Error<DisneyActor>(error = Throwable())
            }

            with(disneyDetailsScreenViewModel) {
                sendIntent(DisneyDetailScreenIntent.FetchCharacterById(ID))

                viewState.test {
                    Assert.assertTrue(awaitItem() is DisneyDetailScreenViewState.Success)
                    Assert.assertTrue(awaitItem() is DisneyDetailScreenViewState.Error)
                }
            }
        }

    @Test
    fun `navigate back when DisneyListScreenIntent NavigateUp intent passed`() =
        runTest {
            with(disneyDetailsScreenViewModel) {
                sideEffect.test {
                    sendIntent(DisneyDetailScreenIntent.NavigateUp)

                    Assert.assertTrue(awaitItem() is DisneyDetailMviContract.DisneyDetailScreenSideEffect.NavigateUp)
                }
            }
        }

    @Test
    fun `get Actor Characteristics List successfully`() {
        disneyDetailsScreenViewModel =
            DisneyDetailScreenViewModel(savedStateHandle, disneyActorUsecase, detailScreenMapper)

        Assert.assertEquals(
            DescriptionHeading,
            disneyDetailsScreenViewModel.getActorCharacteristicsList(actor.description)[0]
        )
        Assert.assertEquals(
            Description,
            disneyDetailsScreenViewModel.getActorCharacteristicsList(actor.description)[1]
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private companion object {
        const val ID = "209"
        const val DescriptionHeading =
            "This character featured in films, tv shows and video games like "
        const val Description = " Aladdin(film), The Return of Jafar"
        val actor = Actor(
            "ALLADIN",
            "This character featured in films, tv shows and video games like : Aladdin(film), The Return of Jafar",
            "World of color, Golden FairyTale Fanfare"
        )

        val disneyActor = DisneyActor(
            "ALLADIN",
            "Aladdin(film), The Return of Jafar",
            "World of color, Golden FairyTale Fanfare",
            "",
            "",
            ""
        )
    }
}
