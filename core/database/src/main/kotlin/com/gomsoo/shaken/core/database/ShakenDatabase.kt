package com.gomsoo.shaken.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gomsoo.shaken.core.database.dao.CocktailDao
import com.gomsoo.shaken.core.database.dao.FavoriteCocktailDao
import com.gomsoo.shaken.core.database.model.CocktailEntity
import com.gomsoo.shaken.core.database.model.FavoriteCocktailEntity
import com.gomsoo.shaken.core.database.util.CollectionConverter
import com.gomsoo.shaken.core.database.util.DateTimeConverter
import com.gomsoo.shaken.core.database.util.MapConverter

@Database(
    entities = [
        CocktailEntity::class,
        FavoriteCocktailEntity::class
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    DateTimeConverter::class,
    CollectionConverter::class,
    MapConverter::class
)
internal abstract class ShakenDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
    abstract fun favoriteCocktailDao(): FavoriteCocktailDao
}
