package com.gomsoo.shaken.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

/**
 * TODO placeholder
 */
@Composable
fun AsyncImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        coil.compose.AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = contentScale
        )
    }
}
