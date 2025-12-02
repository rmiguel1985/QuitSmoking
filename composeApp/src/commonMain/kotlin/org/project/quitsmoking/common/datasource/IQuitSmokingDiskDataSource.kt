package org.project.quitsmoking.common.datasource

import kotlinx.coroutines.flow.Flow

interface IQuitSmokingDiskDataSource {
    suspend fun setQuitDate(date: Long): Result<Unit>
    suspend fun setQuitTime(time: String): Result<Unit>
    suspend fun setDailyCigaretteCount(numberOfCigarettes: Int): Result<Unit>
    suspend fun setMinutesPerCigarette(minutesPerCigarette: Int): Result<Unit>
    suspend fun setCigaretteCost(cost: Double): Result<Unit>
    fun getQuitDate(): Flow<Long>
    fun getQuitTime(): Flow<String>
    fun getDailyCigaretteCount(): Flow<Int>
    fun getMinutesPerCigarette(): Flow<Int>
    fun getCostPerCigarette(): Flow<Double>
}