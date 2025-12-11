package org.project.quitsmoking.features.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.SmokingRooms
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionContext
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.LocalResourceReader
import org.project.quitsmoking.ui.components.TimePicker
import org.project.quitsmoking.utils.AppLogger
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.project.quitsmoking.ui.components.CostsInputDialog
import org.project.quitsmoking.ui.components.DatePicker
import org.project.quitsmoking.ui.components.SmokingHabitsInputDialog
import org.project.quitsmoking.ui.theme.orangeAccent
import org.project.quitsmoking.ui.theme.padding_12
import org.project.quitsmoking.ui.theme.padding_16
import org.project.quitsmoking.ui.theme.padding_24
import org.project.quitsmoking.ui.theme.padding_4
import org.project.quitsmoking.utils.toLocalDate
import quitsmoking.composeapp.generated.resources.Res
import quitsmoking.composeapp.generated.resources.settings_costs_description
import quitsmoking.composeapp.generated.resources.settings_costs_title
import quitsmoking.composeapp.generated.resources.settings_date_description
import quitsmoking.composeapp.generated.resources.settings_date_title
import quitsmoking.composeapp.generated.resources.settings_error_saving_cigarettes
import quitsmoking.composeapp.generated.resources.settings_error_saving_costs
import quitsmoking.composeapp.generated.resources.settings_error_saving_date
import quitsmoking.composeapp.generated.resources.settings_error_saving_time
import quitsmoking.composeapp.generated.resources.settings_smoke_description
import quitsmoking.composeapp.generated.resources.settings_smoke_title
import quitsmoking.composeapp.generated.resources.settings_time_description
import quitsmoking.composeapp.generated.resources.settings_time_title
import quitsmoking.composeapp.generated.resources.settings_title_text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val settingsViewModel = koinViewModel<SettingsViewModel>()
    val settings by settingsViewModel.settings.collectAsStateWithLifecycle()
    val uiStateSettings by settingsViewModel.uiStateSettings.collectAsStateWithLifecycle()

    var showDateDialog by remember { mutableStateOf(false) }
    var showTimeDialog by remember { mutableStateOf(false) }
    var showCigaretteDialog by remember { mutableStateOf(false) }
    var showCostsDialog by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    val currentErrorMessage = uiStateSettings.errorMessage
    val errorMessageText = currentErrorMessage?.getErrorMessage()

    val currentSuccessMessage = uiStateSettings.successMessage
    val successMessageText = currentSuccessMessage?.getSuccessMessage()

    LaunchedEffect(currentErrorMessage) {
        if (currentErrorMessage != null && errorMessageText != null) {
            settingsViewModel.clearUiStateSettings()
            snackBarHostState.showSnackbar(errorMessageText)
        }
    }

    LaunchedEffect(currentSuccessMessage) {
        if (currentSuccessMessage != null && successMessageText != null) {
            settingsViewModel.clearUiStateSettings()
            snackBarHostState.showSnackbar(successMessageText)
        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            settingsViewModel.clearUiStateSettings()
        }
    }

    val statisticsSettings = listOf(
        SettingItem(
            Icons.Default.Event,
            stringResource(Res.string.settings_date_title),
            stringResource(Res.string.settings_date_description),
            onClick = {
                showDateDialog = true
            },
            testTag = "setting"
        ),
        SettingItem(
            Icons.Default.Timer,
            stringResource(Res.string.settings_time_title),
            stringResource(Res.string.settings_time_description),
            onClick = {
                showTimeDialog = true
            }
        ),
        SettingItem(
            Icons.Default.SmokingRooms,
            stringResource(Res.string.settings_smoke_title),
            stringResource(Res.string.settings_smoke_description),
            onClick = {
                showCigaretteDialog = true
            }
        ),
        SettingItem(
            Icons.Default.AccountBalanceWallet,
            stringResource(Res.string.settings_costs_title),
            stringResource(Res.string.settings_costs_description),
            onClick = {
                showCostsDialog = true
            }
        )
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { data ->
                    val backgroundColor = when {
                        uiStateSettings.errorMessage != null -> MaterialTheme.colorScheme.error
                        uiStateSettings.successMessage != null -> MaterialTheme.colorScheme.onSecondary
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }

                    Snackbar(
                        snackbarData = data,
                        containerColor = backgroundColor,
                        contentColor = MaterialTheme.colorScheme.tertiary
                    )
                }
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(Res.string.settings_title_text),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                items(statisticsSettings) { setting ->
                    SettingsItem(setting, setting.onClick)
                }
            }
            Spacer(modifier = Modifier.padding(vertical = padding_4))
            if (showDateDialog) {
                DatePicker(
                    initialDate = settings.quitTimestamp,
                    onDismiss = { showDateDialog = false }
                ) { date ->
                    settingsViewModel.setDate(date)
                    AppLogger.d { "Selected date: ${date.toLocalDate()}" }
                }
            }
            if (showTimeDialog) {
                TimePicker(
                    initialTime = settings.quitTime.ifBlank {
                        "00:00"
                    },
                    onDismiss = { showTimeDialog = false }
                ) { time ->
                    settingsViewModel.setTime(time)
                    AppLogger.d { "Selected time: $time" }
                }
            }
            if (showCigaretteDialog) {
                SmokingHabitsInputDialog(
                    initialCigarettesPerDay = settings.dailyCigaretteCount.toString(),
                    initialMinutesPerCigarette = settings.minutesPerCigarette.toString(),
                    onDismissRequest = { showCigaretteDialog = false },
                    onSave = { cigs, minutes ->
                        showCigaretteDialog = false
                        AppLogger.i { "Saved: $cigs cig/day, $minutes min/cig" }
                        settingsViewModel.setNumberOfCigarettes(cigs)
                        settingsViewModel.setCigarettesPerMinute(minutes)
                    }
                )
            }
            if (showCostsDialog) {
                CostsInputDialog(
                    initialCost = settings.costPerCigarette.toString(),
                    onDismissRequest = { showCostsDialog = false },
                    onSave = { cost ->
                        showCostsDialog = false
                        settingsViewModel.setCosts(cost)
                    }
                )
            }
        }
    }
}

