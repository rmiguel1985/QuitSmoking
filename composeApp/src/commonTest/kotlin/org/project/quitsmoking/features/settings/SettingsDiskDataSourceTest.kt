package org.project.quitsmoking.features.settings

import dev.mokkery.everySuspend
import dev.mokkery.verifySuspend
import org.project.quitsmoking.features.settings.data.datasource.SettingsDiskDataSource
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesOf
import dev.mokkery.answering.calls
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.project.quitsmoking.utils.PreferenceKeys.CIGARETTES_PER_DAY
import org.project.quitsmoking.utils.PreferenceKeys.COST_PER_CIGARETTE
import org.project.quitsmoking.utils.PreferenceKeys.MINUTES_PER_CIGARETTE
import org.project.quitsmoking.utils.PreferenceKeys.STOP_DATE_TIMESTAMP
import org.project.quitsmoking.utils.PreferenceKeys.STOP_TIME
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SettingsDiskDataSourceTest {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var dataSource: SettingsDiskDataSource

    @BeforeTest
    fun setUp() {
        dataStore = mock<DataStore<Preferences>>()
        dataSource = SettingsDiskDataSource(dataStore)
    }

    @Test
    fun `getDailyCigaretteCount returns value when key exists`() = runTest {
        // Given
        val expected = 20
        val preferences = preferencesOf(CIGARETTES_PER_DAY to expected)
        every { dataStore.data } returns flowOf(preferences)

        // When
        val result = dataSource.getDailyCigaretteCount().first()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `getDailyCigaretteCount returns 0 when key does not exist`() = runTest {
        // Given
        val preferences = preferencesOf()
        every { dataStore.data } returns flowOf(preferences)

        // When
        val result = dataSource.getDailyCigaretteCount().first()

        // Then
        assertEquals(0, result)
    }

    @Test
    fun `getCostPerCigarette returns value when key exists`() = runTest {
        // Given
        val expected = 1.50
        val preferences = preferencesOf(COST_PER_CIGARETTE to expected)
        every { dataStore.data } returns flowOf(preferences)

        // When
        val result = dataSource.getCostPerCigarette().first()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `getMinutesPerCigarette returns value when key exists`() = runTest {
        // Given
        val expected = 7
        val preferences = preferencesOf(MINUTES_PER_CIGARETTE to expected)
        every { dataStore.data } returns flowOf(preferences)

        // When
        val result = dataSource.getMinutesPerCigarette().first()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `getQuitDate returns value when key exists`() = runTest {
        // Given
        val expected = 123456789L
        val preferences = preferencesOf(STOP_DATE_TIMESTAMP to expected)
        every { dataStore.data } returns flowOf(preferences)

        // When
        val result = dataSource.getQuitDate().first()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `getQuitTime returns value when key exists`() = runTest {
        // Given
        val expected = "14:30"
        val preferences = preferencesOf(STOP_TIME to expected)
        every { dataStore.data } returns flowOf(preferences)

        // When
        val result = dataSource.getQuitTime().first()

        // Then
        assertEquals(expected, result)
    }

    @Test
    fun `setQuitDate calls edit successfully`() = runTest {
        // Given
        val date = 1669676400000L
        everySuspend { dataStore.updateData(any()) } calls { preferencesOf(STOP_DATE_TIMESTAMP to date) }

        // When
        val result = dataSource.setQuitDate(date)

        // Then
        assertTrue(result.isSuccess)
        verifySuspend { dataStore.updateData(any()) }
    }

    @Test
    fun `setQuitTime calls edit successfully`() = runTest {
        // Given
        val time = "20:00"
        everySuspend { dataStore.updateData(any()) } calls { preferencesOf(STOP_TIME to time) }

        // When
        val result = dataSource.setQuitTime(time)

        // Then
        assertTrue(result.isSuccess)
        verifySuspend { dataStore.updateData(any()) }
    }

    @Test
    fun `setDailyCigaretteCount calls edit successfully`() = runTest {
        // Given
        val count = 10
        everySuspend { dataStore.updateData(any()) } calls { preferencesOf(CIGARETTES_PER_DAY to count) }

        // When
        val result = dataSource.setDailyCigaretteCount(count)

        // Then
        assertTrue(result.isSuccess)
        verifySuspend { dataStore.updateData(any()) }
    }

    @Test
    fun `setMinutesPerCigarette calls edit successfully`() = runTest {
        // Given
        val mins = 5
        everySuspend { dataStore.updateData(any()) } calls { preferencesOf(MINUTES_PER_CIGARETTE to mins) }

        // When
        val result = dataSource.setMinutesPerCigarette(mins)

        // Then
        assertTrue(result.isSuccess)
        verifySuspend { dataStore.updateData(any()) }
    }

    @Test
    fun `setCigaretteCost calls edit successfully`() = runTest {
        // Given
        val cost = 0.5
        everySuspend { dataStore.updateData(any()) } calls { preferencesOf(COST_PER_CIGARETTE to cost) }

        // When
        val result = dataSource.setCigaretteCost(cost)

        // Then
        assertTrue(result.isSuccess)
        verifySuspend { dataStore.updateData(any()) }
    }
}
