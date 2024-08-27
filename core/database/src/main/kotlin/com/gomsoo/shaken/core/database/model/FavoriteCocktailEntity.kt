package com.gomsoo.shaken.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "favorite_cocktail")
data class FavoriteCocktailEntity(
    @PrimaryKey @ColumnInfo(name = "cocktail_id") val cocktailId: String,
    @ColumnInfo(name = "created_at") val createdAt: Instant,
)
