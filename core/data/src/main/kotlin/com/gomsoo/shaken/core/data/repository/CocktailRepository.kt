package com.gomsoo.shaken.core.data.repository

import com.gomsoo.shaken.core.model.data.Cocktail

interface CocktailRepository {
    suspend fun search(keyword: String): List<Cocktail>
}
