package org.project.quitsmoking.utils

import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

// DataStore
object PreferenceKeys {
        val STOP_DATE_TIMESTAMP = longPreferencesKey("preferences_stop_date")
        val STOP_TIME = stringPreferencesKey("preferences_stop_time")
        val CIGARETTES_PER_DAY = intPreferencesKey("preferences_smoke_habit_cigarettes_per_day")
        val MINUTES_PER_CIGARETTE = intPreferencesKey("preferences_smoke_habit_cigarettes_time")
        val COST_PER_CIGARETTE = doublePreferencesKey("preferences_cigarette_cost")
}

object HealthRecoveryConstants {
    // Health
    const val BLOOD_PRESSURE = 0.0139
    const val CARBON_MONOXIDE = 0.3333
    const val HEART_ATTACK = 1.0
    const val SENSE_OF_SMELL = 2.0
    const val BREATHING = 3.0
    const val NICOTINE_WITHDRAWAL = 7.0
    const val CAPACITY = 90.0
    const val LUNG_HAIRS = 270.0
    const val CORONARY_ARTERIES = 365.0
    const val HEART_ATTACK_2_YEARS = 730.0
    const val STROKE = 1825.0
    const val LUNG_CANCER = 3650.0
    const val HEART_ATTACK_15_YEARS = 5475.0
}