package org.project.quitsmoking.common.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.project.quitsmoking.common.datasource.IQuitSmokingDiskDataSource
import org.project.quitsmoking.common.model.QuitSmokingSettingsModel

class QuitSmokingRepository(private val diskDataSource: IQuitSmokingDiskDataSource) :
    IQuitSmokingRepository {

    override val settings: Flow<QuitSmokingSettingsModel> = combine(
        diskDataSource.getQuitDate(),
        diskDataSource.getQuitTime(),
        diskDataSource.getDailyCigaretteCount(),
        diskDataSource.getMinutesPerCigarette(),
        diskDataSource.getCostPerCigarette()
    ) { date, time, count, minutes, cost ->
        QuitSmokingSettingsModel(
            quitTimestamp = date,
            quitTime = time,
            dailyCigaretteCount = count,
            minutesPerCigarette = minutes,
            costPerCigarette = cost
        )
    }

    override suspend fun updateQuitDate(date: Long): Result<Unit> =
        diskDataSource.setQuitDate(date)

    override suspend fun updateQuitTime(time: String): Result<Unit> =
        diskDataSource.setQuitTime(time)

    override suspend fun updateDailyCigaretteCount(numberOfCigarettes: Int): Result<Unit> =
        diskDataSource.setDailyCigaretteCount(numberOfCigarettes)

    override suspend fun updateMinutesPerCigarette(minutesPerCigarette: Int): Result<Unit> =
        diskDataSource.setMinutesPerCigarette(minutesPerCigarette)

    override suspend fun updateCigaretteCost(cost: Double): Result<Unit> =
        diskDataSource.setCigaretteCost(cost)
}