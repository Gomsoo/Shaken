package com.gomsoo.shaken.core.data.repository

import com.gomsoo.shaken.core.model.data.Cocktail
import com.gomsoo.shaken.core.network.CocktailNetworkDataSource
import com.gomsoo.shaken.core.network.Dispatcher
import com.gomsoo.shaken.core.network.ShakenDispatchers
import com.gomsoo.shaken.core.network.model.NetworkCocktail
import com.gomsoo.shaken.core.network.model.asModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultCocktailRepository @Inject constructor(
    private val network: CocktailNetworkDataSource,
    @Dispatcher(ShakenDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : CocktailRepository {

    override suspend fun search(keyword: String): List<Cocktail> = withContext(ioDispatcher) {
        network.search(keyword).map(NetworkCocktail::asModel)
    }
}
