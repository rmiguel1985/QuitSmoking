package org.project.quitsmoking.features.health

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.project.quitsmoking.features.health.data.datasource.IHealthDataSource
import org.project.quitsmoking.features.health.data.repository.HealthRepository
import org.project.quitsmoking.features.health.data.repository.IHealthRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class HealthRepositoryTest {
    private val diskDataSource: IHealthDataSource = mock()
    private lateinit var repository: IHealthRepository
    private val expectedDate: Long = 123L
    private val expectedTime: String = "10:00"

    @BeforeTest
    fun setUp() {
        every { diskDataSource.getQuitDate() } returns flowOf(expectedDate)
        every { diskDataSource.getQuitTime() } returns flowOf(expectedTime)

        repository = HealthRepository(diskDataSource)
    }

    @Test
    fun `getQuitTime emits expected time`() = runTest {
        // When
        repository.getQuitTime().test {
            // Then
            assertEquals(expectedTime, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getQuitDate emits expected time`() = runTest {
        // When
        repository.getQuitDate().test {
            // Then
            assertEquals(expectedDate, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}