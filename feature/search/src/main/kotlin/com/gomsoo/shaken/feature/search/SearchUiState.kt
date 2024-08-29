package com.gomsoo.shaken.feature.search

import com.gomsoo.shaken.core.model.data.CocktailWithFavorite
import com.gomsoo.shaken.core.model.data.SimpleCocktail

sealed class SearchUiState(open val keyword: String) {

    data object Initial : SearchUiState("")

    data class Success(
        val cocktails: List<Item>,
        override val keyword: String,
        val detail: CocktailWithFavorite?
    ) : SearchUiState(keyword)

    data class Item(
        val cocktail: SimpleCocktail,
        val isFavorite: Boolean,
        val keyword: String
    )

    data class Empty(override val keyword: String) : SearchUiState(keyword)
}
