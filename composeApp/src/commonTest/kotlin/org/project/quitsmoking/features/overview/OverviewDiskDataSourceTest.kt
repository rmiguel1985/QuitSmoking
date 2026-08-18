package org.project.quitsmoking.features.overview

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesOf
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.project.quitsmoking.features.overview.data.datasource.OverviewDiskDataSource
import org.project.quitsmoking.utils.PreferenceKeys.CIGARETTES_PER_DAY
import org.project.quitsmoking.utils.PreferenceKeys.COST_PER_CIGARETTE
import org.project.quitsmoking.utils.PreferenceKeys.MINUTES_PER_CIGARETTE
import org.project.quitsmoking.utils.PreferenceKeys.STOP_DATE_TIMESTAMP
import org.project.quitsmoking.utils.PreferenceKeys.STOP_TIME
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class OverviewDiskDataSourceTest {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var dataSource: OverviewDiskDataSource

    @BeforeTest
    fun setUp() {
        dataStore = mock<DataStore<Preferences>>()
        dataSource = OverviewDiskDataSource(dataStore)
    }

    @Test
    fun `getQuitDate returns value from dataStore`() = runTest {
        // Given
        val expected = 123456789L
        every { dataStore.data } returns flowOf(preferencesOf(STOP_DATE_TIMESTAMP to expected))

        // When
        val result = dataSource.getQuitDate().first()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `getQuitDate returns default 0 when key is missing`() = runTest {
        // Given
        every { dataStore.data } returns flowOf(preferencesOf())

        // When
        val result = dataSource.getQuitDate().first()

        // Then
        assertEquals(0L, result)
    }

    @Test
    fun `getQuitTime returns value from dataStore`() = runTest {
        // Given
        val expected = "12:30"
        every { dataStore.data } returns flowOf(preferencesOf(STOP_TIME to expected))

        // When
        val result = dataSource.getQuitTime().first()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `getQuitTime returns default empty string when key is missing`() = runTest {
        // Given
        every { dataStore.data } returns flowOf(preferencesOf())

        // When
        val result = dataSource.getQuitTime().first()

        // Then
        assertEquals("", result)
    }

    @Test
    fun `getDailyCigaretteCount returns value from dataStore`() = runTest {
        // Given
        val expected = 20
        every { dataStore.data } returns flowOf(preferencesOf(CIGARETTES_PER_DAY to expected))

        // When
        val result = dataSource.getDailyCigaretteCount().first()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `getMinutesPerCigarette returns value from dataStore`() = runTest {
        // Given
        val expected = 5
        every { dataStore.data } returns flowOf(preferencesOf(MINUTES_PER_CIGARETTE to expected))

        // When
        val result = dataSource.getMinutesPerCigarette().first()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `getCostPerCigarette returns value from dataStore`() = runTest {
        // Given
        val expected = 0.5
        every { dataStore.data } returns flowOf(preferencesOf(COST_PER_CIGARETTE to expected))

        // When
        val result = dataSource.getCostPerCigarette().first()

        // Then
        assertEquals(expected, result)
    }
}
