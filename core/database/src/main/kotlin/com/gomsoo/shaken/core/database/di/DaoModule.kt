package com.gomsoo.shaken.core.database.di

import com.gomsoo.shaken.core.database.ShakenDatabase
import com.gomsoo.shaken.core.database.dao.FavoriteCocktailDao
import com.gomsoo.shaken.core.database.dao.SimpleCocktailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun providesSimpleCocktailDao(
        database: ShakenDatabase
    ): SimpleCocktailDao = database.simpleCocktailDao()

    @Provides
    fun providesFavoriteCocktailDao(database: ShakenDatabase): FavoriteCocktailDao =
        database.favoriteCocktailDao()
}
