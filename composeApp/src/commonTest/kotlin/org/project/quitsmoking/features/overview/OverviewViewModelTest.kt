package org.project.quitsmoking.features.overview

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.project.quitsmoking.features.overview.ui.OverviewViewModel
import org.project.quitsmoking.features.overview.domain.IOverviewUseCase
import org.project.quitsmoking.features.overview.domain.entities.OverviewModel
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class OverviewViewModelTest {

    private val initialTestOverview = OverviewModel(
        date = "__",
        time = "__",
        notSmokedSinceDays = "0",
        notSmokedSinceYears = "0",
        notSmokedSinceHours = "0",
        notSmokedSinceMinutes = "0",
        notSmokedSinceMonths = "0",
        savedCigarettes = 1,
        savedMoney = 0.0,
        savedTime = 0.0,
    )

    private val testOverview = OverviewModel(
        date = "2022-11-28",
        time = "23:00",
        notSmokedSinceDays = "20",
        notSmokedSinceYears = "2",
        notSmokedSinceHours = "4",
        notSmokedSinceMinutes = "34",
        notSmokedSinceMonths = "10",
        savedCigarettes = 3240,
        savedMoney = 1125.50,
        savedTime = 4560.0
    )

    @Test
    fun `emits overview statistics from use case`() = runTest {
        // Given
        val useCase = mock<IOverviewUseCase>()
        every { useCase.getStatistics() } returns flowOf(testOverview)
        val viewModel = OverviewViewModel(useCase)

        // When
        viewModel.statistic.test {
            //Then
            val initial = awaitItem()
            assertEquals(initialTestOverview, initial)
            val result = awaitItem()
            assertEquals(testOverview, result)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
