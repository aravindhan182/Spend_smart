package com.aravindh.spendsmart.navgraph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.aravindh.spendsmart.R


sealed class BottomBarScreen(
    var route: String,
    var title: String,
    var icon: ImageVector
) {
    object Budget : BottomBarScreen(
        route = "budget",
        title = "Budget",
        icon =Icons.Default.AddCircle
    )

    object Filter : BottomBarScreen(
        route = "filter",
        title = "Filter",
        icon = Icons.Default.Menu
    )
}