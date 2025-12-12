package org.project.quitsmoking.features.settings

import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.project.quitsmoking.features.settings.data.model.SettingsModel
import org.project.quitsmoking.features.settings.data.repository.ISettingsRepository
import org.project.quitsmoking.features.settings.domain.SettingsUseCase
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SettingsUseCaseTest {

    private lateinit var repository: ISettingsRepository
    private lateinit var useCase: SettingsUseCase

    @BeforeTest
    fun setUp() {
        repository = mock<ISettingsRepository>()
        useCase = SettingsUseCase(repository)
    }

    @Test
    fun `getSettings returns flow from repository`() = runTest {
        // Given
        val expectedSettings = SettingsModel(
            dailyCigaretteCount = 10,
            costPerCigarette = 1.5,
            minutesPerCigarette = 5,
            quitTimestamp = 123456789L,
            quitTime = "12:00"
        )
        every { repository.settings } returns flowOf(expectedSettings)

        // When
        val result = useCase.getSettings().first()

        // Then
        assertEquals(expectedSettings, result)
        verify { repository.settings }
    }

    @Test
    fun `setDate delegates to repository and returns success`() = runTest {
        // Given
        val date = 1669676400000L
        everySuspend { repository.updateQuitDate(date) } returns Result.success(Unit)

        // When
        val result = useCase.setDate(date)

        // Then
        assertTrue(result.isSuccess)
        verifySuspend { repository.updateQuitDate(date) }
    }

    @Test
    fun `setTime delegates to repository and returns success`() = runTest {
        // Given
        val time = "20:00"
        everySuspend { repository.updateQuitTime(time) } returns Result.success(Unit)

        // When
        val result = useCase.setTime(time)

        // Then
        assertTrue(result.isSuccess)
        verifySuspend { repository.updateQuitTime(time) }
    }

    @Test
    fun `setNumberOfCigarettes delegates to repository and returns success`() = runTest {
        // Given
        val count = 20
        everySuspend { repository.updateDailyCigaretteCount(count) } returns Result.success(Unit)

        // When
        val result = useCase.setNumberOfCigarettes(count)

        // Then
        assertTrue(result.isSuccess)
        verifySuspend { repository.updateDailyCigaretteCount(count) }
    }

    @Test
    fun `seMinutesPerCigarette delegates to repository and returns success`() = runTest {
        // Given
        val minutes = 7
        everySuspend { repository.updateMinutesPerCigarette(minutes) } returns Result.success(Unit)

        // When
        val result = useCase.seMinutesPerCigarette(minutes)

        // Then
        assertTrue(result.isSuccess)
        verifySuspend { repository.updateMinutesPerCigarette(minutes) }
    }

    @Test
    fun `setCigaretteCost delegates to repository and returns success`() = runTest {
        // Given
        val cost = 0.75
        everySuspend { repository.updateCigaretteCost(cost) } returns Result.success(Unit)

        // When
        val result = useCase.setCigaretteCost(cost)

        // Then
        assertTrue(result.isSuccess)
        verifySuspend { repository.updateCigaretteCost(cost) }
    }
}