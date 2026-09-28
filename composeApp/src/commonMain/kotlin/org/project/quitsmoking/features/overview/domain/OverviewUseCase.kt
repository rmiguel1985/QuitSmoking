package org.project.quitsmoking.features.overview.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.project.quitsmoking.features.overview.domain.entities.OverviewModel
import org.project.quitsmoking.features.overview.domain.entities.SavedTimeUnit
import org.project.quitsmoking.features.overview.data.repository.IOverviewRepository
import org.project.quitsmoking.utils.getSplitTime
import kotlin.math.roundToInt
import kotlin.math.truncate
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class OverviewUseCase @OptIn(ExperimentalTime::class) constructor(
    private val repository: IOverviewRepository,
    private val clock: Clock
) :
    IOverviewUseCase {
    override fun getStatistics(): Flow<OverviewModel> =
        repository.statistics.map { statistics ->

            if (statistics.quitTimestamp == 0L) return@map OverviewModel()

            val currentZone = TimeZone.currentSystemDefault()

            val stopSmokingDate = Instant.fromEpochMilliseconds(statistics.quitTimestamp)
                .toLocalDateTime(TimeZone.currentSystemDefault())

            val (stopSmokingHour, stopSmokingMinutes) = statistics.quitTime.getSplitTime()

            val stopSmokingLocalDateTime = LocalDateTime(
                year = stopSmokingDate.year,
                month = stopSmokingDate.month,
                day = stopSmokingDate.day,
                hour = stopSmokingHour,
                minute = stopSmokingMinutes
            )

            val instant = clock.now()
            val quitInstant = stopSmokingLocalDateTime.toInstant(currentZone)
            val periodSinceQuit = quitInstant.periodUntil(instant, currentZone)
            val elapsedDays = (instant - quitInstant).inWholeMilliseconds
                .coerceAtLeast(0L) / MILLIS_PER_DAY
            val (savedTime, savedTimeUnit) = calculateSavedTime(
                timeSpendByCigarette = statistics.minutesPerCigarette.toDouble(),
                numberOfCigarettes = statistics.dailyCigaretteCount.toDouble(),
                elapsedDays = elapsedDays,
            )

            OverviewModel(
                date = stopSmokingDate.date.toString(),
                notSmokedSinceDays = periodSinceQuit.days.toString(),
                notSmokedSinceMonths = periodSinceQuit.months.toString(),
                notSmokedSinceYears = periodSinceQuit.years.toString(),
                notSmokedSinceHours = periodSinceQuit.hours.toString(),
                notSmokedSinceMinutes = periodSinceQuit.minutes.toString(),
                savedCigarettes = calculateSavedCigarettes(
                    numberOfCigarettes = statistics.dailyCigaretteCount,
                    elapsedDays = elapsedDays,
                ),
                savedMoney = calculateSavedMoney(
                    cigaretteCost = statistics.costPerCigarette,
                    elapsedDays = elapsedDays,
                    numOfCigarettesPerDay = statistics.dailyCigaretteCount,
                ),
                savedTime = savedTime,
                savedTimeUnit = savedTimeUnit,
                time = statistics.quitTime
            )
        }

    private fun calculateSavedMoney(
        cigaretteCost: Double,
        elapsedDays: Double,
        numOfCigarettesPerDay: Int,
    ) = ((cigaretteCost * numOfCigarettesPerDay) * elapsedDays).truncateToTwoDecimals()

    private fun calculateSavedCigarettes(
        numberOfCigarettes: Int,
        elapsedDays: Double,
    ) = (numberOfCigarettes * elapsedDays).roundToInt()

    private fun calculateSavedTime(
        numberOfCigarettes: Double,
        timeSpendByCigarette: Double,
        elapsedDays: Double,
    ): Pair<Double, SavedTimeUnit> {
        val savedMinutes = (timeSpendByCigarette * numberOfCigarettes) * elapsedDays
        return if (savedMinutes >= MINUTES_PER_HOUR) {
            (savedMinutes / MINUTES_PER_HOUR).truncateToTwoDecimals() to SavedTimeUnit.Hours
        } else {
            savedMinutes.truncateToTwoDecimals() to SavedTimeUnit.Minutes
        }
    }

    private fun Double.truncateToTwoDecimals(): Double =
        truncate(this * 100) / 100.0

    private companion object {
        const val MILLIS_PER_DAY = 86_400_000.0
        const val MINUTES_PER_HOUR = 60.0
    }
}
