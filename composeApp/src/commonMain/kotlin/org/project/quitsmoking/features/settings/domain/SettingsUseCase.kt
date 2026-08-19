package org.project.quitsmoking.features.settings.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.project.quitsmoking.features.settings.data.model.SettingsModel
import org.project.quitsmoking.features.settings.data.repository.ISettingsRepository

class SettingsUseCase(private val repository: ISettingsRepository) : ISettingsUseCase {
    override fun getSettings(): Flow<SettingsModel> = repository.settings

    override suspend fun setDate(date: Long): Result<Unit> =
        repository.updateQuitDate(date)

    override suspend fun setTime(time: String): Result<Unit> =
        repository.updateQuitTime(time)

    override suspend fun setNumberOfCigarettes(numberOfCigarettes: Int): Result<Unit> =
        repository.updateDailyCigaretteCount(numberOfCigarettes)

    override suspend fun seMinutesPerCigarette(minutesPerCigarette: Int): Result<Unit> =
        repository.updateMinutesPerCigarette(minutesPerCigarette)

    override suspend fun setCigaretteCost(cost: Double): Result<Unit> =
        repository.updateCigaretteCost(cost)

    override suspend fun consumeFirstRun(): Boolean {
        val isFirstRun = repository.isFirstRun().first()
        if (!isFirstRun) return false
        return repository.setFirstRun(false).isSuccess
    }
}