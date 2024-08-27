package com.gomsoo.shaken.feature.video.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.gomsoo.shaken.feature.video.VideoRoute

const val VIDEO_ROUTE = "video_route"

fun NavController.navigateToVideo(navOptions: NavOptions? = null) {
    navigate(VIDEO_ROUTE, navOptions)
}

fun NavGraphBuilder.videoScreen() {
    composable(route = VIDEO_ROUTE) {
        VideoRoute()
    }
}
