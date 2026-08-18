package org.project.quitsmoking.features.overview.data.datasource

import kotlinx.coroutines.flow.Flow

interface IOverViewDiskDataSource {
    fun getQuitDate(): Flow<Long>
    fun getQuitTime(): Flow<String>
    fun getDailyCigaretteCount(): Flow<Int>
    fun getMinutesPerCigarette(): Flow<Int>
    fun getCostPerCigarette(): Flow<Double>
}