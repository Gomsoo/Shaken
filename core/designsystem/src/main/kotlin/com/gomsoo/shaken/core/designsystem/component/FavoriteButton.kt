package com.gomsoo.shaken.core.designsystem.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gomsoo.shaken.core.designsystem.icon.ShakenIcons

@Composable
fun FavoriteButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO 백그라운드 추가?
    IconToggleButton(
        checked = isSelected,
        onCheckedChange = { onClick() },
        modifier = modifier
    ) {
        Icon(
            imageVector = ShakenIcons.Star,
            contentDescription = null,
            tint = if (isSelected) Color.Blue else Color.Gray
        )
    }
}
