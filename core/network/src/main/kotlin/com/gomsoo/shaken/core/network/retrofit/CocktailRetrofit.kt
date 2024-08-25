package com.gomsoo.shaken.core.network.retrofit

import com.gomsoo.shaken.core.network.CocktailNetworkDataSource
import com.gomsoo.shaken.core.network.model.NetworkCocktail
import kotlinx.serialization.Serializable
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
internal data class CocktailResponse(val drinks: List<NetworkCocktail>?)

private interface CocktailNetworkApi {

    @GET("search.php")
    suspend fun search(@Query("s") keyword: String): CocktailResponse

    @GET("lookup.php")
    suspend fun lookup(@Query("i") id: String): CocktailResponse
}

@Singleton
internal class CocktailRetrofit @Inject constructor(
    retrofit: Retrofit
) : CocktailNetworkDataSource {

    private val service = retrofit.create<CocktailNetworkApi>()

    override suspend fun search(keyword: String): List<NetworkCocktail> =
        service.search(keyword).drinks ?: emptyList()

    override suspend fun getCocktail(id: String): NetworkCocktail? =
        service.lookup(id).drinks?.firstOrNull()
}
