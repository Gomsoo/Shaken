package com.gomsoo.shaken.core.model.data

data class Cocktail(
    val id: String,
    val name: String,
    val thumbnailUrl: String?,
    val category: String?,
    val alcoholic: String?,
    val glass: String?,
    val tags: List<String>,
    val ingredients: List<String>,
    val instructions: Map<String, String>,
    val imageSource: String?,
    val imageAttribution: String?,
    val creativeCommonsConfirmed: String?,
    val updatedAt: String?,
)
