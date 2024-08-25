package com.gomsoo.shaken.feature.search

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.gomsoo.shaken.core.model.data.Cocktail

class CocktailsPreviewParameterProvider : PreviewParameterProvider<List<Cocktail>> {

    override val values: Sequence<List<Cocktail>> = sequenceOf(
        listOf(
            Cocktail("MOJITO", "Mojito"),
            Cocktail("OLD_FASHIONED", "Old Fashioned"),
            Cocktail("LONG_ISLAND_TEA", "Long Island Tea"),
            Cocktail("NEGRONI", "Negroni"),
            Cocktail("WHISKEY_SOUR", "Whiskey Sour"),
            Cocktail("DRY_MARTINI", "Dry Martini"),
            Cocktail("DAIQUIRI", "Daiquiri"),
            Cocktail("MARGARITA", "Margarita")
        )
    )
}
