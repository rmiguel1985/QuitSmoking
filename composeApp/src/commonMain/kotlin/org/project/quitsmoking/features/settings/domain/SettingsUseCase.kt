package org.project.quitsmoking.features.settings.domain

import kotlinx.coroutines.flow.Flow
import org.project.quitsmoking.features.settings.data.model.SettingsModel
import org.project.quitsmoking.features.settings.data.repository.ISettingsRepository

class SettingsUseCase(private val repository: ISettingsRepository) : ISettingsUseCase {
    override fun getSettings(): Flow<SettingsModel> = repository.settings

    override suspend fun setDate(date: Long): Result<Unit> =
        repository.updateQuitDate(date).onSuccess {  }

    override suspend fun setTime(time: String): Result<Unit> =
        repository.updateQuitTime(time)

    override suspend fun setNumberOfCigarettes(numberOfCigarettes: Int): Result<Unit> =
        repository.updateDailyCigaretteCount(numberOfCigarettes)

    override suspend fun seMinutesPerCigarette(minutesPerCigarette: Int): Result<Unit> =
        repository.updateMinutesPerCigarette(minutesPerCigarette)

    override suspend fun setCigaretteCost(cost: Double): Result<Unit> =
        repository.updateCigaretteCost(cost)
}