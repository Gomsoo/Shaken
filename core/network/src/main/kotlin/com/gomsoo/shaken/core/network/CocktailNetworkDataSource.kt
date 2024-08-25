package com.gomsoo.shaken.core.network

import com.gomsoo.shaken.core.network.model.NetworkCocktail

interface CocktailNetworkDataSource {
    suspend fun search(keyword: String): List<NetworkCocktail>
}
