package com.gomsoo.shaken.feature.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gomsoo.shaken.feature.detail.DetailRoute

const val COCKTAIL_ID_ARG = "cocktailId"
const val DETAIL_ROUTE = "detail_route"

private fun getDetailRouteWithId(cocktailId: String = "{$COCKTAIL_ID_ARG}"): String =
    "$DETAIL_ROUTE?$COCKTAIL_ID_ARG=$cocktailId"

fun NavController.navigateToDetail(cocktailId: String, navOptions: NavOptions? = null) {
    navigate(getDetailRouteWithId(cocktailId), navOptions)
}

fun NavGraphBuilder.detailScreen() {
    composable(route = getDetailRouteWithId(),
        arguments = listOf(
            navArgument(COCKTAIL_ID_ARG) {
                nullable = false
                type = NavType.StringType
            }
        )
    ) {
        DetailRoute()
    }
}
