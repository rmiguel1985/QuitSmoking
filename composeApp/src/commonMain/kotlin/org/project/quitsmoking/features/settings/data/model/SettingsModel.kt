package org.project.quitsmoking.features.settings.data.model

data class SettingsModel(
    val quitTimestamp: Long,
    val quitTime: String,
    val dailyCigaretteCount: Int,
    val minutesPerCigarette: Int,
    val costPerCigarette: Double
)
