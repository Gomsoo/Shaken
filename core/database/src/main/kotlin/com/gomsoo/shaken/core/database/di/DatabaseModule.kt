package com.gomsoo.shaken.core.database.di

import android.content.Context
import androidx.room.Room
import com.gomsoo.shaken.core.database.ShakenDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context,
    ): ShakenDatabase = Room.databaseBuilder(
        context = context,
        klass = ShakenDatabase::class.java,
        name = "shaken-database"
    ).build()
}
