package org.project.quitsmoking.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import org.project.quitsmoking.ui.theme.blueLink
import org.project.quitsmoking.ui.theme.padding_16

@Composable
fun ClickableMail(
    email: String,
    displayText: String,
    modifier: Modifier = Modifier,
    subject: String = "",
    body: String = ""
) {
    val uriHandler = LocalUriHandler.current

    Text(
        text = displayText,
        style = MaterialTheme.typography.bodyMedium.copy(
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.blueLink
        ),
        modifier = modifier
            .clickable {
                val encodedSubject = subject.replace(" ", "%20")
                val encodedBody = body.replace(" ", "%20")
                val uri = "mailto:$email?subject=$encodedSubject&body=$encodedBody"
                uriHandler.openUri(uri)
            }
            .padding(horizontal = padding_16)
    )
}
