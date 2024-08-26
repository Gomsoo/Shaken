package com.gomsoo.shaken.core.network.model

import com.gomsoo.shaken.core.model.data.Cocktail
import com.gomsoo.shaken.core.model.data.SimpleCocktail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCocktailItem(
    @SerialName("idDrink") val id: String,
    @SerialName("strDrink") val name: String,
    @SerialName("strDrinkThumb") val thumbnailUrl: String
)

fun NetworkCocktailItem.asModel(): SimpleCocktail = SimpleCocktail(id, name, thumbnailUrl, null)

@Serializable
data class NetworkCocktail(
    @SerialName("idDrink") val id: String,
    @SerialName("strDrink") val name: String,
    @SerialName("strDrinkThumb") val thumbnailUrl: String?,
    @SerialName("strCategory") val category: String?,
    @SerialName("strAlcoholic") val alcoholic: String?,
    @SerialName("strGlass") val glass: String?,
    @SerialName("strTags") val tags: String?,
    @SerialName("strIngredient1") val ingredient1: String?,
    @SerialName("strIngredient2") val ingredient2: String?,
    @SerialName("strIngredient3") val ingredient3: String?,
    @SerialName("strIngredient4") val ingredient4: String?,
    @SerialName("strIngredient5") val ingredient5: String?,
    @SerialName("strIngredient6") val ingredient6: String?,
    @SerialName("strIngredient7") val ingredient7: String?,
    @SerialName("strIngredient8") val ingredient8: String?,
    @SerialName("strIngredient9") val ingredient9: String?,
    @SerialName("strIngredient10") val ingredient10: String?,
    @SerialName("strIngredient11") val ingredient11: String?,
    @SerialName("strIngredient12") val ingredient12: String?,
    @SerialName("strIngredient13") val ingredient13: String?,
    @SerialName("strIngredient14") val ingredient14: String?,
    @SerialName("strIngredient15") val ingredient15: String?,
    @SerialName("strMeasure1") val measure1: String?,
    @SerialName("strMeasure2") val measure2: String?,
    @SerialName("strMeasure3") val measure3: String?,
    @SerialName("strMeasure4") val measure4: String?,
    @SerialName("strMeasure5") val measure5: String?,
    @SerialName("strMeasure6") val measure6: String?,
    @SerialName("strMeasure7") val measure7: String?,
    @SerialName("strMeasure8") val measure8: String?,
    @SerialName("strMeasure9") val measure9: String?,
    @SerialName("strMeasure10") val measure10: String?,
    @SerialName("strMeasure11") val measure11: String?,
    @SerialName("strMeasure12") val measure12: String?,
    @SerialName("strMeasure13") val measure13: String?,
    @SerialName("strMeasure14") val measure14: String?,
    @SerialName("strMeasure15") val measure15: String?,
    @SerialName("strInstructions") val instructions: String?,
    @SerialName("strInstructionsES") val instructionsES: String?,
    @SerialName("strInstructionsDE") val instructionsDE: String?,
    @SerialName("strInstructionsFR") val instructionsFR: String?,
    @SerialName("strInstructionsIT") val instructionsIT: String?,
    @SerialName("strInstructionsZH-HANS") val instructionsZHHANS: String?,
    @SerialName("strInstructionsZH-HANT") val instructionsZHHANT: String?,
    @SerialName("strImageSource") val imageSource: String?,
    @SerialName("strImageAttribution") val imageAttribution: String?,
    @SerialName("strCreativeCommonsConfirmed") val creativeCommonsConfirmed: String?,
    @SerialName("dateModified") val updatedAt: String?
)

fun NetworkCocktail.asSimpleModel(): SimpleCocktail =
    SimpleCocktail(id, name, thumbnailUrl, category)

fun NetworkCocktail.asModel(): Cocktail = Cocktail(
    id = id,
    name = name,
    thumbnailUrl = thumbnailUrl,
    category = category,
    alcoholic = alcoholic,
    glass = glass,
    tags = tags?.split(",")?.map { it.trim() } ?: emptyList(),
    ingredients = formattedIngredients(),
    instructions = listOfNotNull(
        instructions?.let { "EN" to it },
        instructionsES?.let { "ES" to it },
        instructionsDE?.let { "DE" to it },
        instructionsFR?.let { "FR" to it },
        instructionsIT?.let { "IT" to it },
        instructionsZHHANS?.let { "ZH-HANS" to it },
        instructionsZHHANT?.let { "ZH-HANT" to it }
    ).toMap(),
    imageSource = imageSource,
    imageAttribution = imageAttribution,
    creativeCommonsConfirmed = creativeCommonsConfirmed,
    updatedAt = updatedAt
)

private fun NetworkCocktail.formattedIngredients(): List<String> = measures.zip(ingredients)
    .mapNotNull { (measure, ingredient) ->
        ingredient.takeUnless { it.isNullOrBlank() }
            ?.let { "${measure?.trim()?.let { "$it " } ?: ""}$ingredient" }
    }

