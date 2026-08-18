package org.project.quitsmoking.features.overview.data.repository

import kotlinx.coroutines.flow.Flow
import org.project.quitsmoking.features.settings.data.model.SettingsModel

interface IOverviewRepository {
    val statistics: Flow<SettingsModel>
}