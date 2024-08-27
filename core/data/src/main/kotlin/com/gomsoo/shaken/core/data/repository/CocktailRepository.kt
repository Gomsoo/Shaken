package com.gomsoo.shaken.core.data.repository

import com.gomsoo.shaken.core.model.data.Cocktail
import com.gomsoo.shaken.core.model.data.SimpleCocktail
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {

    suspend fun getAlcoholics(): List<SimpleCocktail>

    suspend fun search(keyword: String): List<SimpleCocktail>

    suspend fun searchStartWith(keyword: String): List<SimpleCocktail>

    suspend fun getDetail(id: String): Cocktail?

    fun getFavoriteCocktailIds(): Flow<Set<String>>

    suspend fun setFavorite(cocktailId: String, isFavorite: Boolean)
}
