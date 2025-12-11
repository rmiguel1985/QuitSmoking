package org.project.quitsmoking.features.settings.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.project.quitsmoking.utils.PreferenceKeys.CIGARETTES_PER_DAY
import org.project.quitsmoking.utils.PreferenceKeys.COST_PER_CIGARETTE
import org.project.quitsmoking.utils.PreferenceKeys.STOP_DATE
import org.project.quitsmoking.utils.PreferenceKeys.STOP_TIME
import org.project.quitsmoking.utils.PreferenceKeys.TIME_SPENT_PER_CIGARETTE

class SettingsDiskDataSource(private val dataStore: DataStore<Preferences>) :
    ISettingsDiskDataSource {
    override fun getQuitDate(): Flow<Long> = dataStore.data.map { preferences ->
        preferences[longPreferencesKey(STOP_DATE)] ?: 0
    }

    override fun getQuitTime(): Flow<String> = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(STOP_TIME)] ?: ""
    }

    override fun getDailyCigaretteCount(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[intPreferencesKey(CIGARETTES_PER_DAY)] ?: 0
    }

    override fun getMinutesPerCigarette(): Flow<Int> = dataStore.data.map { preferences ->
        preferences[intPreferencesKey(TIME_SPENT_PER_CIGARETTE)] ?: 0
    }

    override fun getCostPerCigarette(): Flow<Double> = dataStore.data.map { preferences ->
        preferences[doublePreferencesKey(COST_PER_CIGARETTE)] ?: 0.0
    }

    override suspend fun setQuitDate(date: Long): Result<Unit> = runCatching {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(STOP_DATE)] = date
        }

    }

    override suspend fun setQuitTime(time: String): Result<Unit> = runCatching {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(STOP_TIME)] = time
        }
    }

    override suspend fun setDailyCigaretteCount(numberOfCigarettes: Int): Result<Unit> =
        runCatching {
            dataStore.edit { preferences ->
                preferences[intPreferencesKey(CIGARETTES_PER_DAY)] =
                    numberOfCigarettes
            }
        }

    override suspend fun setMinutesPerCigarette(minutesPerCigarette: Int): Result<Unit> =
        runCatching {
            dataStore.edit { preferences ->
                preferences[intPreferencesKey(TIME_SPENT_PER_CIGARETTE)] =
                    minutesPerCigarette
            }
        }

    override suspend fun setCigaretteCost(cost: Double): Result<Unit> = runCatching {
        dataStore.edit { preferences ->
            preferences[doublePreferencesKey(COST_PER_CIGARETTE)] = cost
        }
    }
}