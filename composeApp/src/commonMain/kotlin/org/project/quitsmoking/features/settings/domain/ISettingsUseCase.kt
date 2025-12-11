package org.project.quitsmoking.features.settings.domain

import kotlinx.coroutines.flow.Flow
import org.project.quitsmoking.features.settings.data.model.SettingsModel

interface ISettingsUseCase {
    fun getSettings(): Flow<SettingsModel>
    suspend fun setDate(date: Long): Result<Unit>
    suspend fun setTime(time: String): Result<Unit>
    suspend fun setNumberOfCigarettes(numberOfCigarettes: Int): Result<Unit>
    suspend fun seMinutesPerCigarette(minutesPerCigarette: Int): Result<Unit>
    suspend fun setCigaretteCost(cost: Double): Result<Unit>
}