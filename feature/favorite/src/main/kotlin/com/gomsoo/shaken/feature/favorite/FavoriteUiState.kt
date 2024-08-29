package com.gomsoo.shaken.feature.favorite

import com.gomsoo.shaken.core.model.data.SimpleCocktail

sealed interface FavoriteUiState {

    data object Initial : FavoriteUiState

    data class Success(val cocktails: List<SimpleCocktail>) : FavoriteUiState

    data object Empty : FavoriteUiState
}
