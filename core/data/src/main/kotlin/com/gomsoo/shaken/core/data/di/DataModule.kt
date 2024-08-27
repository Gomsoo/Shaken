package com.gomsoo.shaken.core.data.di

import com.gomsoo.shaken.core.data.repository.CocktailRepository
import com.gomsoo.shaken.core.data.repository.DefaultCocktailRepository
import com.gomsoo.shaken.core.data.repository.DefaultVideoRepository
import com.gomsoo.shaken.core.data.repository.VideoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsCocktailRepository(
        cocktailRepository: DefaultCocktailRepository
    ): CocktailRepository

    @Binds
    internal abstract fun bindsVideoRepository(
        videoRepository: DefaultVideoRepository
    ): VideoRepository
}
