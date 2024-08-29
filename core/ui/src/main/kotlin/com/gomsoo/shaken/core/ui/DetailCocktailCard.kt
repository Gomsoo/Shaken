package com.gomsoo.shaken.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gomsoo.shaken.core.designsystem.component.AsyncImage
import com.gomsoo.shaken.core.designsystem.component.FavoriteButton
import com.gomsoo.shaken.core.model.data.Cocktail

@ExperimentalLayoutApi
@Composable
fun DetailCocktailCard(
    modifier: Modifier = Modifier,
    cocktail: Cocktail,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        ElevatedCard(modifier = modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        cocktail.name,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                    FavoriteButton(
                        isSelected = isFavorite,
                        onClick = onFavoriteClick
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val category = cocktail.category
                    val alcoholic = cocktail.alcoholic
                    if (!category.isNullOrBlank()) {
                        Text(
                            category,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF666666)
                        )
                    }
                    if (!category.isNullOrBlank() && !alcoholic.isNullOrBlank()) {
                        VerticalDivider(
                            modifier
                                .height(12.dp)
                                .padding(horizontal = 8.dp)
                        )
                    }
                    if (!alcoholic.isNullOrBlank()) {
                        Text(
                            alcoholic,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF666666)
                        )
                    }
                }

                val updatedAt = cocktail.updatedAt
                if (!updatedAt.isNullOrBlank()) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Latest Update ",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF888888)
                        )
                        Text(
                            updatedAt,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF888888)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                AsyncImage(
                    imageUrl = cocktail.thumbnailUrl,
                    contentDescription = cocktail.name,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    cocktail.tags.forEach { tag ->
                        Box(
                            modifier = modifier
                                .background(
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()

                Header("Ingredients")
                Column {
                    cocktail.ingredients.forEach {
                        Text(it, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                val glass = cocktail.glass
                if (!glass.isNullOrBlank()) {
                    Header("Glass")
                    Text(glass, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        val instructions = cocktail.instructions
        if (instructions.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedCard(modifier = modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp)
                ) {
                    Header("Instructions", false)

                    var selected by remember { mutableStateOf("EN") }
                    Row {
                        instructions.keys.forEach { key ->
                            FilterChip(
                                selected = selected == key,
                                onClick = { selected = key },
                                label = { Text(key, style = MaterialTheme.typography.labelSmall) },
                                modifier = Modifier.padding(end = 4.dp)
                            )
                        }
                    }
                    val instruction = instructions[selected] ?: ""
                    Text(instruction, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        cocktail.imageSource?.let { imageSource ->
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedCard(modifier = modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp)
                ) {
                    Header("Image Source", false)
                    Text(imageSource, style = MaterialTheme.typography.bodyMedium)
                    cocktail.imageAttribution?.let {
                        Text("Attribution: $it", style = MaterialTheme.typography.bodyMedium)
                    }
                    Text(
                        "Creative commons confirmed: ${cocktail.creativeCommonsConfirmed?.takeIf { it == "Yes" } ?: "No"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun Header(text: String, isTopPaddingVisible: Boolean = true) {
    if (isTopPaddingVisible) {
        Spacer(modifier = Modifier.height(16.dp))
    }
    Text(text, style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(4.dp))
}
