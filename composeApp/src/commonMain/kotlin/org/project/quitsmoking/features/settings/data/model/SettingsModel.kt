package org.project.quitsmoking.features.settings.data.model

data class SettingsModel(
    val quitTimestamp: Long,
    val quitTime: String,
    val dailyCigaretteCount: Int,
    val minutesPerCigarette: Int,
    val costPerCigarette: Double
) {
    val isConfigured: Boolean
        get() = quitTimestamp != 0L &&
            quitTime.isNotBlank() &&
            dailyCigaretteCount > 0 &&
            minutesPerCigarette > 0 &&
            costPerCigarette > 0.0
}
