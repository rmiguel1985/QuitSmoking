package org.project.quitsmoking.features.overview

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.project.quitsmoking.features.overview.data.datasource.IOverViewDiskDataSource
import org.project.quitsmoking.features.overview.data.repository.IOverviewRepository
import org.project.quitsmoking.features.overview.data.repository.OverviewRepository
import org.project.quitsmoking.features.settings.data.model.SettingsModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class OverviewRepositoryTest {
    private val diskDataSource: IOverViewDiskDataSource = mock()
    private lateinit var repository: IOverviewRepository

    @BeforeTest
    fun setUp() {
        every { diskDataSource.getQuitDate() } returns flowOf(123L)
        every { diskDataSource.getQuitTime() } returns flowOf("10:00")
        every { diskDataSource.getDailyCigaretteCount() } returns flowOf(20)
        every { diskDataSource.getMinutesPerCigarette() } returns flowOf(5)
        every { diskDataSource.getCostPerCigarette() } returns flowOf(0.5)

        repository = OverviewRepository(diskDataSource)
    }

    @Test
    fun `statistics flow emits combined expected model from data source`() = runTest {
        // Given
        val expectedModel = SettingsModel(
            quitTimestamp = 123L,
            quitTime = "10:00",
            dailyCigaretteCount = 20,
            minutesPerCigarette = 5,
            costPerCigarette = 0.5
        )

        // When
        repository.statistics.test {
            // Then
            assertEquals(expectedModel, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
