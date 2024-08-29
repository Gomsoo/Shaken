package com.gomsoo.shaken.core.data.repository

import com.gomsoo.shaken.core.model.data.Cocktail
import com.gomsoo.shaken.core.model.data.CocktailWithFavorite
import com.gomsoo.shaken.core.model.data.SimpleCocktail
import com.gomsoo.shaken.core.model.data.SimpleCocktailWithFavorite
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {

    suspend fun getAlcoholics(): List<SimpleCocktail>

    suspend fun search(keyword: String): List<SimpleCocktail>

    suspend fun searchStartWith(keyword: String): List<SimpleCocktail>

    suspend fun getDetail(id: String): Cocktail?

    fun getFavoriteCocktailIds(): Flow<Set<String>>

    /**
     * 넘겨받은 아이템의 favorite 여부를 토글시켜줌. 파라미터로 넘겨받은 아이템의 `item.isFavorite`과 반대로 저장
     */
    suspend fun setFavorite(item: SimpleCocktailWithFavorite)

    /**
     * 넘겨받은 아이템의 favorite 여부를 토글시켜줌. 파라미터로 넘겨받은 아이템의 `item.isFavorite`과 반대로 저장
     */
    suspend fun setFavorite(item: CocktailWithFavorite)

    fun getFavoriteCocktails(): Flow<List<SimpleCocktail>>
}
