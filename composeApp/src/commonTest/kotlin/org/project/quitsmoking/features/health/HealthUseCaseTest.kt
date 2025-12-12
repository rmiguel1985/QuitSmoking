package org.project.quitsmoking.features.health

import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import org.project.quitsmoking.features.health.data.repository.IHealthRepository
import org.project.quitsmoking.features.health.domain.HealthUseCase
import org.project.quitsmoking.utils.HealthRecoveryConstants.BREATHING
import org.project.quitsmoking.utils.HealthRecoveryConstants.CAPACITY
import org.project.quitsmoking.utils.HealthRecoveryConstants.CARBON_MONOXIDE
import org.project.quitsmoking.utils.HealthRecoveryConstants.CORONARY_ARTERIES
import org.project.quitsmoking.utils.HealthRecoveryConstants.HEART_ATTACK
import org.project.quitsmoking.utils.HealthRecoveryConstants.LUNG_CANCER
import org.project.quitsmoking.utils.HealthRecoveryConstants.LUNG_HAIRS
import org.project.quitsmoking.utils.HealthRecoveryConstants.NICOTINE_WITHDRAWAL
import org.project.quitsmoking.utils.HealthRecoveryConstants.SENSE_OF_SMELL
import org.project.quitsmoking.utils.HealthRecoveryConstants.STROKE
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class HealthUseCaseTest {
    private lateinit var repository: IHealthRepository
    private lateinit var clock: Clock
    private lateinit var useCase: HealthUseCase
    private val zone = TimeZone.currentSystemDefault()
    private val quitDate = LocalDateTime(2022, 11, 28, 23, 0).toInstant(zone)

    @BeforeTest
    fun setUp() {
        repository = mock<IHealthRepository> {
            every { getQuitDate() } returns flowOf(quitDate.toEpochMilliseconds())
            every { getQuitTime() } returns flowOf("23:00")
        }

        clock = mock<Clock>()
        useCase = HealthUseCase(repository, clock = clock)
    }

    @Test
    fun `returns half progress for blood pressure after 10 and a half minutes`() = runTest {
        // Given
        val now = quitDate
            .plus(10, DateTimeUnit.MINUTE, zone)
            .plus(30, DateTimeUnit.SECOND, zone)

        every { clock.now() } returns now

        // When
        val healthStats = useCase.getHealthStatistics().first()

        // Then
        assertEquals(0.5f, healthStats.bloodPressure, 0.001f)
    }

    @Test
    fun `returns full progress for blood pressure after 21 minutes`() = runTest {
        // Given
        val now = quitDate.plus(21, DateTimeUnit.MINUTE, zone)
        every { clock.now() } returns now // Define the specific time for this test

        // When
        val healthStats = useCase.getHealthStatistics().first()

        // Then
        assertEquals(1.0f, healthStats.bloodPressure)
    }

    @Test
    fun `returns half progress for heart attack risk after 1 year`() = runTest {
        // Given
        val now = quitDate.plus(1, DateTimeUnit.YEAR, zone)
        every { clock.now() } returns now

        // When
        val healthStats = useCase.getHealthStatistics().first()

        // Then
        assertEquals(0.5f, healthStats.heartAttack2Years)
    }

    @Test
    fun `returns full progress for heart attack risk after 2 year`() = runTest {
        // Given
        val now = quitDate.plus(2, DateTimeUnit.YEAR, zone)
        every { clock.now() } returns now

        // When
        val healthStats = useCase.getHealthStatistics().first()

        // Then
        assertEquals(1.0f, healthStats.heartAttack2Years)
    }

    @Test
    fun `returns half progress for carbon monoxide`() = runTest {
        val halfMinutes = CARBON_MONOXIDE * 24 * 60 / 2
        val now = quitDate.plus(halfMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(0.5f, stats.carbonMonoxide, 0.003f)
    }

    @Test
    fun `returns full progress for carbon monoxide`() = runTest {
        val fullMinutes = CARBON_MONOXIDE * 24 * 60
        val now = quitDate.plus(fullMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(1f, stats.carbonMonoxide, 0.002f)
    }

    @Test
    fun `returns half progress for heart attack`() = runTest {
        val halfMinutes = HEART_ATTACK * 24 * 60 / 2
        val now = quitDate.plus(halfMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(0.5f, stats.heartAttack, 0.001f)
    }

    @Test
    fun `returns full progress for heart attack`() = runTest {
        val fullMinutes = HEART_ATTACK * 24 * 60
        val now = quitDate.plus(fullMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(1f, stats.heartAttack)
    }

    @Test
    fun `returns half progress for sense of smell`() = runTest {
        val halfMinutes = SENSE_OF_SMELL * 24 * 60 / 2
        val now = quitDate.plus(halfMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(0.5f, stats.senseOfSmell, 0.001f)
    }

    @Test
    fun `returns full progress for sense of smell`() = runTest {
        val fullMinutes = SENSE_OF_SMELL * 24 * 60
        val now = quitDate.plus(fullMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(1f, stats.senseOfSmell)
    }

    @Test
    fun `returns half progress for breathing`() = runTest {
        val halfMinutes = BREATHING * 24 * 60 / 2
        val now = quitDate.plus(halfMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(0.5f, stats.breathing, 0.001f)
    }

    @Test
    fun `returns full progress for breathing`() = runTest {
        val fullMinutes = BREATHING * 24 * 60
        val now = quitDate.plus(fullMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(1f, stats.breathing)
    }

    @Test
    fun `returns half progress for nicotine withdrawal`() = runTest {
        val halfMinutes = NICOTINE_WITHDRAWAL * 24 * 60 / 2
        val now = quitDate.plus(halfMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(0.5f, stats.nicotineWithdrawal, 0.001f)
    }

    @Test
    fun `returns full progress for nicotine withdrawal`() = runTest {
        val fullMinutes = NICOTINE_WITHDRAWAL * 24 * 60
        val now = quitDate.plus(fullMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(1f, stats.nicotineWithdrawal)
    }

    @Test
    fun `returns half progress for capacity`() = runTest {
        val halfMinutes = CAPACITY * 24 * 60 / 2
        val now = quitDate.plus(halfMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(0.5f, stats.capacity, 0.001f)
    }

    @Test
    fun `returns full progress for capacity`() = runTest {
        val fullMinutes = CAPACITY * 24 * 60
        val now = quitDate.plus(fullMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(1f, stats.capacity)
    }

    @Test
    fun `returns half progress for lung hairs`() = runTest {
        val halfMinutes = LUNG_HAIRS * 24 * 60 / 2
        val now = quitDate.plus(halfMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(0.5f, stats.lungHairs, 0.001f)
    }

    @Test
    fun `returns full progress for lung hairs`() = runTest {
        val fullMinutes = LUNG_HAIRS * 24 * 60
        val now = quitDate.plus(fullMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(1f, stats.lungHairs)
    }

    @Test
    fun `returns half progress for coronary arteries`() = runTest {
        val halfMinutes = CORONARY_ARTERIES * 24 * 60 / 2
        val now = quitDate.plus(halfMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(0.5f, stats.coronaryArteries, 0.001f)
    }

    @Test
    fun `returns full progress for coronary arteries`() = runTest {
        val fullMinutes = CORONARY_ARTERIES * 24 * 60
        val now = quitDate.plus(fullMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(1f, stats.coronaryArteries)
    }

    @Test
    fun `returns half progress for stroke`() = runTest {
        val halfMinutes = STROKE * 24 * 60 / 2
        val now = quitDate.plus(halfMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(0.5f, stats.stroke, 0.001f)
    }

    @Test
    fun `returns full progress for stroke`() = runTest {
        val fullMinutes = STROKE * 24 * 60
        val now = quitDate.plus(fullMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(1f, stats.stroke)
    }

    @Test
    fun `returns half progress for lung cancer`() = runTest {
        val halfMinutes = LUNG_CANCER * 24 * 60 / 2
        val now = quitDate.plus(halfMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(0.5f, stats.lungCancer, 0.001f)
    }

    @Test
    fun `returns full progress for lung cancer`() = runTest {
        val fullMinutes = LUNG_CANCER * 24 * 60
        val now = quitDate.plus(fullMinutes.toLong(), DateTimeUnit.MINUTE, zone)

        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(1f, stats.lungCancer)
    }

    @Test
    fun `returns half progress for heart attack 15 years`() = runTest {
        val months = (15 * 12) / 2 // 90 months = 7.5 years
        val now = quitDate.plus(months, DateTimeUnit.MONTH, zone)
        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(0.5f, stats.heartAttack15Years, 0.001f)
    }

    @Test
    fun `returns full progress for heart attack 15 years`() = runTest {
        val now = quitDate.plus(15, DateTimeUnit.YEAR, zone)
        every { clock.now() } returns now

        val stats = useCase.getHealthStatistics().first()

        assertEquals(1f, stats.heartAttack15Years)
    }
}