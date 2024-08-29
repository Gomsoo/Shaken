package com.gomsoo.shaken.feature.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gomsoo.shaken.core.designsystem.theme.ShakenTheme
import com.gomsoo.shaken.core.model.data.SimpleCocktail
import com.gomsoo.shaken.core.ui.CocktailItem

@Composable
fun FavoriteRoute(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
    onItemClick: (String) -> Unit
) {
    val favoriteUiState by viewModel.favoriteUiState.collectAsStateWithLifecycle()
    FavoriteScreen(
        favoriteUiState = favoriteUiState,
        modifier = modifier,
        onItemClick = onItemClick
    )
}

@Composable
internal fun FavoriteScreen(
    favoriteUiState: FavoriteUiState,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit
) {
    Box(modifier = modifier) {
        when (favoriteUiState) {
            is FavoriteUiState.Initial -> InitialState(modifier)
            is FavoriteUiState.Empty -> EmptyState(modifier)
            is FavoriteUiState.Success -> FavoriteCocktails(
                cocktails = favoriteUiState.cocktails,
                modifier = modifier,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun InitialState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.feature_favorite_initial_emoji))
        Text(text = stringResource(R.string.feature_favorite_initial_message))
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
        Text(text = stringResource(R.string.feature_favorite_empty_emoji))
        Text(text = stringResource(R.string.feature_favorite_empty_message))
    }
}

@Composable
private fun FavoriteCocktails(
    cocktails: List<SimpleCocktail>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn {
            items(items = cocktails, key = { item -> item.id }) { cocktail ->
                CocktailItem(
                    cocktail = cocktail,
                    onItemClick = { onItemClick(cocktail.id) },
                    isFavorite = null,
                    onFavoriteClick = {}
                )
            }
        }
    }
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
