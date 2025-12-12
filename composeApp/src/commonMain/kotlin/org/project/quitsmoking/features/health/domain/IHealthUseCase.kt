package org.project.quitsmoking.features.health.domain

import kotlinx.coroutines.flow.Flow
import org.project.quitsmoking.features.health.domain.model.HealthModel

interface IHealthUseCase {
    fun getHealthStatistics(): Flow<HealthModel>
}