@Composable
fun ErrorMessage.getErrorMessage(): String = when (this) {
    ErrorMessage.ERROR_SAVING_COSTS -> stringResource(Res.string.settings_error_saving_costs)
    ErrorMessage.ERROR_SAVING_CIGARETTES -> stringResource(Res.string.settings_error_saving_cigarettes)
    ErrorMessage.ERROR_SAVING_TIME -> stringResource(Res.string.settings_error_saving_time)
    ErrorMessage.ERROR_SAVING_DATE -> stringResource(Res.string.settings_error_saving_date)
}

@Composable
fun SuccessMessage.getSuccessMessage(): String = when (this) {
    SuccessMessage.SUCCESS_SAVED_COSTS -> "Costs saved successfully"
    SuccessMessage.SUCCESS_SAVED_CIGARETTES -> "Cigarettes saved successfully"
    SuccessMessage.SUCCESS_SAVED_TIME -> "Time saved successfully"
    SuccessMessage.SUCCESS_SAVED_DATE -> "Date saved successfully"
}

@Composable
fun SettingsItem(setting: SettingItem, settingClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                settingClick()
            }
            .padding(horizontal = padding_16, vertical = padding_12)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = setting.icon,
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = null,
                modifier = Modifier.size(padding_24)
            )
            Spacer(modifier = Modifier.width(padding_16))
            Column {
                Text(text = setting.title, fontWeight = FontWeight.SemiBold)
                Text(text = setting.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = padding_16),
        color = MaterialTheme.colorScheme.orangeAccent
    )
}

// Data class
data class SettingItem(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val testTag: String = "",
    val onClick: () -> Unit = {},
)