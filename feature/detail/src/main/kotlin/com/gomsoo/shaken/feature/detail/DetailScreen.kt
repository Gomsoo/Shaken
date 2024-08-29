package com.gomsoo.shaken.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gomsoo.shaken.core.designsystem.theme.ShakenTheme
import com.gomsoo.shaken.core.model.data.CocktailWithFavorite
import com.gomsoo.shaken.core.ui.DetailCocktailCard

@Composable
fun DetailRoute(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val detailUiState by viewModel.detailUiState.collectAsStateWithLifecycle()
    DetailScreen(detailUiState, viewModel::setFavorite, modifier)
}

@Composable
internal fun DetailScreen(
    detailUiState: DetailUiState,
    onFavoriteClick: (CocktailWithFavorite) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f))
            .fillMaxSize()
    ) {
        when (detailUiState) {
            is DetailUiState.Initial -> InitialState(modifier)
            is DetailUiState.InvalidId -> EmptyState(modifier)
            is DetailUiState.InvalidResult -> EmptyState(modifier)
            is DetailUiState.Success -> CocktailDetail(
                cocktailWithFavorite = detailUiState.cocktail,
                onFavoriteClick = onFavoriteClick,
                modifier = modifier
            )
        }
    }
}

/**
 * TODO
 */
@Composable
private fun InitialState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "오쉣")
        Text(text = "시간 없어라")
    }
}

/**
 * TODO
 */
@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "OMG")
        Text(text = "The time is running out")
    }
}

/**
 * TODO
 *   - Extract string resources
 *   - Move to core:ui
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CocktailDetail(
    cocktailWithFavorite: CocktailWithFavorite,
    onFavoriteClick: (CocktailWithFavorite) -> Unit,
    modifier: Modifier = Modifier
) {
    val (cocktail, isFavorite) = cocktailWithFavorite
    DetailCocktailCard(modifier, cocktail, isFavorite) { onFavoriteClick(cocktailWithFavorite) }
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
    @PreviewParameter(CocktailPreviewParameterProvider::class)
    cocktail: CocktailWithFavorite
) {
    ShakenTheme {
        CocktailDetail(cocktail, {})
    }
}
