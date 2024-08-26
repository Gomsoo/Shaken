package com.gomsoo.shaken.core.network.retrofit

import com.gomsoo.shaken.core.network.CocktailNetworkDataSource
import com.gomsoo.shaken.core.network.model.NetworkCocktail
import com.gomsoo.shaken.core.network.model.NetworkCocktailItem
import kotlinx.serialization.Serializable
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
internal data class CocktailResponse<T>(val drinks: List<T>?)

private interface CocktailNetworkApi {

    @GET("filter.php")
    suspend fun filterByAlcoholic(
        @Query("a") alcoholic: String
    ): CocktailResponse<NetworkCocktailItem>

    companion object {
        const val QUERY_ALCOHOLIC = "Alcoholic"
        const val QUERY_NON_ALCOHOLIC = "Non_Alcoholic"
    }

    @GET("search.php")
    suspend fun search(@Query("s") keyword: String): CocktailResponse<NetworkCocktail>

    @GET("search.php")
    suspend fun searchStartWith(@Query("f") keyword: String): CocktailResponse<NetworkCocktail>

    @GET("lookup.php")
    suspend fun lookup(@Query("i") id: String): CocktailResponse<NetworkCocktail>
}

@Singleton
internal class CocktailRetrofit @Inject constructor(
    retrofit: Retrofit
) : CocktailNetworkDataSource {

    private val service = retrofit.create<CocktailNetworkApi>()

    override suspend fun getAlcoholics(): List<NetworkCocktailItem> =
        service.filterByAlcoholic(CocktailNetworkApi.QUERY_ALCOHOLIC).drinks ?: emptyList()

    override suspend fun search(keyword: String): List<NetworkCocktail> =
        service.search(keyword).drinks ?: emptyList()

    /**
     * 해당 API로 시작'단어' 검색이 불가능. 시작 알파벳으로만 검색 가능
     */
    override suspend fun searchStartWith(keyword: String): List<NetworkCocktail> {
        val firstLetter = keyword.first()
        return service.searchStartWith("$firstLetter").drinks ?: emptyList()
    }

    override suspend fun getCocktail(id: String): NetworkCocktail? =
        service.lookup(id).drinks?.firstOrNull()
}
