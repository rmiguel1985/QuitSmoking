package org.project.quitsmoking.features.settings.ui

data class UiStateSettings(
    val isLoading: Boolean = false,
    val errorMessage: ErrorMessage? = null,
    val successMessage: SuccessMessage? = null
)

enum class ErrorMessage {
    ERROR_SAVING_COSTS,
    ERROR_SAVING_CIGARETTES,
    ERROR_SAVING_TIME,
    ERROR_SAVING_DATE,
}

enum class SuccessMessage {
    SUCCESS_SAVED_COSTS,
    SUCCESS_SAVED_CIGARETTES,
    SUCCESS_SAVED_TIME,
    SUCCESS_SAVED_DATE,
}