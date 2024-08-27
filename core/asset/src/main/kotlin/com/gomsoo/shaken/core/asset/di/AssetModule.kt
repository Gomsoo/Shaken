package com.gomsoo.shaken.core.asset.di

import android.content.Context
import com.gomsoo.shaken.core.asset.AssetDataSource
import com.gomsoo.shaken.core.asset.AssetDataSourceImpl
import com.gomsoo.shaken.core.network.Dispatcher
import com.gomsoo.shaken.core.network.ShakenDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AssetModule {
    @Provides
    @Singleton
    internal fun providesDatabase(
        @ApplicationContext context: Context,
        @Dispatcher(ShakenDispatchers.IO) ioDispatcher: CoroutineDispatcher
    ): AssetDataSource = AssetDataSourceImpl(context, ioDispatcher)
}
