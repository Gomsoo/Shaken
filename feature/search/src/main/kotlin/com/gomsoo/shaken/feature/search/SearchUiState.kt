package com.gomsoo.shaken.feature.search

import com.gomsoo.shaken.core.model.data.Cocktail

sealed class SearchUiState(open val keyword: String) {

    data object Initial : SearchUiState("")

    data class Success(
        val cocktails: List<Cocktail>,
        override val keyword: String
    ) : SearchUiState(keyword)

    data class Empty(override val keyword: String) : SearchUiState(keyword)
}
