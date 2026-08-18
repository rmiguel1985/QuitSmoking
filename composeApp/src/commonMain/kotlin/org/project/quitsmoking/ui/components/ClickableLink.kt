package org.project.quitsmoking.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import org.project.quitsmoking.ui.theme.blueLink
import org.project.quitsmoking.ui.theme.padding_16

@Composable
fun ClickableLink(
    url: String,
    displayText: String
) {
    val uriHandler = LocalUriHandler.current

    ClickableText(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.blueLink)) {
                append(displayText)
            }
            addStringAnnotation(
                tag = "URL",
                annotation = url,
                start = 0,
                end = displayText.length
            )
        },
        style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
        onClick = { offset ->
            val annotations = buildAnnotatedString {
                addStringAnnotation("URL", url, 0, displayText.length)
            }.getStringAnnotations("URL", offset, offset)
            annotations.firstOrNull()?.let {
                uriHandler.openUri(it.item)
            }
        },
        modifier = Modifier.padding(horizontal = padding_16)
    )
}
