package com.gomsoo.shaken.core.network.model

import com.gomsoo.shaken.core.model.data.Cocktail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCocktail(
    @SerialName("idDrink") val id: String,
    @SerialName("strDrink") val name: String
)

fun NetworkCocktail.asModel(): Cocktail = Cocktail(id, name)

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
