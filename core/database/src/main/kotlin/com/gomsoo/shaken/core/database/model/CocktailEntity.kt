package com.gomsoo.shaken.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gomsoo.shaken.core.model.data.Cocktail

@Entity(tableName = "cocktail")
data class CocktailEntity(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String?,
    val category: String?,
    val alcoholic: String?,
    val glass: String?,
    val tags: List<String>,
    val ingredients: List<String>,
    val instructions: Map<String, String>,
    @ColumnInfo(name = "image_source") val imageSource: String?,
    @ColumnInfo(name = "image_attribution") val imageAttribution: String?,
    @ColumnInfo(name = "creative_commons_confirmed") val creativeCommonsConfirmed: String?,
    @ColumnInfo(name = "updated_at") val updatedAt: String?, // TODO JSR-310
)

fun CocktailEntity.asModel() = Cocktail(
    id = id,
    name = name,
    thumbnailUrl = thumbnailUrl,
    category = category,
    alcoholic = alcoholic,
    glass = glass,
    tags = tags,
    ingredients = ingredients,
    instructions = instructions,
    imageSource = imageSource,
    imageAttribution = imageAttribution,
    creativeCommonsConfirmed = creativeCommonsConfirmed,
    updatedAt = updatedAt
)
