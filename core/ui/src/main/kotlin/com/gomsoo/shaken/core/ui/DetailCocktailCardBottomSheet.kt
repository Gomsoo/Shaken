package com.gomsoo.shaken.core.ui

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import com.gomsoo.shaken.core.model.data.Cocktail

@ExperimentalLayoutApi
@ExperimentalMaterial3Api
@Composable
fun DetailCocktailCardBottomSheet(
    cocktail: Cocktail,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        DetailCocktailCard(
            cocktail = cocktail,
            isFavorite = isFavorite,
            onFavoriteClick = onFavoriteClick
        )
    }
}
