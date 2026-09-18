package org.project.quitsmoking.features.overview.ui

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
import org.project.quitsmoking.features.overview.domain.IOverviewUseCase
import org.project.quitsmoking.features.overview.domain.entities.OverviewModel

class OverviewViewModel(private val overviewUseCase: IOverviewUseCase) : ViewModel() {

    private val _refreshTrigger = MutableStateFlow(0)

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val statistic: StateFlow<OverviewModel> = _refreshTrigger
        .flatMapLatest {
            overviewUseCase.getStatistics()
                .onEach { _isRefreshing.value = false }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = OverviewModel()
        )

    fun refresh() {
        _isRefreshing.value = true
        _refreshTrigger.value++
    }
}
