package org.project.quitsmoking.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.stringResource
import org.project.quitsmoking.ui.theme.padding_16
import org.project.quitsmoking.utils.getSplitTime
import org.project.quitsmoking.utils.toFormatedTime
import quitsmoking.composeapp.generated.resources.Res
import quitsmoking.composeapp.generated.resources.common_cancel
import quitsmoking.composeapp.generated.resources.common_ok
import quitsmoking.composeapp.generated.resources.time_picker_title_text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    title: String = stringResource(Res.string.time_picker_title_text),
    initialTime: String,
    onDismiss: () -> Unit,
    onClick: (String) -> Unit,
) {
    val (initialHour, initialMinute) = initialTime.getSplitTime()
    val timePickerState = rememberTimePickerState(
        is24Hour = true,
        initialHour = initialHour,
        initialMinute = initialMinute
    )

    ShowTimePickerDialog(
        state = timePickerState,
        title = title,
        onDismiss = onDismiss,
        onConfirm = {
            onClick(timePickerState.toFormatedTime())
            onDismiss()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowTimePickerDialog(
    state: TimePickerState,
    title: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(padding_16),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(Modifier.padding(padding_16)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = padding_16)
                )

                TimePicker(
                    state = state,
                    layoutType = TimePickerLayoutType.Vertical,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(padding_16))
                Row(
                    modifier = Modifier.align(Alignment.End),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(Res.string.common_cancel))
                    }
                    Spacer(modifier = Modifier.width(padding_16))
                    TextButton(onClick = onConfirm) {
                        Text(stringResource(Res.string.common_ok))
                    }
                }
            }
        }
    }
}