package org.project.quitsmoking.features.health.data.datasource

import kotlinx.coroutines.flow.Flow

interface IHealthDataSource {
    fun getQuitDate(): Flow<Long>
    fun getQuitTime(): Flow<String>
}