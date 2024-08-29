package com.gomsoo.shaken.feature.favorite.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.gomsoo.shaken.feature.favorite.FavoriteRoute

const val FAVORITE_ROUTE = "favorite_route"

fun NavController.navigateToFavorite(navOptions: NavOptions? = null) {
    navigate(FAVORITE_ROUTE, navOptions)
}

fun NavGraphBuilder.favoriteScreen(onItemClick: (String) -> Unit) {
    composable(route = FAVORITE_ROUTE) {
        FavoriteRoute(onItemClick = onItemClick)
    }
}
