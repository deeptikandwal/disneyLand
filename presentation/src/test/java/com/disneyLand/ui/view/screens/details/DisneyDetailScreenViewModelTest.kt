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

            with(disneyDetailsScreenViewModel) {
                sendIntent(DisneyDetailScreenIntent.FetchCharacterById(ID))
                viewState.test {
                    Assert.assertEquals(DisneyDetailScreenViewState.Loading, awaitItem())
                    Assert.assertEquals(DisneyDetailScreenViewState.Success(actor), awaitItem())
                }

                Assert.assertEquals(actor.name, disneyActor.name)
            }
        }

    @Test(expected = retrofit2.HttpException::class)
    fun `fetch disney actor failed GIVEN intent WHEN fetchDisneyActor by Id is called THEN verify`() =
        runTest {
            val exception = retrofit2.HttpException(
                retrofit2.Response.error<ResponseBody>(
                    503,
                    "Address not found".toResponseBody("plain/text".toMediaTypeOrNull())
                )
            )

            coEvery { disneyActorUsecase(ID) } answers {
                throw exception
            }

            with(disneyDetailsScreenViewModel) {
                sendIntent(DisneyDetailScreenIntent.FetchCharacterById(ID))
                viewState.test {
                    Assert.assertEquals(DisneyDetailScreenViewState.Loading, awaitItem())
                }
            }
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
