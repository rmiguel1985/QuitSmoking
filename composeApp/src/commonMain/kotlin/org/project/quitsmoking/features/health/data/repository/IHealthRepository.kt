package org.project.quitsmoking.features.health.data.repository

import kotlinx.coroutines.flow.Flow

interface IHealthRepository {
    fun getQuitDate(): Flow<Long>
    fun getQuitTime(): Flow<String>
}