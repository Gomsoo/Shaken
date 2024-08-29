package com.gomsoo.shaken.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.gomsoo.shaken.core.database.model.SimpleCocktailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SimpleCocktailDao {

    @Query(
        """
            SELECT * FROM simple_cocktail as c, favorite_cocktail as f
            WHERE c.id=f.cocktail_id
            ORDER BY f.created_at ASC
        """
    )
    fun getFavoriteCocktails(): Flow<List<SimpleCocktailEntity>>

    @Upsert
    fun upsert(vararg cocktails: SimpleCocktailEntity)

    @Delete
    fun delete(cocktail: SimpleCocktailEntity)
}
