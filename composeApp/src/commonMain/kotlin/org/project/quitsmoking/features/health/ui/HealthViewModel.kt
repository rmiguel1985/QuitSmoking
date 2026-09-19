package org.project.quitsmoking.features.health.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import org.project.quitsmoking.features.health.domain.IHealthUseCase
import org.project.quitsmoking.features.health.domain.model.HealthModel

class HealthViewModel(private val useCase: IHealthUseCase): ViewModel() {

    private val _refreshTrigger = MutableStateFlow(0)

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val healthStats: StateFlow<HealthModel> = _refreshTrigger
        .flatMapLatest {
            useCase.getHealthStatistics()
                .onEach { _isRefreshing.value = false }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HealthModel()
        )

    fun refresh() {
        _isRefreshing.value = true
        _refreshTrigger.value++
    }
}