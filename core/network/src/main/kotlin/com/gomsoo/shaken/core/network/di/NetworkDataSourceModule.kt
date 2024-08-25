package com.gomsoo.shaken.core.network.di

import com.gomsoo.shaken.core.network.CocktailNetworkDataSource
import com.gomsoo.shaken.core.network.retrofit.CocktailRetrofit
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDataSourceModule {

    @Binds
    internal abstract fun bindsCocktailRepository(
        cocktailRetrofit: CocktailRetrofit
    ): CocktailNetworkDataSource
}
