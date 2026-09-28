package org.project.quitsmoking.features.overview

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.project.quitsmoking.features.overview.data.repository.IOverviewRepository
import org.project.quitsmoking.features.overview.domain.OverviewUseCase
import org.project.quitsmoking.features.overview.domain.entities.SavedTimeUnit
import org.project.quitsmoking.features.settings.data.model.SettingsModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class OverViewUseCaseTest {

    @Test
    fun `getStatistics should calculate correct overview data`() = runTest {
        val quit = LocalDateTime(2022, 11, 28, 23, 0)
        val daysPassed = 21
        val dailyCigaretteCount = 20
        val minutesPerCigarette = 5
        val costPerCigarette = 0.5
        val useCase = useCase(
            quit = quit,
            now = quit.toInstant(TimeZone.currentSystemDefault()) + daysPassed.days,
            dailyCigaretteCount = dailyCigaretteCount,
            minutesPerCigarette = minutesPerCigarette,
            costPerCigarette = costPerCigarette,
        )

        useCase.getStatistics().test {
            val item = awaitItem()

            assertEquals("23:00", item.time)
            assertEquals(420, item.savedCigarettes)
            assertEquals(210.0, item.savedMoney)
            assertEquals(35.0, item.savedTime)
            assertEquals(SavedTimeUnit.Hours, item.savedTimeUnit)
            assertEquals(daysPassed.toString(), item.notSmokedSinceDays)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getStatistics should truncate money to two decimals`() = runTest {
        val quit = LocalDateTime(2022, 11, 28, 12, 0)
        val useCase = useCase(
            quit = quit,
            now = quit.toInstant(TimeZone.currentSystemDefault()) + 1.days,
            dailyCigaretteCount = 1,
            minutesPerCigarette = 7,
            costPerCigarette = 0.1234,
        )

        useCase.getStatistics().test {
            val item = awaitItem()

            // 0.1234 * 1 cigarette * 1 day = 0.1234 -> 0.12
            // 7 minutes is below one hour, so time stays in minutes
            assertEquals(1, item.savedCigarettes)
            assertEquals(0.12, item.savedMoney)
            assertEquals(7.0, item.savedTime)
            assertEquals(SavedTimeUnit.Minutes, item.savedTimeUnit)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getStatistics should truncate saved hours to two decimals`() = runTest {
        val quit = LocalDateTime(2022, 11, 28, 12, 0)
        val useCase = useCase(
            quit = quit,
            now = quit.toInstant(TimeZone.currentSystemDefault()) + 10.days,
            dailyCigaretteCount = 1,
            minutesPerCigarette = 7,
            costPerCigarette = 0.1234,
        )

        useCase.getStatistics().test {
            val item = awaitItem()

            // money = 0.1234 * 10 = 1.234 -> 1.23
            // time = (7 min * 10 days) / 60 = 1.1666... -> 1.16 h
            assertEquals(1.23, item.savedMoney)
            assertEquals(1.16, item.savedTime)
            assertEquals(SavedTimeUnit.Hours, item.savedTimeUnit)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getStatistics should count a partial day toward saved cigarettes money and time`() = runTest {
        val quit = LocalDateTime(2026, 9, 24, 15, 45)
        val useCase = useCase(
            quit = quit,
            now = quit.toInstant(TimeZone.currentSystemDefault()) + 22.minutes,
            dailyCigaretteCount = 5,
            minutesPerCigarette = 5,
            costPerCigarette = 0.35,
        )

        useCase.getStatistics().test {
            val item = awaitItem()

            // 22 minutes is 22/1440 of a day
            // cigarettes = 5 * 22/1440 = 0.0763... -> rounds to 0
            // money = 0.35 * 5 * 22/1440 = 0.0267... -> 0.02
            // time = 5 * 5 * 22/1440 = 0.3819... minutes -> 0.38 min
            assertEquals("0", item.notSmokedSinceDays)
            assertEquals("0", item.notSmokedSinceHours)
            assertEquals("22", item.notSmokedSinceMinutes)
            assertEquals(0, item.savedCigarettes)
            assertEquals(0.02, item.savedMoney)
            assertEquals(0.38, item.savedTime)
            assertEquals(SavedTimeUnit.Minutes, item.savedTimeUnit)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getStatistics should count elapsed time within the same calendar day`() = runTest {
        val quit = LocalDateTime(2026, 9, 24, 0, 30)
        val useCase = useCase(
            quit = quit,
            now = quit.toInstant(TimeZone.currentSystemDefault()) + 23.hours,
            dailyCigaretteCount = 5,
            minutesPerCigarette = 5,
            costPerCigarette = 0.35,
        )

        useCase.getStatistics().test {
            val item = awaitItem()

            // 23 hours is 23/24 of a day, still the same date
            // cigarettes = 5 * 23/24 = 4.7916... -> rounds to 5
            // money = 0.35 * 5 * 23/24 = 1.6770... -> 1.67
            // time = 25 * 23/24 = 23.9583... minutes -> 23.95 min
            assertEquals("0", item.notSmokedSinceDays)
            assertEquals(5, item.savedCigarettes)
            assertEquals(1.67, item.savedMoney)
            assertEquals(23.95, item.savedTime)
            assertEquals(SavedTimeUnit.Minutes, item.savedTimeUnit)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getStatistics should show saved time in hours after one hour is reached`() = runTest {
        val quit = LocalDateTime(2026, 9, 24, 15, 45)
        val useCase = useCase(
            quit = quit,
            now = quit.toInstant(TimeZone.currentSystemDefault()) + 3.days,
            dailyCigaretteCount = 5,
            minutesPerCigarette = 5,
            costPerCigarette = 0.35,
        )

        useCase.getStatistics().test {
            val item = awaitItem()

            // 5 cigarettes * 5 minutes * 3 days = 75 minutes = 1.25 h
            assertEquals(15, item.savedCigarettes)
            assertEquals(5.25, item.savedMoney)
            assertEquals(1.25, item.savedTime)
            assertEquals(SavedTimeUnit.Hours, item.savedTimeUnit)

            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun useCase(
        quit: LocalDateTime,
        now: Instant,
        dailyCigaretteCount: Int,
        minutesPerCigarette: Int,
        costPerCigarette: Double,
    ): OverviewUseCase {
        val zone = TimeZone.currentSystemDefault()
        val clock = mock<Clock> {
            every { now() } returns now
        }
        val repository = mock<IOverviewRepository> {
            every { statistics } returns flowOf(
                SettingsModel(
                    quitTimestamp = quit.toInstant(zone).toEpochMilliseconds(),
                    quitTime = quit.toClockLabel(),
                    dailyCigaretteCount = dailyCigaretteCount,
                    minutesPerCigarette = minutesPerCigarette,
                    costPerCigarette = costPerCigarette,
                )
            )
        }
        return OverviewUseCase(repository, clock)
    }

    private fun LocalDateTime.toClockLabel(): String {
        val hourLabel = hour.toString().padStart(2, '0')
        val minuteLabel = minute.toString().padStart(2, '0')
        return "$hourLabel:$minuteLabel"
    }
}
