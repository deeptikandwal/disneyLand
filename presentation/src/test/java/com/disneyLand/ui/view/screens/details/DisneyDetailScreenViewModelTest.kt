package com.disneyLand.ui.view.screens.details

import app.cash.turbine.test
import com.disneyLand.BaseTest
import com.disneyLand.Outcome
import com.disneyLand.model.Actor
import com.disneyLand.model.DisneyActor
import com.disneyLand.source.DetailScreenMapper
import com.disneyLand.ui.view.screens.details.DisneyDetailMviContract.DisneyDetailScreenIntent
import com.disneyLand.ui.view.screens.details.DisneyDetailMviContract.DisneyDetailScreenViewState
import com.disneyLand.usecase.DisneyActorUsecaseImpl
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

class DisneyDetailScreenViewModelTest : BaseTest() {

    private lateinit var disneyDetailsScreenViewModel: DisneyDetailScreenViewModel

    @MockK
    private lateinit var disneyActorUsecase: DisneyActorUsecaseImpl

    @MockK
    private lateinit var detailScreenMapper: DetailScreenMapper

    @Before
    override fun setUp() {
        super.setUp()
        disneyDetailsScreenViewModel =
            DisneyDetailScreenViewModel(disneyActorUsecase, detailScreenMapper)
    }

    @Test
    fun `fetch disney actor successfully GIVEN intent WHEN fetchDisneyActor by Id is called THEN verify`() =
        runTest {
            coEvery { disneyActorUsecase(ID) } answers {
                flow {
                    emit(
                        Outcome.Success(disneyActor)
                    )
                }
            }
            every { detailScreenMapper.mapToDetailScreenData(any()) } answers {
                actor
            }

            with(disneyDetailsScreenViewModel) {
                sendIntent(DisneyDetailScreenIntent.FetchCharacterById(ID))

                viewState.test {
                    Assert.assertEquals(DisneyDetailScreenViewState.Loading, awaitItem())
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
                flow { emit(Outcome.Failure(exception)) }
            }

            with(disneyDetailsScreenViewModel) {
                sendIntent(DisneyDetailScreenIntent.FetchCharacterById(ID))
                viewState.test {
                    Assert.assertEquals(DisneyDetailScreenViewState.Loading, awaitItem())
                    Assert.assertTrue(awaitItem() is DisneyDetailScreenViewState.Error)
                }
            }
        }

    @Test
    fun `fetch actor failed GIVEN intent WHEN fetchDisneyActor by Id is called THEN verify`() =
        runTest {
            coEvery { disneyActorUsecase(ID) } answers {
                flow {
                    emit(Outcome.Failure<DisneyActor>(error = Throwable()))
                }
            }

            with(disneyDetailsScreenViewModel) {
                sendIntent(DisneyDetailScreenIntent.FetchCharacterById(ID))

                viewState.test {
                    Assert.assertEquals(DisneyDetailScreenViewState.Loading, awaitItem())
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
        Assert.assertEquals(
            "This character featured in films, tv shows and video games like ",
            disneyDetailsScreenViewModel.getActorCharacteristicsList(actor.description)[0]
        )
        Assert.assertEquals(
            " Aladdin(film), The Return of Jafar",
            disneyDetailsScreenViewModel.getActorCharacteristicsList(actor.description)[1]
        )
    }

    @Test
    fun `set visibility of Compose successfully`() {
        Assert.assertTrue(disneyDetailsScreenViewModel.setVisibilityOfCompose(actor.description))
        Assert.assertFalse(disneyDetailsScreenViewModel.setVisibilityOfCompose(""))
    }

    private companion object {
        const val ID = "209"
        val actor = Actor(
            "ALLADIN",
            "This character featured in films, tv shows and video games like : Aladdin(film), The Return of Jafar",
            "World of color, Golden FairyTale Fanfare",
            "",
            "",
            ""
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
