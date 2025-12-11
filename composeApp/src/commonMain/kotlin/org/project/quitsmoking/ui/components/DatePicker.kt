package org.project.quitsmoking.ui.components

import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import kotlin.time.ExperimentalTime
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import quitsmoking.composeapp.generated.resources.Res
import quitsmoking.composeapp.generated.resources.common_cancel
import quitsmoking.composeapp.generated.resources.common_ok

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun DatePicker(
    initialDate: Long? = null,
    onDismiss: () -> Unit,
    onClick: (Long) -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialDate)
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val selectedMillis = datePickerState.selectedDateMillis
                    if (selectedMillis != null) {
                        onClick(selectedMillis)
                        onDismiss()
                    }
                }
            ) {
                Text(stringResource(Res.string.common_ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(Res.string.common_cancel))
            }
        },
    ) {
        Column {
            DatePicker(
                state = datePickerState,
                modifier = Modifier
            )
        }
    }
}
