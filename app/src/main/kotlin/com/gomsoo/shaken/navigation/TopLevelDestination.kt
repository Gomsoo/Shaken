package com.gomsoo.shaken.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.gomsoo.shaken.core.designsystem.icon.ShakenIcons

/**
 * TODO render selected icon
 */
enum class TopLevelDestination(
    val defaultIcon: ImageVector,
    val selectedIcon: ImageVector,
    val textId: Int
) {
    SEARCH(
        defaultIcon = ShakenIcons.Search,
        selectedIcon = ShakenIcons.OutlinedSearch,
        textId = com.gomsoo.shaken.feature.search.R.string.feature_search_title
    ),
    FAVORITE(
        defaultIcon = ShakenIcons.Star,
        selectedIcon = ShakenIcons.OutlinedStar,
        textId = com.gomsoo.shaken.feature.favorite.R.string.feature_favorite_title
    ),
    VIDEO(
        defaultIcon = ShakenIcons.PlayCircle,
        selectedIcon = ShakenIcons.OutlinedPlayCircle,
        textId = com.gomsoo.shaken.feature.video.R.string.feature_video_title
    )
}
