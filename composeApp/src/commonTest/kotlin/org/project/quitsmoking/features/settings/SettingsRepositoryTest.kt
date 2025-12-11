package org.project.quitsmoking.features.settings

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.project.quitsmoking.features.settings.data.datasource.ISettingsDiskDataSource
import org.project.quitsmoking.features.settings.data.model.SettingsModel
import org.project.quitsmoking.features.settings.data.repository.ISettingsRepository
import org.project.quitsmoking.features.settings.data.repository.SettingsRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SettingsRepositoryTest {
    private val diskDataSource: ISettingsDiskDataSource = mock()
    private lateinit var repository: ISettingsRepository

    @BeforeTest
    fun setUp() {
        every { diskDataSource.getQuitDate() } returns flowOf(123L)
        every { diskDataSource.getCostPerCigarette() } returns flowOf(0.5)
        every { diskDataSource.getQuitTime() } returns flowOf("10:00")
        every { diskDataSource.getDailyCigaretteCount() } returns flowOf(20)
        every { diskDataSource.getMinutesPerCigarette() } returns flowOf(5)

        repository = SettingsRepository(diskDataSource)
    }

    @Test
    fun `settings flow emits combined expected model from data source`() = runTest {
        // Given
        val expectedModel = SettingsModel(
            quitTimestamp = 123L,
            quitTime = "10:00",
            dailyCigaretteCount = 20,
            minutesPerCigarette = 5,
            costPerCigarette = 0.5
        )

        // When
        repository.settings.test {
            // Then
            assertEquals(expectedModel, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateQuitDate fun returns success and call data datasource with expected values only once`() =
        runTest {
            // Given
            val testDate = 12345L
            val expectedResult = Result.success(Unit)
            everySuspend { diskDataSource.setQuitDate(testDate) } returns expectedResult

            // When
            val result = repository.updateQuitDate(testDate)

            // Then
            verifySuspend(VerifyMode.Companion.exactly(1)) { diskDataSource.setQuitDate(testDate) }
            assertEquals(expectedResult, result)
        }

    @Test
    fun `updateQuitTime fun returns success and call data datasource with expected values only once`() =
        runTest {
            // Given
            val testTime = "12:30"
            val expectedResult = Result.success(Unit)
            everySuspend { diskDataSource.setQuitTime(testTime) } returns expectedResult

            // When
            val result = repository.updateQuitTime(testTime)

            // Then
            verifySuspend(VerifyMode.Companion.exactly(1)) { diskDataSource.setQuitTime(testTime) }
            assertEquals(expectedResult, result)
        }

    @Test
    fun `updateDailyCigaretteCount fun returns success and call data datasource with expected values only once`() =
        runTest {
            // Given
            val testCount = 25
            val expectedResult = Result.success(Unit)
            everySuspend { diskDataSource.setDailyCigaretteCount(testCount) } returns expectedResult

            // When
            val result = repository.updateDailyCigaretteCount(testCount)

            // Then
            verifySuspend(VerifyMode.Companion.exactly(1)) {
                diskDataSource.setDailyCigaretteCount(
                    testCount
                )
            }
            assertEquals(expectedResult, result)
        }

    @Test
    fun `updateMinutesPerCigarette fun returns success and call data datasource with expected values only once`() =
        runTest {
            // Given
            val testMinutes = 7
            val expectedResult = Result.success(Unit)
            everySuspend { diskDataSource.setMinutesPerCigarette(testMinutes) } returns expectedResult

            // When
            val result = repository.updateMinutesPerCigarette(testMinutes)

            // Then
            verifySuspend(VerifyMode.Companion.exactly(1)) {
                diskDataSource.setMinutesPerCigarette(
                    testMinutes
                )
            }
            assertEquals(expectedResult, result)
        }

    @Test
    fun `updateCigaretteCost fun returns success and call data datasource with expected values only once`() =
        runTest {
            // Given
            val testCost = 0.75
            val expectedResult = Result.success(Unit)
            everySuspend { diskDataSource.setCigaretteCost(testCost) } returns expectedResult

            // When
            val result = repository.updateCigaretteCost(testCost)

            // Then
            verifySuspend(VerifyMode.Companion.exactly(1)) {
                diskDataSource.setCigaretteCost(
                    testCost
                )
            }
            assertEquals(expectedResult, result)
        }
}