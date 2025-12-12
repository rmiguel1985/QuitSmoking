package org.project.quitsmoking.features.health.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.project.quitsmoking.features.health.domain.model.HealthModel
import org.project.quitsmoking.features.health.data.repository.IHealthRepository
import org.project.quitsmoking.utils.HealthRecoveryConstants.BLOOD_PRESSURE
import org.project.quitsmoking.utils.HealthRecoveryConstants.BREATHING
import org.project.quitsmoking.utils.HealthRecoveryConstants.CAPACITY
import org.project.quitsmoking.utils.HealthRecoveryConstants.CARBON_MONOXIDE
import org.project.quitsmoking.utils.HealthRecoveryConstants.CORONARY_ARTERIES
import org.project.quitsmoking.utils.HealthRecoveryConstants.HEART_ATTACK
import org.project.quitsmoking.utils.HealthRecoveryConstants.HEART_ATTACK_15_YEARS
import org.project.quitsmoking.utils.HealthRecoveryConstants.HEART_ATTACK_2_YEARS
import org.project.quitsmoking.utils.HealthRecoveryConstants.LUNG_CANCER
import org.project.quitsmoking.utils.HealthRecoveryConstants.LUNG_HAIRS
import org.project.quitsmoking.utils.HealthRecoveryConstants.NICOTINE_WITHDRAWAL
import org.project.quitsmoking.utils.HealthRecoveryConstants.SENSE_OF_SMELL
import org.project.quitsmoking.utils.HealthRecoveryConstants.STROKE
import org.project.quitsmoking.utils.getSplitTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class HealthUseCase(
    private val repository: IHealthRepository,
    private val clock: Clock
) : IHealthUseCase {

    override fun getHealthStatistics(): Flow<HealthModel> =
        combine(
            repository.getQuitDate(),
            repository.getQuitTime(),
        ) { date, time ->
            val currentZone = TimeZone.currentSystemDefault()

            val stopSmokingLocalDate = Instant.fromEpochMilliseconds(date)
                .toLocalDateTime(currentZone)
                .date

            val (hours, minutes) = time.getSplitTime()
            val stopSmokingLocalTime = LocalTime(hour = hours, minute = minutes)

            val stopSmokingLocalDateTime = LocalDateTime(stopSmokingLocalDate, stopSmokingLocalTime)
            val instant = clock.now()

            val quitInstant = stopSmokingLocalDateTime.toInstant(currentZone)
            val durationSinceQuit = instant - quitInstant

            // Calculate total days including fractions
            val totalDays = durationSinceQuit.inWholeMinutes / (24.0 * 60.0)

            HealthModel(
                bloodPressure = HealthMilestone.BloodPressure.calculateProgress(totalDays),
                carbonMonoxide = HealthMilestone.CarbonMonoxide.calculateProgress(totalDays),
                heartAttack = HealthMilestone.HeartAttack.calculateProgress(totalDays),
                senseOfSmell = HealthMilestone.SenseOfSmell.calculateProgress(totalDays),
                breathing = HealthMilestone.Breathing.calculateProgress(totalDays),
                nicotineWithdrawal = HealthMilestone.NicotineWithdrawal.calculateProgress(totalDays),
                capacity = HealthMilestone.Capacity.calculateProgress(totalDays),
                lungHairs = HealthMilestone.LungHairs.calculateProgress(totalDays),
                coronaryArteries = HealthMilestone.CoronaryArteries.calculateProgress(totalDays),
                heartAttack2Years = HealthMilestone.HeartAttack2Years.calculateProgress(totalDays),
                stroke = HealthMilestone.Stroke.calculateProgress(totalDays),
                lungCancer = HealthMilestone.LungCancer.calculateProgress(totalDays),
                heartAttack15Years = HealthMilestone.HeartAttack15Years.calculateProgress(totalDays),
            )
        }

    private enum class HealthMilestone(val targetDays: Double) {
        BloodPressure(BLOOD_PRESSURE),
        CarbonMonoxide(CARBON_MONOXIDE),
        HeartAttack(HEART_ATTACK),
        SenseOfSmell(SENSE_OF_SMELL),
        Breathing(BREATHING),
        NicotineWithdrawal(NICOTINE_WITHDRAWAL),
        Capacity(CAPACITY),
        LungHairs(LUNG_HAIRS),
        CoronaryArteries(CORONARY_ARTERIES),
        HeartAttack2Years(HEART_ATTACK_2_YEARS),
        Stroke(STROKE),
        LungCancer(LUNG_CANCER),
        HeartAttack15Years(HEART_ATTACK_15_YEARS);

        fun calculateProgress(elapsedDays: Double): Float {
            if (targetDays <= 0) return 1.0f
            return (elapsedDays / targetDays).coerceIn(0.0, 1.0).toFloat()
        }
    }
}