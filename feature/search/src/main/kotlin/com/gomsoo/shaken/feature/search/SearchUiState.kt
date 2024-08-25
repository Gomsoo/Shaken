package com.gomsoo.shaken.feature.search

import com.gomsoo.shaken.core.model.data.Cocktail

interface SearchUiState {
    data object Initial : SearchUiState
    data class Success(val cocktails: List<Cocktail>) : SearchUiState
    data object Empty : SearchUiState
}
