package com.gomsoo.shaken.core.network

import com.gomsoo.shaken.core.network.model.NetworkCocktail
import com.gomsoo.shaken.core.network.model.NetworkCocktailItem

interface CocktailNetworkDataSource {
    suspend fun getAlcoholics(): List<NetworkCocktailItem>
    suspend fun search(keyword: String): List<NetworkCocktail>
    suspend fun getCocktail(id: String): NetworkCocktail?
}
