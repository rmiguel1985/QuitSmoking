package org.project.quitsmoking.features.overview.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.project.quitsmoking.utils.PreferenceKeys.CIGARETTES_PER_DAY
import org.project.quitsmoking.utils.PreferenceKeys.COST_PER_CIGARETTE
import org.project.quitsmoking.utils.PreferenceKeys.MINUTES_PER_CIGARETTE
import org.project.quitsmoking.utils.PreferenceKeys.STOP_DATE_TIMESTAMP
import org.project.quitsmoking.utils.PreferenceKeys.STOP_TIME

class OverviewDiskDataSource(private val dataStore: DataStore<Preferences>) :
    IOverViewDiskDataSource {
    override fun getQuitDate(): Flow<Long> = dataStore.data.map { preferences ->
        preferences[STOP_DATE_TIMESTAMP] ?: 0
    }

    override fun getQuitTime(): Flow<String> = dataStore.data.map { preferences ->
        preferences[STOP_TIME] ?: ""
    }

    override fun getDailyCigaretteCount(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[CIGARETTES_PER_DAY] ?: 0
    }

    override fun getMinutesPerCigarette(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[MINUTES_PER_CIGARETTE] ?: 0
    }

    override fun getCostPerCigarette(): Flow<Double> = dataStore.data.map { preferences ->
        preferences[COST_PER_CIGARETTE] ?: 0.0
    }
}