private val NetworkCocktail.measures
    get() = listOf(
        measure1,
        measure2,
        measure3,
        measure4,
        measure5,
        measure6,
        measure7,
        measure8,
        measure9,
        measure10,
        measure11,
        measure12,
        measure13,
        measure14,
        measure15
    )

private val NetworkCocktail.ingredients
    get() = listOf(
        ingredient1,
        ingredient2,
        ingredient3,
        ingredient4,
        ingredient5,
        ingredient6,
        ingredient7,
        ingredient8,
        ingredient9,
        ingredient10,
        ingredient11,
        ingredient12,
        ingredient13,
        ingredient14,
        ingredient15
    )

data class FullNetworkCocktail(
    @SerialName("idDrink") val idDrink: String, // 17222
    @SerialName("strDrink") val strDrink: String, // A1
    @SerialName("strDrinkAlternate") val strDrinkAlternate: Any, // null
    @SerialName("strTags") val strTags: Any, // null
    @SerialName("strVideo") val strVideo: Any, // null
    @SerialName("strCategory") val strCategory: String, // Cocktail
    @SerialName("strIBA") val strIBA: Any, // null
    @SerialName("strAlcoholic") val strAlcoholic: String, // Alcoholic
    @SerialName("strGlass") val strGlass: String, // Cocktail glass
    @SerialName("strInstructions") val strInstructions: String, // Pour all ingredients into a cocktail shaker, mix and serve over ice into a chilled glass.
    @SerialName("strInstructionsES") val strInstructionsES: String, // Vierta todos los ingredientes en una coctelera, mezcle y sirva con hielo en un vaso frío.
    @SerialName("strInstructionsDE") val strInstructionsDE: String, // Alle Zutaten in einen Cocktailshaker geben, mischen und über Eis in ein gekühltes Glas servieren.
    @SerialName("strInstructionsFR") val strInstructionsFR: Any, // null
    @SerialName("strInstructionsIT") val strInstructionsIT: String, // Versare tutti gli ingredienti in uno shaker, mescolare e servire con ghiaccio in un bicchiere freddo.
    @SerialName("strInstructionsZH-HANS") val strInstructionsZHHANS: Any, // null
    @SerialName("strInstructionsZH-HANT") val strInstructionsZHHANT: Any, // null
    @SerialName("strDrinkThumb") val strDrinkThumb: String, // https://www.thecocktaildb.com/images/media/drink/2x8thr1504816928.jpg
    @SerialName("strIngredient1") val strIngredient1: String, // Gin
    @SerialName("strIngredient2") val strIngredient2: String, // Grand Marnier
    @SerialName("strIngredient3") val strIngredient3: String, // Lemon Juice
    @SerialName("strIngredient4") val strIngredient4: String, // Grenadine
    @SerialName("strIngredient5") val strIngredient5: Any, // null
    @SerialName("strIngredient6") val strIngredient6: Any, // null
    @SerialName("strIngredient7") val strIngredient7: Any, // null
    @SerialName("strIngredient8") val strIngredient8: Any, // null
    @SerialName("strIngredient9") val strIngredient9: Any, // null
    @SerialName("strIngredient10") val strIngredient10: Any, // null
    @SerialName("strIngredient11") val strIngredient11: Any, // null
    @SerialName("strIngredient12") val strIngredient12: Any, // null
    @SerialName("strIngredient13") val strIngredient13: Any, // null
    @SerialName("strIngredient14") val strIngredient14: Any, // null
    @SerialName("strIngredient15") val strIngredient15: Any, // null
    @SerialName("strMeasure1") val strMeasure1: String, // 1 3/4 shot
    @SerialName("strMeasure2") val strMeasure2: String, // 1 Shot
    @SerialName("strMeasure3") val strMeasure3: String, // 1/4 Shot
    @SerialName("strMeasure4") val strMeasure4: String, // 1/8 Shot
    @SerialName("strMeasure5") val strMeasure5: Any, // null
    @SerialName("strMeasure6") val strMeasure6: Any, // null
    @SerialName("strMeasure7") val strMeasure7: Any, // null
    @SerialName("strMeasure8") val strMeasure8: Any, // null
    @SerialName("strMeasure9") val strMeasure9: Any, // null
    @SerialName("strMeasure10") val strMeasure10: Any, // null
    @SerialName("strMeasure11") val strMeasure11: Any, // null
    @SerialName("strMeasure12") val strMeasure12: Any, // null
    @SerialName("strMeasure13") val strMeasure13: Any, // null
    @SerialName("strMeasure14") val strMeasure14: Any, // null
    @SerialName("strMeasure15") val strMeasure15: Any, // null
    @SerialName("strImageSource") val strImageSource: Any, // null
    @SerialName("strImageAttribution") val strImageAttribution: Any, // null
    @SerialName("strCreativeCommonsConfirmed") val strCreativeCommonsConfirmed: String, // No
    @SerialName("dateModified") val dateModified: String // 2017-09-07 21:42:09
)
