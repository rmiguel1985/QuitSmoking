package org.project.quitsmoking.features.overview.domain.entities

data class OverviewModel(
    val date: String = "__",
    val time: String = "__",
    val notSmokedSinceDays: String = "0",
    val notSmokedSinceYears: String = "0",
    val notSmokedSinceHours: String = "0",
    val notSmokedSinceMinutes: String = "0",
    val savedCigarettes: Int = 1,
    val savedMoney: Double = 0.0,
    val savedTime: Double = 0.0,
    val notSmokedSinceMonths: String = "0"
)
