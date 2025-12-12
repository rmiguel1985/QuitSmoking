package org.project.quitsmoking.features.health.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.project.quitsmoking.features.health.domain.IHealthUseCase
import org.project.quitsmoking.features.health.domain.model.HealthModel

class HealthViewModel(private val useCase: IHealthUseCase): ViewModel() {
    val healthStats: StateFlow<HealthModel> = useCase.getHealthStatistics().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HealthModel()
    )
}