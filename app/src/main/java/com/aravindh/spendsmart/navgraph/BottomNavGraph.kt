package com.aravindh.spendsmart.navgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aravindh.spendsmart.ui.screens.transactionscreen.TransactionScreen
import com.aravindh.spendsmart.ui.screens.calendar.DatePickerEnhancedExample
import com.aravindh.spendsmart.ui.screens.daily.DailyScreen
import com.aravindh.spendsmart.ui.screens.montlyscreen.MonthlyScreen
import com.aravindh.spendsmart.ui.screens.analyticsscreen.AnalyticsScreen
import com.aravindh.spendsmart.ui.screens.transactionscreen.TransactionViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavGraph(navController: NavHostController, currentIndex: MutableIntState) {
    val transactionViewModel: TransactionViewModel = viewModel()
    NavHost(navController = navController, startDestination = BottomBarScreen.Daily.route) {
        composable(route = BottomBarScreen.Daily.route) {
            DailyScreen(navController = navController)
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
        composable(route = "fabRoute") {
            TransactionScreen(navController = navController,transactionViewModel)
        }
    }

}