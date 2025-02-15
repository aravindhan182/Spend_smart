package com.aravindh.spendsmart.navgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aravindh.spendsmart.ui.screens.analyticsscreen.AnalyticsScreen
import com.aravindh.spendsmart.ui.screens.calendar.DatePickerEnhancedExample
import com.aravindh.spendsmart.ui.screens.daily.DailyScreen
import com.aravindh.spendsmart.ui.screens.daily.DailyViewModel
import com.aravindh.spendsmart.ui.screens.montlyscreen.MonthlyScreen
import com.aravindh.spendsmart.ui.screens.transactionscreen.TransactionScreen
import com.aravindh.spendsmart.ui.screens.transactionscreen.TransactionViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavGraph(navController: NavHostController, currentIndex: MutableIntState) {
    val transactionViewModel: TransactionViewModel = viewModel()
    val dailyViewModel: DailyViewModel = viewModel()
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        NavHost(navController = navController, startDestination = BottomBarScreen.Daily.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it }) + fadeIn(
                    animationSpec = tween(
                        300
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(
                    animationSpec = tween(
                        300
                    )
                )
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it }) + fadeIn(
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it }) + fadeOut(
                    animationSpec = tween(300)
                )
            }
        ) {
            composable(route = BottomBarScreen.Daily.route) {
                DailyScreen(navController = navController, dailyViewModel)
            }
            composable(route = BottomBarScreen.Monthly.route) {
                MonthlyScreen(navController = navController)
            }
            composable(route = BottomBarScreen.Calendar.route) {
                DatePickerEnhancedExample(navController = navController)
            }
            composable(route = BottomBarScreen.Analytics.route) {
                AnalyticsScreen(navController = navController)
            }
            composable(route = "fabRoute?transactionID={transactionID}") { backStackEntry ->
                val transactionID = backStackEntry.arguments?.getString("transactionID") ?: ""
                TransactionScreen(
                    navController = navController,
                    transactionViewModel,
                    transactionID
                )
            }
        }
    }

}