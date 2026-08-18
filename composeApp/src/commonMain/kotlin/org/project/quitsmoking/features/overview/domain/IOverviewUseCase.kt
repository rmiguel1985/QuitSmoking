package org.project.quitsmoking.features.overview.domain

import kotlinx.coroutines.flow.Flow
import org.project.quitsmoking.features.overview.domain.entities.OverviewModel

interface IOverviewUseCase {
    fun getStatistics(): Flow<OverviewModel>
}