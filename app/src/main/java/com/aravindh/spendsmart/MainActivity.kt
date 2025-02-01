package com.aravindh.spendsmart

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.PratikFagadiya.smoothanimationbottombar.model.SmoothAnimationBottomBarScreens
import com.PratikFagadiya.smoothanimationbottombar.properties.BottomBarProperties
import com.PratikFagadiya.smoothanimationbottombar.ui.SmoothAnimationBottomBar
import com.aravindh.spendsmart.navgraph.BottomBarScreen
import com.aravindh.spendsmart.navgraph.BottomNavGraph
import com.aravindh.spendsmart.ui.theme.SpendSmartTheme
import com.aravindh.spendsmart.ui.theme.dark_cyan
import com.aravindh.spendsmart.ui.theme.light_cyan

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpendSmartTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val bottomNavigationItems = listOf(
                    SmoothAnimationBottomBarScreens(
                        BottomBarScreen.Daily.route,
                        "Daily",
                        R.drawable.baseline_update_24
                    ), SmoothAnimationBottomBarScreens(
                        BottomBarScreen.Monthly.route,
                        "Monthly",
                        R.drawable.baseline_calendar_view_month_24
                    ),
                    SmoothAnimationBottomBarScreens(
                        BottomBarScreen.Calendar.route,
                        "Calendar",
                        R.drawable.baseline_calendar_month_24
                    ), SmoothAnimationBottomBarScreens(
                        BottomBarScreen.Analytics.route,
                        "Analytics",
                        R.drawable.baseline_speaker_notes_24
                    )
                )
                val currentIndex = rememberSaveable {
                    mutableIntStateOf(0)
                }
                val screens = listOf(
                    BottomBarScreen.Daily,
                    BottomBarScreen.Monthly,
                    BottomBarScreen.Calendar,
                    BottomBarScreen.Analytics
                )
                val isBottomBarDestination = screens.any { it.route == currentDestination?.route }
                Scaffold(bottomBar = {
                       if (isBottomBarDestination){
                    SmoothAnimationBottomBar(navController,
                        bottomNavigationItems,
                        initialIndex = currentIndex,
                        bottomBarProperties = BottomBarProperties(
                            backgroundColor = dark_cyan,
                            indicatorColor = light_cyan.copy(alpha = 0.5F),
                            iconTintColor = White,
                            iconTintActiveColor = Black,
                            textActiveColor = Black,
                            cornerRadius = 18.dp,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        ),
                        onSelectItem = {
                            currentDestination?.hierarchy?.any { it.route == it.route } == true
                        })
                }
                }, floatingActionButton = {
                    if (isBottomBarDestination) {
                        FloatingActionButton(
                            onClick = {
                                navController.navigate("fabRoute")
                            },
                            modifier = Modifier.padding(bottom = 80.dp),
                            backgroundColor = Color.Red // Change color as needed
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_add_24),
                                contentDescription = "Add"
                            )
                        }
                    }
                }, floatingActionButtonPosition = FabPosition.End,
                    isFloatingActionButtonDocked = true, content = { innerPadding ->
                        Modifier.padding(innerPadding)
                        BottomNavGraph(navController, currentIndex)
                    }
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpendSmartTheme {

    }
}