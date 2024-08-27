package com.gomsoo.shaken.feature.detail

import com.gomsoo.shaken.core.model.data.CocktailWithFavorite

sealed interface DetailUiState {

    data object Initial : DetailUiState

    data object InvalidId : DetailUiState
    data object InvalidResult : DetailUiState

    data class Success(val cocktail: CocktailWithFavorite) : DetailUiState
}
