package com.gomsoo.shaken.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gomsoo.shaken.core.database.model.FavoriteCocktailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCocktailDao {

    @Query("SELECT cocktail_id FROM favorite_cocktail")
    fun getAllIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(vararg entities: FavoriteCocktailEntity): List<Long>

    @Query("DELETE FROM favorite_cocktail WHERE cocktail_id in (:ids)")
    suspend fun delete(vararg ids: String)
}
