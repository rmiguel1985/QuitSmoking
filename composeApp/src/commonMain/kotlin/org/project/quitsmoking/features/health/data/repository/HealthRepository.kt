package org.project.quitsmoking.features.health.data.repository

import kotlinx.coroutines.flow.Flow
import org.project.quitsmoking.features.health.data.datasource.IHealthDataSource

class HealthRepository(private val diskDataSource: IHealthDataSource): IHealthRepository {
    override fun getQuitDate(): Flow<Long> =
        diskDataSource.getQuitDate()

    override fun getQuitTime(): Flow<String> =
        diskDataSource.getQuitTime()
}