package org.project.quitsmoking.features.health.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.project.quitsmoking.utils.PreferenceKeys.STOP_DATE_TIMESTAMP
import org.project.quitsmoking.utils.PreferenceKeys.STOP_TIME

class HealthDataSource(private val dataStore: DataStore<Preferences>): IHealthDataSource {
    override fun getQuitDate(): Flow<Long> = dataStore.data.map { preferences ->
        preferences[STOP_DATE_TIMESTAMP] ?: 0
    }

    override fun getQuitTime(): Flow<String> = dataStore.data.map { preferences ->
        preferences[STOP_TIME] ?: ""
    }
}