package com.gomsoo.shaken.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.gomsoo.shaken.feature.search.navigation.SEARCH_ROUTE
import com.gomsoo.shaken.feature.search.navigation.searchScreen

@Composable
fun ShakenNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = SEARCH_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        searchScreen()
    }
}
