package com.gomsoo.shaken.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gomsoo.shaken.core.model.data.Cocktail
import com.gomsoo.shaken.core.model.data.SimpleCocktail

@Entity(tableName = "simple_cocktail")
data class SimpleCocktailEntity(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String?,
    val category: String?
)

fun SimpleCocktailEntity.asModel() = SimpleCocktail(
    id = id,
    name = name,
    thumbnailUrl = thumbnailUrl,
    category = category
)

fun SimpleCocktail.asEntity() = SimpleCocktailEntity(
    id = id,
    name = name,
    thumbnailUrl = thumbnailUrl,
    category = category
)

fun Cocktail.asEntity() = SimpleCocktailEntity(
    id = id,
    name = name,
    thumbnailUrl = thumbnailUrl,
    category = category
)
