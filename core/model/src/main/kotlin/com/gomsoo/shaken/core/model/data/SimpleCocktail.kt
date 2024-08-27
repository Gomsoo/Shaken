package com.gomsoo.shaken.core.model.data

data class SimpleCocktail(
    val id: String,
    val name: String,
    val thumbnailUrl: String?,
    val category: String?
)

data class SimpleCocktailWithFavorite(val cocktail: SimpleCocktail, val isFavorite: Boolean)
