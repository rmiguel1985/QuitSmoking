package org.project.quitsmoking.features.settings.data.repository

import kotlinx.coroutines.flow.Flow
import org.project.quitsmoking.features.settings.data.model.SettingsModel

interface ISettingsRepository {
    // The Single Source of Truth
    val settings: Flow<SettingsModel>

    // Setters can remain granular for individual updates
    suspend fun updateQuitDate(date: Long): Result<Unit>
    suspend fun updateQuitTime(time: String): Result<Unit>
    suspend fun updateDailyCigaretteCount(numberOfCigarettes: Int): Result<Unit>
    suspend fun updateMinutesPerCigarette(minutesPerCigarette: Int): Result<Unit>
    suspend fun updateCigaretteCost(cost: Double): Result<Unit>
}