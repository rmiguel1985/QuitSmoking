package org.project.quitsmoking.features.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.project.quitsmoking.features.settings.data.model.SettingsModel
import org.project.quitsmoking.features.settings.domain.ISettingsUseCase
import org.project.quitsmoking.utils.AppLogger

class SettingsViewModel(private val settingsUseCase: ISettingsUseCase) : ViewModel() {

    private val _uiStateSettings = MutableStateFlow(UiStateSettings())
    val uiStateSettings: StateFlow<UiStateSettings> = _uiStateSettings

    fun setCosts(cost: Double) {
        viewModelScope.launch {
            _uiStateSettings.value = UiStateSettings(isLoading = true)
            settingsUseCase.setCigaretteCost(cost)
                .onSuccess {
                    _uiStateSettings.update { UiStateSettings(successMessage = SuccessMessage.SUCCESS_SAVED_COSTS) }
                }
                .onFailure { throwable ->
                    _uiStateSettings.update { UiStateSettings(errorMessage = ErrorMessage.ERROR_SAVING_COSTS) }
                }
        }
    }

    fun setNumberOfCigarettes(cigs: Int) {
        viewModelScope.launch {
            _uiStateSettings.value = UiStateSettings(isLoading = true)
            settingsUseCase.setNumberOfCigarettes(cigs)
                .onSuccess {
                    _uiStateSettings.update { UiStateSettings(successMessage = SuccessMessage.SUCCESS_SAVED_CIGARETTES) }
                    AppLogger.d { "Time saved successfully" }
                }
                .onFailure { throwable ->
                    _uiStateSettings.update { UiStateSettings(errorMessage = ErrorMessage.ERROR_SAVING_CIGARETTES) }
                }
        }
    }

    fun setCigarettesPerMinute(minutes: Int) {
        viewModelScope.launch {
            _uiStateSettings.value = UiStateSettings(isLoading = true)
            settingsUseCase.seMinutesPerCigarette(minutes)
                .onSuccess {
                    _uiStateSettings.update { UiStateSettings(successMessage = SuccessMessage.SUCCESS_SAVED_CIGARETTES) }
                    AppLogger.d { "Num of cigarettes saved successfully" }
                }
                .onFailure { throwable ->
                    _uiStateSettings.update { UiStateSettings(errorMessage = ErrorMessage.ERROR_SAVING_CIGARETTES) }
                }
        }
    }

    fun setDate(date: Long) {
        viewModelScope.launch {
            _uiStateSettings.value = UiStateSettings(isLoading = true)
            settingsUseCase.setDate(date)
                .onSuccess {
                    _uiStateSettings.update { UiStateSettings(successMessage = SuccessMessage.SUCCESS_SAVED_DATE) }
                    AppLogger.d { "Date saved successfully" }
                }
                .onFailure { throwable ->
                    _uiStateSettings.update { UiStateSettings(errorMessage = ErrorMessage.ERROR_SAVING_DATE) }
                }
        }
    }

    fun setTime(time: String) {
        viewModelScope.launch {
            _uiStateSettings.value = UiStateSettings(isLoading = true)
            settingsUseCase.setTime(time)
                .onSuccess {
                    _uiStateSettings.update { UiStateSettings(successMessage = SuccessMessage.SUCCESS_SAVED_TIME) }
                    AppLogger.d { "Time saved successfully" }
                }
                .onFailure { throwable ->
                    _uiStateSettings.update { UiStateSettings(errorMessage = ErrorMessage.ERROR_SAVING_TIME) }
                    AppLogger.e { "Error saving Time" }
                }
        }
    }

    fun clearUiStateSettings() {
        _uiStateSettings.value = UiStateSettings()
    }

    val settings = settingsUseCase.getSettings().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsModel(
            quitTimestamp = 0,
            quitTime = "",
            costPerCigarette = 0.0,
            dailyCigaretteCount = 0,
            minutesPerCigarette = 0,
        )
    )
}


