package org.project.quitsmoking.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import org.project.quitsmoking.ui.theme.blueLink
import org.project.quitsmoking.ui.theme.padding_16

@Composable
fun ClickableLink(
    url: String,
    displayText: String,
    modifier: Modifier = Modifier
) {
    val annotatedString = buildAnnotatedString {
        withLink(
            LinkAnnotation.Url(
                url = url,
                styles = TextLinkStyles(style = SpanStyle(color = MaterialTheme.colorScheme.blueLink))
            )
        ) {
            append(displayText)
        }
    }

    Text(
        text = annotatedString,
        style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
        modifier = modifier.padding(horizontal = padding_16)
    )
}
