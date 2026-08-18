package org.project.quitsmoking.features.overview

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import org.project.quitsmoking.features.overview.data.repository.IOverviewRepository
import org.project.quitsmoking.features.overview.domain.OverviewUseCase
import org.project.quitsmoking.features.settings.data.model.SettingsModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class OverViewUseCaseTest {

    @Test
    fun `getStatistics should calculate correct overview data`() = runTest {
        // Given
        val zone = TimeZone.currentSystemDefault()
        val daysPassed = 21
        val quitDate = LocalDateTime(2022, 11, 28, 23, 0).toInstant(zone)
        val now = quitDate.plus(daysPassed, DateTimeUnit.DAY, zone)

        val quitTime = "08:30"
        val dailyCigaretteCount = 20
        val minutesPerCigarette = 5
        val costPerCigarette = 0.5

        val clock = mock<Clock> {
            every { now() } returns now
        }

        val repository = mock<IOverviewRepository> {
            every { statistics } returns flowOf(
                SettingsModel(
                    quitTimestamp = quitDate.toEpochMilliseconds(),
                    quitTime = quitTime,
                    dailyCigaretteCount = dailyCigaretteCount,
                    minutesPerCigarette = minutesPerCigarette,
                    costPerCigarette = costPerCigarette
                )
            )
        }

        // When
        val useCase = OverviewUseCase(repository, clock)

        useCase.getStatistics().test {
            val item = awaitItem()

            // Then
            val expectedSavedCigarettes = dailyCigaretteCount * daysPassed
            val expectedSavedMoney = costPerCigarette * dailyCigaretteCount * daysPassed
            val expectedSavedTime = ((minutesPerCigarette.toDouble() * dailyCigaretteCount) * daysPassed) / 60.0
            val expectedNotSmokedSinceDays = daysPassed.toString()

            assertEquals(quitTime, item.time)
            assertEquals(expectedSavedCigarettes, item.savedCigarettes)
            assertEquals(expectedSavedMoney, item.savedMoney)
            assertEquals(expectedSavedTime, item.savedTime)
            assertEquals(expectedNotSmokedSinceDays, item.notSmokedSinceDays)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getStatistics should truncate money and round time to two decimals`() = runTest {
        // Given
        val zone = TimeZone.currentSystemDefault()
        val daysPassed = 1
        val quitDate = LocalDateTime(2022, 11, 28, 23, 0).toInstant(zone)
        val now = quitDate.plus(daysPassed, DateTimeUnit.DAY, zone)

        // time = (7 min * 1 cig * 1 day) / 60 = 0.11666... -> 0.11
        // money = 0.1234 * 1 cig * 1 day = 0.1234 -> 0.12
        val dailyCigaretteCount = 1
        val minutesPerCigarette = 7
        val costPerCigarette = 0.1234

        val clock = mock<Clock> {
            every { now() } returns now
        }

        val repository = mock<IOverviewRepository> {
            every { statistics } returns flowOf(
                SettingsModel(
                    quitTimestamp = quitDate.toEpochMilliseconds(),
                    quitTime = "12:00",
                    dailyCigaretteCount = dailyCigaretteCount,
                    minutesPerCigarette = minutesPerCigarette,
                    costPerCigarette = costPerCigarette
                )
            )
        }

        // When
        val useCase = OverviewUseCase(repository, clock)

        useCase.getStatistics().test {
            val item = awaitItem()

            // Then
            assertEquals(0.12, item.savedMoney)
            assertEquals(0.11, item.savedTime)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
