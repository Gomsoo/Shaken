package com.gomsoo.shaken.core.data.repository

import androidx.room.withTransaction
import com.gomsoo.shaken.core.database.ShakenDatabase
import com.gomsoo.shaken.core.database.dao.FavoriteCocktailDao
import com.gomsoo.shaken.core.database.dao.SimpleCocktailDao
import com.gomsoo.shaken.core.database.model.FavoriteCocktailEntity
import com.gomsoo.shaken.core.database.model.SimpleCocktailEntity
import com.gomsoo.shaken.core.database.model.asEntity
import com.gomsoo.shaken.core.database.model.asModel
import com.gomsoo.shaken.core.model.data.Cocktail
import com.gomsoo.shaken.core.model.data.CocktailWithFavorite
import com.gomsoo.shaken.core.model.data.SimpleCocktail
import com.gomsoo.shaken.core.model.data.SimpleCocktailWithFavorite
import com.gomsoo.shaken.core.network.CocktailNetworkDataSource
import com.gomsoo.shaken.core.network.Dispatcher
import com.gomsoo.shaken.core.network.ShakenDispatchers
import com.gomsoo.shaken.core.network.model.NetworkCocktail
import com.gomsoo.shaken.core.network.model.NetworkCocktailItem
import com.gomsoo.shaken.core.network.model.asModel
import com.gomsoo.shaken.core.network.model.asSimpleModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject

internal class DefaultCocktailRepository @Inject constructor(
    private val network: CocktailNetworkDataSource,
    private val simpleCocktailDao: SimpleCocktailDao,
    private val favoriteDao: FavoriteCocktailDao,
    private val database: ShakenDatabase,
    @Dispatcher(ShakenDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : CocktailRepository {

    override suspend fun getAlcoholics(): List<SimpleCocktail> = withContext(ioDispatcher) {
        network.getAlcoholics().map(NetworkCocktailItem::asModel)
    }

    override suspend fun search(keyword: String): List<SimpleCocktail> = withContext(ioDispatcher) {
        network.search(keyword).map(NetworkCocktail::asSimpleModel)
    }

    override suspend fun searchStartWith(keyword: String): List<SimpleCocktail> =
        withContext(ioDispatcher) {
            network.searchStartWith(keyword).map(NetworkCocktail::asSimpleModel)
        }

    override suspend fun getDetail(id: String): Cocktail? = withContext(ioDispatcher) {
        network.getCocktail(id)?.asModel()
    }

    override fun getFavoriteCocktailIds(): Flow<Set<String>> = favoriteDao.getAllIds()
        .mapLatest { it.toSet() }

    override suspend fun setFavorite(cocktailId: String, toFavorite: Boolean) {
        if (toFavorite) {
            favoriteDao.insertOrIgnore(FavoriteCocktailEntity(cocktailId, Instant.now()))
        } else {
            favoriteDao.delete(cocktailId)
        }
    }

    private fun insertOrDeleteFavoriteCocktail(
        entity: SimpleCocktailEntity,
        isFavorite: Boolean
    ) {
        if (isFavorite) {
            simpleCocktailDao.upsert(entity)
        } else {
            simpleCocktailDao.delete(entity)
        }
    }

    override suspend fun setFavorite(item: SimpleCocktailWithFavorite) {
        database.withTransaction {
            setFavorite(item.cocktail.id, !item.isFavorite)
            insertOrDeleteFavoriteCocktail(item.cocktail.asEntity(), !item.isFavorite)
        }
    }

    override suspend fun setFavorite(item: CocktailWithFavorite) {
        database.withTransaction {
            setFavorite(item.cocktail.id, !item.isFavorite)
            insertOrDeleteFavoriteCocktail(item.cocktail.asEntity(), !item.isFavorite)
        }
    }

    override fun getFavoriteCocktails(): Flow<List<SimpleCocktail>> =
        simpleCocktailDao.getFavoriteCocktails().mapLatest { it.map { it.asModel() } }
}
