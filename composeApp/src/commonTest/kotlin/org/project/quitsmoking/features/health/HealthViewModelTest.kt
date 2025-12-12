package org.project.quitsmoking.features.health

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.project.quitsmoking.features.health.domain.IHealthUseCase
import org.project.quitsmoking.features.health.domain.model.HealthModel
import org.project.quitsmoking.features.health.ui.HealthViewModel
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HealthViewModelTest {
    private val initialHealthRecovery =
        HealthModel()

    private val testHealthRecovery =
        HealthModel(
            bloodPressure = 0.3f,
            carbonMonoxide = 0.2f,
            heartAttack = 0.7f,
            senseOfSmell = 0.8f,
            breathing = 0.9f,
            nicotineWithdrawal = 0.2f,
            capacity = 0.1f,
            lungHairs = 1.0f,
            coronaryArteries = 1.0f,
            heartAttack2Years = 0.2f,
            stroke = 0.5f,
            lungCancer = 0.4f,
            heartAttack15Years = 0.7f
        )

    @Test
    fun `health statistics are emitted from use case`() = runTest {
        // Given
        val useCase = mock<IHealthUseCase>()
        every { useCase.getHealthStatistics() } returns flowOf(testHealthRecovery)
        val viewModel = HealthViewModel(useCase)

        // When
        viewModel.healthStats.test {
            //Then
            val initial = awaitItem()
            assertEquals(initialHealthRecovery, initial)
            val result = awaitItem()
            assertEquals(testHealthRecovery, result)
            cancelAndIgnoreRemainingEvents()
        }
    }
}