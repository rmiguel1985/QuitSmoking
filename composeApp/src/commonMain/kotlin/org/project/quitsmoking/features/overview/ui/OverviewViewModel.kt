package org.project.quitsmoking.features.overview.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.project.quitsmoking.features.overview.domain.IOverviewUseCase
import org.project.quitsmoking.features.overview.domain.entities.OverviewModel


class OverviewViewModel(private val overviewUseCase: IOverviewUseCase) : ViewModel() {

    val statistic: StateFlow<OverviewModel> = overviewUseCase.getStatistics().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OverviewModel()
    )
}
