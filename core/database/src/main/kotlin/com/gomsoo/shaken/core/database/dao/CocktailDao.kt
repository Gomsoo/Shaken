package com.gomsoo.shaken.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gomsoo.shaken.core.database.model.CocktailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

    @Query(
        """
            SELECT * FROM cocktail as c, favorite_cocktail as f
            WHERE c.id=f.cocktail_id
            ORDER BY f.created_at ASC
        """
    )
    fun getFavoriteCocktails(): Flow<List<CocktailEntity>>
}
