package com.gomsoo.shaken.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.gomsoo.shaken.core.designsystem.component.AsyncImage
import com.gomsoo.shaken.core.designsystem.component.FavoriteButton
import com.gomsoo.shaken.core.designsystem.theme.Gray800
import com.gomsoo.shaken.core.model.data.SimpleCocktail

@Composable
fun CocktailItem(
    cocktail: SimpleCocktail,
    keyword: String = "",
    onItemClick: () -> Unit,
    isFavorite: Boolean?,
    onFavoriteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            imageUrl = cocktail.thumbnailUrl,
            contentDescription = cocktail.name,
            modifier = Modifier
                .height(48.dp)
                .width(48.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (keyword.isBlank()) {
                Text(text = cocktail.name, style = MaterialTheme.typography.labelLarge)
            } else {
                Text(
                    text = buildAnnotatedString {
                        val split = cocktail.name.split(keyword, ignoreCase = true)
                        append(split[0])
                        withStyle(SpanStyle(color = Color.Red)) {
                            append(keyword)
                        }
                        split.getOrNull(1)?.let(::append)
                    },
                    style = MaterialTheme.typography.labelLarge
                )
            }
            cocktail.category?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray800,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
        isFavorite?.let { FavoriteButton(isSelected = it, onClick = onFavoriteClick) }
    }
}
