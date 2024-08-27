package com.gomsoo.shaken.core.database.di

import com.gomsoo.shaken.core.database.ShakenDatabase
import com.gomsoo.shaken.core.database.dao.CocktailDao
import com.gomsoo.shaken.core.database.dao.FavoriteCocktailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun providesCocktailDao(database: ShakenDatabase): CocktailDao = database.cocktailDao()

    @Provides
    fun providesFavoriteCocktailDao(database: ShakenDatabase): FavoriteCocktailDao =
        database.favoriteCocktailDao()
}
