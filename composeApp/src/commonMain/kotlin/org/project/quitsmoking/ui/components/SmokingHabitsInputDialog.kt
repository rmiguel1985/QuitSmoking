package org.project.quitsmoking.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import org.jetbrains.compose.resources.stringResource
import org.project.quitsmoking.ui.theme.padding_8
import quitsmoking.composeapp.generated.resources.Res
import quitsmoking.composeapp.generated.resources.common_cancel
import quitsmoking.composeapp.generated.resources.common_save
import quitsmoking.composeapp.generated.resources.smoking_habits_dialog_cigarette_label_text
import quitsmoking.composeapp.generated.resources.smoking_habits_dialog_error_text
import quitsmoking.composeapp.generated.resources.smoking_habits_dialog_minutes_label_text
import quitsmoking.composeapp.generated.resources.smoking_habits_dialog_title_text

@Composable
fun SmokingHabitsInputDialog(
    initialCigarettesPerDay: String = "",
    initialMinutesPerCigarette: String = "",
    onDismissRequest: () -> Unit,
    onSave: (cigarettesPerDay: Int, minutesPerCigarette: Int) -> Unit
) {
    var cigarettesPerDay by remember { mutableStateOf(initialCigarettesPerDay) }
    var minutesPerCigarette by remember { mutableStateOf(initialMinutesPerCigarette) }
    var isError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                val cigs = cigarettesPerDay.toIntOrNull()
                val minutes = minutesPerCigarette.toIntOrNull()

                if (cigs != null && minutes != null && cigs >= 0 && minutes > 0) {
                    onSave(cigs, minutes)
                } else {
                    isError = true
                }
            }) {
                Text(stringResource(Res.string.common_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(Res.string.common_cancel))
            }
        },
        title = {
            Text(stringResource(Res.string.smoking_habits_dialog_title_text))
        },
        text = {
            Column {
                OutlinedTextField(
                    value = cigarettesPerDay,
                    onValueChange = {
                        cigarettesPerDay = it
                        isError = false
                    },
                    label = { Text(stringResource(Res.string.smoking_habits_dialog_cigarette_label_text)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    isError = isError,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(padding_8))

                OutlinedTextField(
                    value = minutesPerCigarette,
                    onValueChange = {
                        minutesPerCigarette = it
                        isError = false
                    },
                    label = { Text(stringResource(Res.string.smoking_habits_dialog_minutes_label_text)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    isError = isError,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (isError) {
                    Text(
                        text = stringResource(Res.string.smoking_habits_dialog_error_text),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = padding_8)
                    )
                }
            }
        },
    )
}
