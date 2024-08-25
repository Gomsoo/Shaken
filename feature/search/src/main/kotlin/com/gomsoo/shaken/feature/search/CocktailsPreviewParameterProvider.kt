package com.gomsoo.shaken.feature.search

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.gomsoo.shaken.core.model.data.SimpleCocktail

class CocktailsPreviewParameterProvider : PreviewParameterProvider<List<SimpleCocktail>> {

    override val values: Sequence<List<SimpleCocktail>> = sequenceOf(
        listOf(
            SimpleCocktail("MOJITO", "Mojito"),
            SimpleCocktail("OLD_FASHIONED", "Old Fashioned"),
            SimpleCocktail("LONG_ISLAND_TEA", "Long Island Tea"),
            SimpleCocktail("NEGRONI", "Negroni"),
            SimpleCocktail("WHISKEY_SOUR", "Whiskey Sour"),
            SimpleCocktail("DRY_MARTINI", "Dry Martini"),
            SimpleCocktail("DAIQUIRI", "Daiquiri"),
            SimpleCocktail("MARGARITA", "Margarita")
        )
    )
}
