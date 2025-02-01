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
    object Daily : BottomBarScreen(
        route = "daily",
        title = "Daily",
        icon =Icons.Default.AddCircle
    )

    object Monthly : BottomBarScreen(
        route = "monthly",
        title = "Monthly",
        icon = Icons.Default.Menu
    )

    object Calendar : BottomBarScreen(
        route = "calendar",
        title = "Calendar",
        icon =Icons.Default.AddCircle
    )

    object Analytics : BottomBarScreen(
        route = "Analytics",
        title = "Analytics",
        icon = Icons.Default.Menu
    )
}