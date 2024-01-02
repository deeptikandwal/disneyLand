package com.disneyLand.ui.view.screens.details

import app.cash.turbine.test
import com.disneyLand.Outcome
import com.disneyLand.model.Actor
import com.disneyLand.model.DisneyActor
import com.disneyLand.ui.view.screens.details.DisneyDetailMviContract.DisneyDetailScreenIntent
import com.disneyLand.ui.view.screens.details.DisneyDetailMviContract.DisneyDetailScreenViewState
import com.disneyLand.usecase.DisneyActorUsecaseImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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

    private lateinit var disneyDetailsScreenViewModel: DisneyDetailScreenViewModel

    @MockK
    private lateinit var disneyActorUsecase: DisneyActorUsecaseImpl

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        disneyDetailsScreenViewModel = DisneyDetailScreenViewModel(disneyActorUsecase)
    }

    @Test
    fun `fetch disney actor successfully GIVEN intent WHEN fetchDisneyActor by Id is called THEN verify`() =
        runTest {
            val disneyActor = getDisneyActor()
            val actor = getActor()

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

    private fun getDisneyActor(): DisneyActor = DisneyActor(
        "Alladin",
        "Aladdin(film), The Return of Jafar",
        "World of color, Golden FairyTale Fanfare",
        "",
        "",
        ""
    )

    private fun getActor() = Actor(
        "ALLADIN",
        "This character featured in films, tv shows and video games like Aladdin(film), The Return of Jafar",
        "World of color, Golden FairyTale Fanfare",
        "",
        "",
        ""
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private companion object {
        const val ID = "209"
    }
}
