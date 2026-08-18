package org.project.quitsmoking.features.overview.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.project.quitsmoking.features.overview.domain.entities.OverviewModel
import org.project.quitsmoking.features.overview.data.repository.IOverviewRepository
import org.project.quitsmoking.utils.getSplitTime
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

            val periodSinceQuit = stopSmokingLocalDateTime.toInstant(currentZone)
                .periodUntil(instant, currentZone)

            val daysTotal = stopSmokingLocalDateTime.toInstant(currentZone)
                .daysUntil(instant, currentZone)

            OverviewModel(
                date = stopSmokingDate.date.toString(),
                notSmokedSinceDays = periodSinceQuit.days.toString(),
                notSmokedSinceMonths = periodSinceQuit.months.toString(),
                notSmokedSinceYears = periodSinceQuit.years.toString(),
                notSmokedSinceHours = periodSinceQuit.hours.toString(),
                notSmokedSinceMinutes = periodSinceQuit.minutes.toString(),
                savedCigarettes = calculateSavedCigarettes(
                    numberOfCigarettes = statistics.dailyCigaretteCount,
                    numberOfDays = daysTotal
                ),
                savedMoney = calculateSavedMoney(
                    cigaretteCost = statistics.costPerCigarette,
                    numberOfDays = daysTotal,
                    numOfCigarettesPerDay = statistics.dailyCigaretteCount
                ),
                savedTime = calculateSavedTime(
                    timeSpendByCigarette = statistics.minutesPerCigarette.toDouble(),
                    numberOfCigarettes = statistics.dailyCigaretteCount.toDouble(),
                    totalDays = daysTotal
                ),
                time = statistics.quitTime
            )
        }

    private fun calculateSavedMoney(
        cigaretteCost: Double,
        numberOfDays: Int,
        numOfCigarettesPerDay: Int
    ) =
        ((cigaretteCost * numOfCigarettesPerDay) * numberOfDays).truncateToTwoDecimals()

    private fun calculateSavedCigarettes(numberOfCigarettes: Int, numberOfDays: Int) =
        numberOfCigarettes * numberOfDays

    private fun calculateSavedTime(
        numberOfCigarettes: Double,
        timeSpendByCigarette: Double,
        totalDays: Int
    ): Double =
        (((timeSpendByCigarette * numberOfCigarettes) * totalDays) / 60).truncateToTwoDecimals()

    private fun Double.truncateToTwoDecimals(): Double =
        truncate(this * 100) / 100.0
}
