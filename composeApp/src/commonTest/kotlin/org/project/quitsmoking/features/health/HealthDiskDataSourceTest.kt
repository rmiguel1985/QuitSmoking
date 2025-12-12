package org.project.quitsmoking.features.health

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesOf
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.project.quitsmoking.features.health.data.datasource.HealthDataSource
import org.project.quitsmoking.utils.PreferenceKeys.STOP_DATE_TIMESTAMP
import org.project.quitsmoking.utils.PreferenceKeys.STOP_TIME
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class HealthDiskDataSourceTest {
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var dataSource: HealthDataSource

    @BeforeTest
    fun setUp() {
        dataStore = mock<DataStore<Preferences>>()
        dataSource = HealthDataSource(dataStore)
    }

    @Test
    fun `getQuitDate returns timestamp when key exists`() = runTest {
        // Given
        val expectedTimestamp = 1669676400000L
        val preferences = preferencesOf(
            STOP_DATE_TIMESTAMP to expectedTimestamp
        )
        every { dataStore.data } returns flowOf(preferences)

        // When
        val result = dataSource.getQuitDate().first()

        // Then
        assertEquals(expectedTimestamp, result)
    }

    @Test
    fun `getQuitDate returns 0 when key does not exist`() = runTest {
        // Given
        val preferences = preferencesOf()
        every { dataStore.data } returns flowOf(preferences)

        // When
        val result = dataSource.getQuitDate().first()

        // Then
        assertEquals(0L, result)
    }

    @Test
    fun `getQuitTime returns time string when key exists`() = runTest {
        // Given
        val expectedTime = "23:00"
        val preferences = preferencesOf(
            STOP_TIME to expectedTime
        )
        every { dataStore.data } returns flowOf(preferences)

        // When
        val result = dataSource.getQuitTime().first()

        // Then
        assertEquals(expectedTime, result)
    }

    @Test
    fun `getQuitTime returns empty string when key does not exist`() = runTest {
        // Given
        val preferences = preferencesOf()
        every { dataStore.data } returns flowOf(preferences)

        // When
        val result = dataSource.getQuitTime().first()

        // Then
        assertEquals("", result)
    }
}