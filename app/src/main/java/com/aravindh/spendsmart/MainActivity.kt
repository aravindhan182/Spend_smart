package com.aravindh.spendsmart

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpendSmartTheme {
                val navController = rememberNavController()

                val bottomNavigationItems = listOf(
                    SmoothAnimationBottomBarScreens(
                        BottomBarScreen.Budget.route,
                        "Budget",
                        R.drawable.baseline_attach_money_24
                    ), SmoothAnimationBottomBarScreens(
                        BottomBarScreen.Filter.route,
                        "Filter",
                        R.drawable.baseline_filter_list_24
                    )
                )
                val currentIndex = rememberSaveable {
                    mutableIntStateOf(0)
                }
                Scaffold(bottomBar = {
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
                            Log.i("SELECTED_ITEM", "onCreate: Selected Item ${it.name}")
                        })
                }) { innerPadding ->
                    Modifier.padding(innerPadding)
                    BottomNavGraph(navController, currentIndex)
                }
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