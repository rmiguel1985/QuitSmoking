package org.project.quitsmoking.features.overview.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SmokeFree
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.project.quitsmoking.ui.theme.orangeAccent
import org.project.quitsmoking.ui.theme.padding_24
import org.project.quitsmoking.ui.theme.padding_32
import org.project.quitsmoking.ui.theme.padding_4
import org.project.quitsmoking.ui.theme.padding_8
import org.project.quitsmoking.utils.CurrencyFormatter
import quitsmoking.composeapp.generated.resources.Res
import quitsmoking.composeapp.generated.resources.overview_not_smoked_since_text
import quitsmoking.composeapp.generated.resources.overview_not_smoked_since_value
import quitsmoking.composeapp.generated.resources.overview_stop_smoking_text
import quitsmoking.composeapp.generated.resources.overview_title_saved
import quitsmoking.composeapp.generated.resources.overview_title_text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen() {
    val highlightColor = MaterialTheme.colorScheme.orangeAccent
    val overviewViewModel = koinViewModel<OverviewViewModel>()
    val statistic by overviewViewModel.statistic.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.overview_title_text),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Stopped smoking
            Text(
                text = stringResource(Res.string.overview_stop_smoking_text),
                style = MaterialTheme.typography.titleMedium.copy(color = highlightColor)
            )
            if (statistic.date.isNotEmpty()) {
                Text(
                    text = "${statistic.date}\n${statistic.time}",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = padding_4)
                )
            }

            Spacer(modifier = Modifier.height(padding_32))

            // Not smoked since
            Text(
                text = stringResource(Res.string.overview_not_smoked_since_text),
                style = MaterialTheme.typography.titleMedium.copy(color = highlightColor)
            )
            Text(
                text = stringResource(
                    Res.string.overview_not_smoked_since_value,
                    statistic.notSmokedSinceYears,
                    statistic.notSmokedSinceMonths,
                    statistic.notSmokedSinceDays,
                    statistic.notSmokedSinceHours,
                    statistic.notSmokedSinceMinutes,
                ),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = padding_4)
            )

            Spacer(modifier = Modifier.height(padding_32))

            // Saved section
            Text(
                text = stringResource(Res.string.overview_title_saved),
                style = MaterialTheme.typography.titleSmall.copy(color = highlightColor)
            )

            Spacer(modifier = Modifier.height(padding_8))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SavedItem(
                    icon = Icons.Default.SmokeFree,
                    value = statistic.savedCigarettes.toString()
                )
                SavedItem(
                    icon = Icons.Default.Wallet,
                    value = CurrencyFormatter().format(
                        amount = statistic.savedMoney,
                        withCurrencySymbol = true
                    )
                )
                SavedItem(icon = Icons.Default.Timer, value = "${statistic.savedTime} h")
            }
        }
    }
}

@Composable
fun SavedItem(icon: ImageVector, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(padding_24)
        )
        Text(text = value)
    }
}
