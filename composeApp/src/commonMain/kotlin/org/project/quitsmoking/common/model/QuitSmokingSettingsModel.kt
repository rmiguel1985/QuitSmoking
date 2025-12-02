package org.project.quitsmoking.common.model

data class QuitSmokingSettingsModel(
    val quitTimestamp: Long,
    val quitTime: String,
    val dailyCigaretteCount: Int,
    val minutesPerCigarette: Int,
    val costPerCigarette: Double
)
