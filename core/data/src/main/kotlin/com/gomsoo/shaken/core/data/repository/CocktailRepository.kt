package com.gomsoo.shaken.core.data.repository

import com.gomsoo.shaken.core.model.data.Cocktail
import com.gomsoo.shaken.core.model.data.SimpleCocktail

interface CocktailRepository {

    suspend fun search(keyword: String): List<SimpleCocktail>

    suspend fun getDetail(id: String): Cocktail?
}
