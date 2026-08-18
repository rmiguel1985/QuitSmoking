package org.project.quitsmoking.features.overview.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.project.quitsmoking.features.overview.data.datasource.IOverViewDiskDataSource
import org.project.quitsmoking.features.settings.data.model.SettingsModel

class OverviewRepository(private val diskDataSource: IOverViewDiskDataSource): IOverviewRepository {
    override val statistics: Flow<SettingsModel> = combine(
        diskDataSource.getQuitDate(),
        diskDataSource.getQuitTime(),
        diskDataSource.getDailyCigaretteCount(),
        diskDataSource.getMinutesPerCigarette(),
        diskDataSource.getCostPerCigarette()
    ) { date, time, count, minutes, cost ->
        SettingsModel(
            quitTimestamp = date,
            quitTime = time,
            dailyCigaretteCount = count,
            minutesPerCigarette = minutes,
            costPerCigarette = cost
        )
    }
}