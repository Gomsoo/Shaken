package com.gomsoo.shaken.feature.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gomsoo.shaken.core.designsystem.component.AsyncImage
import com.gomsoo.shaken.core.designsystem.component.FavoriteButton
import com.gomsoo.shaken.core.designsystem.icon.ShakenIcons
import com.gomsoo.shaken.core.designsystem.theme.ShakenTheme
import com.gomsoo.shaken.core.model.data.CocktailWithFavorite
import com.gomsoo.shaken.core.model.data.SimpleCocktail
import com.gomsoo.shaken.core.model.data.SimpleCocktailWithFavorite
import com.gomsoo.shaken.core.ui.DetailCocktailCardBottomSheet

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()
    SearchScreen(
        searchUiState = searchUiState,
        modifier = modifier,
        onItemClick = { _, cocktail -> viewModel.setDetailId(cocktail.id) },
        onFavoriteClick = viewModel::setFavorite,
        onDetailFavoriteClick = viewModel::setFavorite,
        onDismissRequest = { viewModel.setDetailId(null) },
        onInputTextChange = viewModel::setKeyword,
        onInputTextClear = { viewModel.setKeyword("") }
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    searchUiState: SearchUiState,
    modifier: Modifier = Modifier,
    onItemClick: (String, SimpleCocktail) -> Unit,
    onFavoriteClick: (SimpleCocktailWithFavorite) -> Unit,
    onDetailFavoriteClick: (CocktailWithFavorite) -> Unit,
    onDismissRequest: () -> Unit,
    onInputTextChange: (String) -> Unit,
    onInputTextClear: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            when (searchUiState) {
                is SearchUiState.Initial -> InitialState(modifier)
                is SearchUiState.Empty -> EmptyState(modifier)
                is SearchUiState.Success -> {
                    Cocktails(
                        cocktails = searchUiState.cocktails,
                        keyword = searchUiState.keyword,
                        modifier = modifier,
                        onItemClick = onItemClick,
                        onFavoriteClick = onFavoriteClick
                    )

                    if (searchUiState.detail != null) {
                        DetailCocktailCardBottomSheet(
                            cocktail = searchUiState.detail.cocktail,
                            isFavorite = searchUiState.detail.isFavorite,
                            onFavoriteClick = { onDetailFavoriteClick(searchUiState.detail) },
                            onDismissRequest = { onDismissRequest() })
                    }
                }
            }
        }

        SearchInput(searchUiState.keyword, onInputTextChange, onInputTextClear)
    }
}

@Composable
private fun InitialState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.feature_search_initial_emoji))
        Text(text = stringResource(R.string.feature_search_initial_message))
    }
}

/**
 * TODO Message가 Input text를 안다면 더 좋을듯
 */
@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.feature_search_empty_emoji))
        Text(text = stringResource(R.string.feature_search_empty_message))
    }
}

/**
 * TODO Change to Grid?
 */
@Composable
private fun Cocktails(
    cocktails: List<SimpleCocktailWithFavorite>,
    keyword: String,
    modifier: Modifier = Modifier,
    onItemClick: (String, SimpleCocktail) -> Unit,
    onFavoriteClick: (SimpleCocktailWithFavorite) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn {
            items(items = cocktails, key = { item -> item.cocktail.id }) { item ->
                val (cocktail, isFavorite) = item
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(cocktail.id, cocktail) }
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
                                color = Color(0xFF888888), // TODO Extract color resource
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                    FavoriteButton(isSelected = isFavorite, onClick = { onFavoriteClick(item) })
                }
            }
        }
    }
}

@Composable
fun SearchInput(text: String, onTextChange: (String) -> Unit, onClearClick: () -> Unit) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        placeholder = { Text(stringResource(R.string.feature_search_input_placeholder)) },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            AnimatedVisibility(visible = text.isNotEmpty(), enter = fadeIn(), exit = fadeOut()) {
                IconButton(onClick = onClearClick) {
                    Icon(ShakenIcons.Clear, contentDescription = null)
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun InitialStatePreview() {
    ShakenTheme {
        InitialState()
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyStatePreview() {
    ShakenTheme {
        EmptyState()
    }
}

@Preview(showBackground = true)
@Composable
private fun CocktailsPreview(
    @PreviewParameter(CocktailsPreviewParameterProvider::class)
    cocktails: List<SimpleCocktailWithFavorite>
) {
    ShakenTheme {
        Cocktails(cocktails = cocktails, "", onItemClick = { _, _ -> }, onFavoriteClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchInputPreview() {
    ShakenTheme {
        SearchInput("", {}, {})
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchInputTextPreview() {
    ShakenTheme {
        SearchInput("Marga", {}, {})
    }
}
