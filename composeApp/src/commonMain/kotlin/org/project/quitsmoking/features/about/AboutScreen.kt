package org.project.quitsmoking.features.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.project.BuildKonfig
import org.project.quitsmoking.ui.components.ClickableLink
import org.project.quitsmoking.ui.components.ClickableMail
import org.project.quitsmoking.ui.theme.orangeAccent
import org.project.quitsmoking.ui.theme.padding_16
import org.project.quitsmoking.ui.theme.padding_24
import quitsmoking.composeapp.generated.resources.Res
import quitsmoking.composeapp.generated.resources.about_contact_description
import quitsmoking.composeapp.generated.resources.about_contact_title
import quitsmoking.composeapp.generated.resources.about_developer_description
import quitsmoking.composeapp.generated.resources.about_developer_title
import quitsmoking.composeapp.generated.resources.about_disclaimer_description
import quitsmoking.composeapp.generated.resources.about_disclaimer_title
import quitsmoking.composeapp.generated.resources.about_license_description
import quitsmoking.composeapp.generated.resources.about_license_link_text
import quitsmoking.composeapp.generated.resources.about_license_title
import quitsmoking.composeapp.generated.resources.about_mission_description
import quitsmoking.composeapp.generated.resources.about_mission_title
import quitsmoking.composeapp.generated.resources.about_original_description
import quitsmoking.composeapp.generated.resources.about_original_link_text
import quitsmoking.composeapp.generated.resources.about_original_title
import quitsmoking.composeapp.generated.resources.about_project_link_text
import quitsmoking.composeapp.generated.resources.about_project_title
import quitsmoking.composeapp.generated.resources.about_title_text
import quitsmoking.composeapp.generated.resources.about_version_text

private const val CONTACT_EMAIL = "support.quit.smoking@protonmail.com"
private const val PROJECT_URL = "https://quitsmoking.dev"
private const val ORIGINAL_PROJECT_URL = "https://archive.softwareheritage.org/browse/origin/directory/?origin_url=https://github.com/scoute-dich/QuitSmoking"
private const val LICENSE_URL = "https://www.gnu.org/licenses/gpl-3.0.en.html"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(Res.string.about_title_text),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Section(
                title = stringResource(Res.string.about_mission_title),
                body = stringResource(Res.string.about_mission_description)
            )

            Spacer(modifier = Modifier.height(padding_24))

            Section(
                title = stringResource(Res.string.about_disclaimer_title),
                body = stringResource(Res.string.about_disclaimer_description)
            )

            Spacer(modifier = Modifier.height(padding_24))

            Section(
                title = stringResource(Res.string.about_developer_title),
                body = stringResource(Res.string.about_developer_description)
            )

            Spacer(modifier = Modifier.height(padding_24))

            Section(
                title = stringResource(Res.string.about_contact_title),
                body = stringResource(Res.string.about_contact_description)
            )

            ClickableMail(
                email = CONTACT_EMAIL,
                displayText = CONTACT_EMAIL,
                subject = "App Feedback"
            )

            Spacer(modifier = Modifier.height(padding_24))

            Section(
                title = stringResource(Res.string.about_project_title),
                link = PROJECT_URL,
                linkText = stringResource(Res.string.about_project_link_text)
            )

            Spacer(modifier = Modifier.height(padding_24))

            Section(
                title = stringResource(Res.string.about_original_title),
                body = stringResource(Res.string.about_original_description),
                link = ORIGINAL_PROJECT_URL,
                linkText = stringResource(Res.string.about_original_link_text)
            )

            Spacer(modifier = Modifier.height(padding_24))

            Section(
                title = stringResource(Res.string.about_license_title),
                body = stringResource(Res.string.about_license_description),
                link = LICENSE_URL,
                linkText = stringResource(Res.string.about_license_link_text)
            )

            Spacer(modifier = Modifier.height(padding_24))

            AppVersionInfo()
        }
    }
}

@Composable
fun Section(
    title: String,
    body: String = "",
    linkText: String = "",
    link: String = "",
) {
    SectionTitle(title)
    if (body.isNotBlank()) BodyText(body)
    if (link.isNotBlank() && linkText.isNotBlank()) {
        ClickableLink(
            url = link,
            displayText = linkText
        )
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.orangeAccent,
            fontWeight = FontWeight.Bold
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun BodyText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = padding_16)
    )
}

@Composable
fun AppVersionInfo() {
    val version = BuildKonfig.appVersion
    Text(
        text = stringResource(Res.string.about_version_text, version),
        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.orangeAccent),
        modifier = Modifier.padding(top = padding_16, bottom = padding_16)
    )
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen()
}