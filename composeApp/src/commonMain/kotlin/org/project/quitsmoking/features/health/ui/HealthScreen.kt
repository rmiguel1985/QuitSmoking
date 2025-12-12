package org.project.quitsmoking.features.health.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.project.quitsmoking.features.health.domain.model.HealthModel
import org.project.quitsmoking.ui.theme.orangeAccent
import org.project.quitsmoking.ui.theme.padding_16
import org.project.quitsmoking.ui.theme.padding_4
import quitsmoking.composeapp.generated.resources.Res
import quitsmoking.composeapp.generated.resources.health_blood_pressure_description
import quitsmoking.composeapp.generated.resources.health_blood_pressure_title
import quitsmoking.composeapp.generated.resources.health_breathing_description
import quitsmoking.composeapp.generated.resources.health_breathing_title
import quitsmoking.composeapp.generated.resources.health_capacity_description
import quitsmoking.composeapp.generated.resources.health_capacity_title
import quitsmoking.composeapp.generated.resources.health_carbon_monoxide_description
import quitsmoking.composeapp.generated.resources.health_carbon_monoxide_title
import quitsmoking.composeapp.generated.resources.health_coronary_arteries_description
import quitsmoking.composeapp.generated.resources.health_coronary_arteries_title
import quitsmoking.composeapp.generated.resources.health_goal_achieved
import quitsmoking.composeapp.generated.resources.health_goal_in_progress
import quitsmoking.composeapp.generated.resources.health_heart_attack_15y_description
import quitsmoking.composeapp.generated.resources.health_heart_attack_15y_title
import quitsmoking.composeapp.generated.resources.health_heart_attack_2y_description
import quitsmoking.composeapp.generated.resources.health_heart_attack_2y_title
import quitsmoking.composeapp.generated.resources.health_heart_attack_description
import quitsmoking.composeapp.generated.resources.health_heart_attack_title
import quitsmoking.composeapp.generated.resources.health_lung_cancer_description
import quitsmoking.composeapp.generated.resources.health_lung_cancer_title
import quitsmoking.composeapp.generated.resources.health_lung_hairs_description
import quitsmoking.composeapp.generated.resources.health_lung_hairs_title
import quitsmoking.composeapp.generated.resources.health_nicotine_withdrawal_description
import quitsmoking.composeapp.generated.resources.health_nicotine_withdrawal_title
import quitsmoking.composeapp.generated.resources.health_sense_of_smell_description
import quitsmoking.composeapp.generated.resources.health_sense_of_smell_title
import quitsmoking.composeapp.generated.resources.health_stroke_description
import quitsmoking.composeapp.generated.resources.health_stroke_title
import quitsmoking.composeapp.generated.resources.health_title_text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthScreen(
    healthViewModel: HealthViewModel = koinViewModel<HealthViewModel>()
) {
    val health by healthViewModel.healthStats.collectAsStateWithLifecycle()

    val healthItems = remember(health) {
        getHealthItems(health)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(Res.string.health_title_text),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(padding_16),
            verticalArrangement = Arrangement.spacedBy(padding_16)
        ) {
            items(healthItems) { item ->
                HealthItemCard(item)
            }
        }
    }
}

private fun getHealthItems(health: HealthModel): List<HealthItem> =
    listOf(
        HealthItem(
            Res.string.health_blood_pressure_title,
            Res.string.health_blood_pressure_description,
            progress = health.bloodPressure
        ),
        HealthItem(
            Res.string.health_carbon_monoxide_title,
            Res.string.health_carbon_monoxide_description,
            progress = health.carbonMonoxide
        ),
        HealthItem(
            Res.string.health_heart_attack_title,
            Res.string.health_heart_attack_description,
            progress = health.heartAttack
        ),
        HealthItem(
            Res.string.health_sense_of_smell_title,
            Res.string.health_sense_of_smell_description,
            progress = health.senseOfSmell
        ),
        HealthItem(
            Res.string.health_breathing_title,
            Res.string.health_breathing_description,
            progress = health.breathing
        ),
        HealthItem(
            Res.string.health_nicotine_withdrawal_title,
            Res.string.health_nicotine_withdrawal_description,
            progress = health.nicotineWithdrawal
        ),
        HealthItem(
            Res.string.health_capacity_title,
            Res.string.health_capacity_description,
            progress = health.capacity
        ),
        HealthItem(
            Res.string.health_lung_hairs_title,
            Res.string.health_lung_hairs_description,
            progress = health.lungHairs
        ),
        HealthItem(
            Res.string.health_coronary_arteries_title,
            Res.string.health_coronary_arteries_description,
            progress = health.coronaryArteries
        ),
        HealthItem(
            Res.string.health_heart_attack_2y_title,
            Res.string.health_heart_attack_2y_description,
            progress = health.heartAttack2Years
        ),
        HealthItem(
            Res.string.health_stroke_title,
            Res.string.health_stroke_description,
            progress = health.stroke
        ),
        HealthItem(
            Res.string.health_lung_cancer_title,
            Res.string.health_lung_cancer_description,
            progress = health.lungCancer
        ),
        HealthItem(
            Res.string.health_heart_attack_15y_title,
            Res.string.health_heart_attack_15y_description,
            progress = health.heartAttack15Years
        )
    )

@Composable
fun HealthItemCard(item: HealthItem) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(item.title),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)

        )
        Spacer(modifier = Modifier.height(padding_4))
        Text(
            text = stringResource(item.description),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(padding_4))

        ProgressDescription(item)

        Spacer(modifier = Modifier.height(padding_4))
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = { item.progress },
            color = MaterialTheme.colorScheme.orangeAccent
        )
    }
}

@Composable
private fun ProgressDescription(item: HealthItem) {
    if (item.progress == 1.0f) {
        Text(
            text = stringResource(
                Res.string.health_goal_achieved,
                (item.progress * 100).toInt()
            ),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.orangeAccent
            )
        )
    } else {
        Text(
            text = stringResource(
                Res.string.health_goal_in_progress,
                (item.progress * 100).toInt()
            ),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontStyle = FontStyle.Italic
            )
        )
    }
}

// Data class
data class HealthItem(val title: StringResource, val description: StringResource, val progress: Float = 0.6f)

@Preview(showBackground = true)
@Composable
fun HealthScreenPreview() {
    MaterialTheme {
        HealthScreen()
    }
}