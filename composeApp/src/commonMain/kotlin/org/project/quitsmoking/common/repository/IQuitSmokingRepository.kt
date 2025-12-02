package org.project.quitsmoking.common.repository

import kotlinx.coroutines.flow.Flow
import org.project.quitsmoking.common.model.QuitSmokingSettingsModel

interface IQuitSmokingRepository {
    // The Single Source of Truth
    val settings: Flow<QuitSmokingSettingsModel>

    // Setters can remain granular for individual updates
    suspend fun updateQuitDate(date: Long): Result<Unit>
    suspend fun updateQuitTime(time: String): Result<Unit>
    suspend fun updateDailyCigaretteCount(numberOfCigarettes: Int): Result<Unit>
    suspend fun updateMinutesPerCigarette(minutesPerCigarette: Int): Result<Unit>
    suspend fun updateCigaretteCost(cost: Double): Result<Unit>
}