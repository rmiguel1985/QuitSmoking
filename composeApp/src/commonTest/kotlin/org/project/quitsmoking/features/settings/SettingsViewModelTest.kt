package org.project.quitsmoking.features.settings

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.project.quitsmoking.features.settings.data.model.SettingsModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import org.project.quitsmoking.features.settings.domain.ISettingsUseCase
import org.project.quitsmoking.features.settings.ui.ErrorMessage
import org.project.quitsmoking.features.settings.ui.SettingsViewModel
import org.project.quitsmoking.features.settings.ui.SuccessMessage
import org.project.quitsmoking.features.settings.ui.UiStateSettings

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private lateinit var useCase: ISettingsUseCase
    private lateinit var viewModel: SettingsViewModel


    @BeforeTest
    fun setup() {
        useCase = mock()
        everySuspend { useCase.getSettings() } returns flowOf(
            SettingsModel(
                quitTimestamp = 0,
                quitTime = "",
                costPerCigarette = 0.0,
                dailyCigaretteCount = 0,
                minutesPerCigarette = 0
            )
        )

        viewModel = SettingsViewModel(useCase)
    }

    @Test
    fun `setCosts emits loading then success`() = runTest {
        everySuspend { useCase.setCigaretteCost(5.0) } returns Result.success(Unit)

        viewModel.uiStateSettings.test {
            viewModel.setCosts(5.0)
            advanceUntilIdle()

            assertEquals(UiStateSettings(isLoading = false), awaitItem())
            assertEquals(UiStateSettings(isLoading = true), awaitItem())
            assertEquals(
                UiStateSettings(successMessage = SuccessMessage.SUCCESS_SAVED_COSTS),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setCosts emits loading then error`() = runTest {
        everySuspend { useCase.setCigaretteCost(5.0) } returns Result.failure(Exception())

        viewModel.uiStateSettings.test {
            viewModel.setCosts(5.0)
            advanceUntilIdle()

            assertEquals(UiStateSettings(isLoading = false), awaitItem())
            assertEquals(UiStateSettings(isLoading = true), awaitItem())
            assertEquals(
                UiStateSettings(errorMessage = ErrorMessage.ERROR_SAVING_COSTS),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setNumberOfCigarettes success`() = runTest {
        everySuspend { useCase.setNumberOfCigarettes(10) } returns Result.success(Unit)

        viewModel.uiStateSettings.test {
            viewModel.setNumberOfCigarettes(10)
            advanceUntilIdle()

            assertEquals(UiStateSettings(isLoading = false), awaitItem())
            assertEquals(UiStateSettings(isLoading = true), awaitItem())
            assertEquals(
                UiStateSettings(successMessage = SuccessMessage.SUCCESS_SAVED_CIGARETTES),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setNumberOfCigarettes error`() = runTest {
        everySuspend { useCase.setNumberOfCigarettes(10) } returns Result.failure(Exception())

        viewModel.uiStateSettings.test {
            viewModel.setNumberOfCigarettes(10)
            advanceUntilIdle()

            assertEquals(UiStateSettings(isLoading = false), awaitItem())
            assertEquals(UiStateSettings(isLoading = true), awaitItem())
            assertEquals(
                UiStateSettings(errorMessage = ErrorMessage.ERROR_SAVING_CIGARETTES),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setCigarettesPerMinute success`() = runTest {
        everySuspend { useCase.seMinutesPerCigarette(3) } returns Result.success(Unit)

        viewModel.uiStateSettings.test {
            viewModel.setCigarettesPerMinute(3)
            advanceUntilIdle()

            assertEquals(UiStateSettings(isLoading = false), awaitItem())
            assertEquals(UiStateSettings(isLoading = true), awaitItem())
            assertEquals(
                UiStateSettings(successMessage = SuccessMessage.SUCCESS_SAVED_CIGARETTES),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setCigarettesPerMinute error`() = runTest {
        everySuspend { useCase.seMinutesPerCigarette(3) } returns Result.failure(Exception())

        viewModel.uiStateSettings.test {
            viewModel.setCigarettesPerMinute(3)
            advanceUntilIdle()

            assertEquals(UiStateSettings(isLoading = false), awaitItem())
            assertEquals(UiStateSettings(isLoading = true), awaitItem())
            assertEquals(
                UiStateSettings(errorMessage = ErrorMessage.ERROR_SAVING_CIGARETTES),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setDate success`() = runTest {
        everySuspend { useCase.setDate(1234L) } returns Result.success(Unit)

        viewModel.uiStateSettings.test {
            viewModel.setDate(1234L)
            advanceUntilIdle()

            assertEquals(UiStateSettings(isLoading = false), awaitItem())
            assertEquals(UiStateSettings(isLoading = true), awaitItem())
            assertEquals(
                UiStateSettings(successMessage = SuccessMessage.SUCCESS_SAVED_DATE),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setDate error`() = runTest {
        everySuspend { useCase.setDate(1234L) } returns Result.failure(Exception())

        viewModel.uiStateSettings.test {
            viewModel.setDate(1234L)
            advanceUntilIdle()

            assertEquals(UiStateSettings(isLoading = false), awaitItem())
            assertEquals(UiStateSettings(isLoading = true), awaitItem())
            assertEquals(
                UiStateSettings(errorMessage = ErrorMessage.ERROR_SAVING_DATE),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setTime success`() = runTest {
        everySuspend { useCase.setTime("10:00") } returns Result.success(Unit)

        viewModel.uiStateSettings.test {
            viewModel.setTime("10:00")
            advanceUntilIdle()

            assertEquals(UiStateSettings(isLoading = false), awaitItem())
            assertEquals(UiStateSettings(isLoading = true), awaitItem())
            assertEquals(
                UiStateSettings(successMessage = SuccessMessage.SUCCESS_SAVED_TIME),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setTime error`() = runTest {
        everySuspend { useCase.setTime("10:00") } returns Result.failure(Exception())

        viewModel.uiStateSettings.test {
            viewModel.setTime("10:00")
            advanceUntilIdle()

            assertEquals(UiStateSettings(isLoading = false), awaitItem())
            assertEquals(UiStateSettings(isLoading = true), awaitItem())
            assertEquals(
                UiStateSettings(errorMessage = ErrorMessage.ERROR_SAVING_TIME),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `clearUiStateSettings resets state`() = runTest {
        everySuspend { useCase.setTime("10:00") } returns Result.success(Unit)

        viewModel.uiStateSettings.test {
            viewModel.setTime("10:00")
            advanceUntilIdle()

            assertEquals(UiStateSettings(isLoading = false), awaitItem())
            assertEquals(UiStateSettings(isLoading = true), awaitItem())

            assertEquals(
                UiStateSettings(successMessage = SuccessMessage.SUCCESS_SAVED_TIME),
                awaitItem()
            )

            viewModel.clearUiStateSettings()
            advanceUntilIdle()

            assertEquals(UiStateSettings(), awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}
